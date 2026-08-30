package com.example.data.repository

import android.util.Log
import com.example.data.dao.ChatDao
import com.example.data.model.AnonymousChatMessage
import com.example.data.model.AnonymousChatSession
import com.example.data.model.MessageDeliveryStatus
import com.example.data.model.MessageSenderRole
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.random.Random

class AnonymousChatRepository(
    private val chatDao: ChatDao,
    private val scope: CoroutineScope
) {
    private val tag = "SahayakChatRepo"

    // Safe Firestore reference (graceful if Firebase is offline / uninitialized)
    private val firestore: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            Log.w(tag, "Firestore not initialized or offline: ${e.message}")
            null
        }
    }

    private var activeListenerRegistration: ListenerRegistration? = null
    private var activeSessionsListener: ListenerRegistration? = null

    private val _activeSessions = MutableStateFlow<List<AnonymousChatSession>>(emptyList())
    val activeSessions: Flow<List<AnonymousChatSession>> = _activeSessions.asStateFlow()

    init {
        listenToAllActiveSessions()
        seedInitialWelcomeIfEmpty()
    }

    fun observeSessionMessages(sessionId: String): Flow<List<AnonymousChatMessage>> {
        // Start live Firestore sync for this session
        startFirestoreSyncForSession(sessionId)
        return chatDao.getMessagesBySessionFlow(sessionId)
    }

    private fun startFirestoreSyncForSession(sessionId: String) {
        activeListenerRegistration?.remove()

        val db = firestore ?: return
        try {
            activeListenerRegistration = db.collection("anonymous_support_conversations")
                .document(sessionId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.w(tag, "Firestore listen error: ${error.message}")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        val messages = snapshot.documents.mapNotNull { doc ->
                            val data = doc.data ?: return@mapNotNull null
                            AnonymousChatMessage.fromFirestoreMap(data)
                        }

                        scope.launch(Dispatchers.IO) {
                            chatDao.insertMessages(messages)
                        }
                    }
                }
        } catch (e: Exception) {
            Log.w(tag, "Failed to attach Firestore listener: ${e.message}")
        }
    }

    private fun listenToAllActiveSessions() {
        val db = firestore
        if (db != null) {
            try {
                activeSessionsListener = db.collection("anonymous_support_conversations")
                    .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            Log.w(tag, "Active sessions listen error: ${error.message}")
                            return@addSnapshotListener
                        }

                        if (snapshot != null) {
                            val sessions = snapshot.documents.mapNotNull { doc ->
                                val data = doc.data ?: return@mapNotNull null
                                AnonymousChatSession.fromFirestoreMap(data)
                            }
                            if (sessions.isNotEmpty()) {
                                _activeSessions.value = sessions
                            }
                        }
                    }
            } catch (e: Exception) {
                Log.w(tag, "Could not listen to Firestore active sessions: ${e.message}")
            }
        }

        // Seed default demo sessions in local state if empty
        if (_activeSessions.value.isEmpty()) {
            _activeSessions.value = listOf(
                AnonymousChatSession(
                    sessionId = "SHK-7291-NX44",
                    category = "ADDICTION",
                    seekerAlias = "Anonymous Seeker #7291",
                    counsellorName = "Dr. Ananya Sharma (Empanelled)",
                    isAssigned = true,
                    lastMessageText = "We are here for you. Take a slow breath.",
                    lastMessageTimestamp = System.currentTimeMillis() - 1000 * 60 * 12
                ),
                AnonymousChatSession(
                    sessionId = "SHK-3810-KP91",
                    category = "ACADEMIC_STRESS",
                    seekerAlias = "Anonymous Seeker #3810",
                    counsellorName = "Unassigned / Triage Queue",
                    isAssigned = false,
                    lastMessageText = "Feeling extreme burnout before entrance exams.",
                    lastMessageTimestamp = System.currentTimeMillis() - 1000 * 60 * 45
                )
            )
        }
    }

    suspend fun sendMessage(
        sessionId: String,
        text: String,
        category: String = "GENERAL",
        seekerAlias: String = "Anonymous Seeker",
        fallbackLanguage: com.example.localization.AppLanguage = com.example.localization.AppLanguage.ENGLISH
    ): AnonymousChatMessage {
        val msgId = UUID.randomUUID().toString()
        val message = AnonymousChatMessage(
            id = msgId,
            sessionId = sessionId,
            senderRole = MessageSenderRole.SEEKER.name,
            senderAlias = seekerAlias,
            messageText = text.trim(),
            timestamp = System.currentTimeMillis(),
            category = category,
            isSafetyAlert = false,
            deliveryStatus = MessageDeliveryStatus.SENT.name
        )

        // 1. Save to Room database immediately
        withContext(Dispatchers.IO) {
            chatDao.insertMessage(message)
        }

        // 2. Sync to Firestore if available
        val db = firestore
        if (db != null) {
            try {
                db.collection("anonymous_support_conversations")
                    .document(sessionId)
                    .collection("messages")
                    .document(msgId)
                    .set(message.toFirestoreMap(), SetOptions.merge())

                // Update session overview doc
                val sessionDoc = AnonymousChatSession(
                    sessionId = sessionId,
                    category = category,
                    seekerAlias = seekerAlias,
                    counsellorName = "Empanelled Triage Queue",
                    isAssigned = false,
                    lastMessageText = text.trim(),
                    lastMessageTimestamp = message.timestamp
                )
                db.collection("anonymous_support_conversations")
                    .document(sessionId)
                    .set(sessionDoc.toFirestoreMap(), SetOptions.merge())
            } catch (e: Exception) {
                Log.w(tag, "Firestore send error: ${e.message}")
            }
        }

        // Update in-memory session list
        val updatedList = _activeSessions.value.toMutableList()
        val existingIndex = updatedList.indexOfFirst { it.sessionId == sessionId }
        val updatedSession = AnonymousChatSession(
            sessionId = sessionId,
            category = category,
            seekerAlias = seekerAlias,
            counsellorName = "Empanelled Triage Queue",
            isAssigned = false,
            lastMessageText = text.trim(),
            lastMessageTimestamp = message.timestamp
        )
        if (existingIndex >= 0) {
            updatedList[existingIndex] = updatedSession
        } else {
            updatedList.add(0, updatedSession)
        }
        _activeSessions.value = updatedList

        // 3. Automated safety and empathetic triage counsellor reply with intelligent routing
        triggerAutomatedSupportResponse(sessionId, text, category, fallbackLanguage)

        return message
    }

    suspend fun sendCounsellorReply(
        sessionId: String,
        text: String,
        counsellorName: String = "Dr. Ananya Sharma (Empanelled)"
    ) {
        val msgId = UUID.randomUUID().toString()
        val message = AnonymousChatMessage(
            id = msgId,
            sessionId = sessionId,
            senderRole = MessageSenderRole.COUNSELLOR.name,
            senderAlias = counsellorName,
            messageText = text.trim(),
            timestamp = System.currentTimeMillis(),
            category = "SUPPORT",
            isSafetyAlert = false,
            deliveryStatus = MessageDeliveryStatus.DELIVERED.name
        )

        withContext(Dispatchers.IO) {
            chatDao.insertMessage(message)
        }

        val db = firestore
        if (db != null) {
            try {
                db.collection("anonymous_support_conversations")
                    .document(sessionId)
                    .collection("messages")
                    .document(msgId)
                    .set(message.toFirestoreMap(), SetOptions.merge())

                db.collection("anonymous_support_conversations")
                    .document(sessionId)
                    .update(
                        mapOf(
                            "counsellorName" to counsellorName,
                            "isAssigned" to true,
                            "lastMessageText" to text.trim(),
                            "lastMessageTimestamp" to message.timestamp
                        )
                    )
            } catch (e: Exception) {
                Log.w(tag, "Firestore counsellor reply error: ${e.message}")
            }
        }
    }

    private fun triggerAutomatedSupportResponse(
        sessionId: String,
        userText: String,
        category: String,
        fallbackLanguage: com.example.localization.AppLanguage = com.example.localization.AppLanguage.ENGLISH
    ) {
        scope.launch(Dispatchers.IO) {
            val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(userText, fallbackLanguage)
            val isCrisis = triage.riskLevel == com.example.engine.SafetyRiskLevel.CRITICAL || triage.riskLevel == com.example.engine.SafetyRiskLevel.HIGH

            delay(900) // Brief natural conversational pacing

            if (isCrisis) {
                val crisisMsg = AnonymousChatMessage(
                    id = UUID.randomUUID().toString(),
                    sessionId = sessionId,
                    senderRole = MessageSenderRole.SYSTEM_SAFETY_BOT.name,
                    senderAlias = when (triage.detectedLanguage) {
                        com.example.localization.AppLanguage.TAMIL -> "🛡️ SAYANAK பாதுகாப்பு அமைப்பு"
                        com.example.localization.AppLanguage.HINDI -> "🛡️ SAYANAK सुरक्षा प्रहरी"
                        else -> "🛡️ SAYANAK Safety Guard"
                    },
                    messageText = buildString {
                        append("🚨 ")
                        append(when (triage.detectedLanguage) {
                            com.example.localization.AppLanguage.TAMIL -> "அவசர பாதுகாப்பு தகவல் [${triage.riskLevel.labelTa}]\n\n"
                            com.example.localization.AppLanguage.HINDI -> "आपातकालीन सुरक्षा सूचना [${triage.riskLevel.labelHi}]\n\n"
                            else -> "EMERGENCY SAFETY TRIAGE [${triage.riskLevel.labelEn}]\n\n"
                        })
                        append(triage.conversationalReply)
                        append("\n\n📞 ")
                        append(when (triage.detectedLanguage) {
                            com.example.localization.AppLanguage.TAMIL -> "உடனடி பரிந்துரைக்கப்பட்ட உதவி எண்: "
                            com.example.localization.AppLanguage.HINDI -> "अनुशंसित हेल्पलाइन: "
                            else -> "Recommended Direct Helpline: "
                        })
                        append("${triage.primaryChannel.title} -> ${triage.primaryChannel.number}\n")
                        triage.secondaryChannel?.let { sec ->
                            append("🛡️ ${sec.title} -> ${sec.number}\n")
                        }
                        append("\n⚖️ ${triage.legalProtectionNotice}")
                    },
                    timestamp = System.currentTimeMillis(),
                    category = triage.category.name,
                    isSafetyAlert = true,
                    deliveryStatus = MessageDeliveryStatus.DELIVERED.name
                )
                chatDao.insertMessage(crisisMsg)
            } else {
                val counsellorMsg = AnonymousChatMessage(
                    id = UUID.randomUUID().toString(),
                    sessionId = sessionId,
                    senderRole = MessageSenderRole.COUNSELLOR.name,
                    senderAlias = when (triage.detectedLanguage) {
                        com.example.localization.AppLanguage.TAMIL -> "ஆலோசகர் (ரகசிய உதவி)"
                        com.example.localization.AppLanguage.HINDI -> "परामर्शदाता (गोपनीय)"
                        else -> "Empanelled Care Specialist"
                    },
                    messageText = triage.conversationalReply,
                    timestamp = System.currentTimeMillis(),
                    category = triage.category.name,
                    isSafetyAlert = false,
                    deliveryStatus = MessageDeliveryStatus.DELIVERED.name
                )
                chatDao.insertMessage(counsellorMsg)
            }
        }
    }

    suspend fun clearSessionMessages(sessionId: String) {
        withContext(Dispatchers.IO) {
            chatDao.deleteSessionMessages(sessionId)
        }
    }

    private fun seedInitialWelcomeIfEmpty() {
        scope.launch(Dispatchers.IO) {
            val existing = chatDao.getMessagesBySession("SHK-WELCOME-DEFAULT")
            if (existing.isEmpty()) {
                val welcome = AnonymousChatMessage(
                    id = "msg-welcome-001",
                    sessionId = "SHK-WELCOME-DEFAULT",
                    senderRole = MessageSenderRole.SYSTEM_SAFETY_BOT.name,
                    senderAlias = "🛡️ Sahayak Privacy Shield",
                    messageText = "Welcome to Sahayak Confidential Support. All chats here are anonymous. No phone numbers, email addresses, or device IDs are stored or shared. Reach out freely.",
                    timestamp = System.currentTimeMillis(),
                    category = "GENERAL",
                    isSafetyAlert = false,
                    deliveryStatus = MessageDeliveryStatus.DELIVERED.name
                )
                chatDao.insertMessage(welcome)
            }
        }
    }

    companion object {
        fun generateAnonymousChatSessionId(): String {
            val num = Random.nextInt(1000, 9999)
            val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
            val suffix = (1..4).map { chars.random() }.joinToString("")
            return "SHK-CHAT-$num-$suffix"
        }
    }
}
