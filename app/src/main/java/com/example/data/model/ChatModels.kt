package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MessageSenderRole {
    SEEKER,
    COUNSELLOR,
    SYSTEM_SAFETY_BOT
}

enum class MessageDeliveryStatus {
    SENDING,
    SENT,
    DELIVERED
}

@Entity(tableName = "chat_messages")
data class AnonymousChatMessage(
    @PrimaryKey val id: String = "",
    val sessionId: String = "",
    val senderRole: String = MessageSenderRole.SEEKER.name,
    val senderAlias: String = "Anonymous Seeker",
    val messageText: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val category: String = "GENERAL",
    val isSafetyAlert: Boolean = false,
    val deliveryStatus: String = MessageDeliveryStatus.SENT.name
) {
    // Convert to Map for Firestore document storage
    fun toFirestoreMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "sessionId" to sessionId,
            "senderRole" to senderRole,
            "senderAlias" to senderAlias,
            "messageText" to messageText,
            "timestamp" to timestamp,
            "category" to category,
            "isSafetyAlert" to isSafetyAlert,
            "deliveryStatus" to deliveryStatus
        )
    }

    companion object {
        fun fromFirestoreMap(data: Map<String, Any>): AnonymousChatMessage {
            return AnonymousChatMessage(
                id = data["id"] as? String ?: "",
                sessionId = data["sessionId"] as? String ?: "",
                senderRole = data["senderRole"] as? String ?: MessageSenderRole.SEEKER.name,
                senderAlias = data["senderAlias"] as? String ?: "Anonymous Seeker",
                messageText = data["messageText"] as? String ?: "",
                timestamp = (data["timestamp"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                category = data["category"] as? String ?: "GENERAL",
                isSafetyAlert = data["isSafetyAlert"] as? Boolean ?: false,
                deliveryStatus = data["deliveryStatus"] as? String ?: MessageDeliveryStatus.DELIVERED.name
            )
        }
    }
}

data class AnonymousChatSession(
    val sessionId: String = "",
    val category: String = "GENERAL",
    val seekerAlias: String = "Anonymous Seeker",
    val counsellorName: String = "Empanelled Counsellor",
    val isAssigned: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastMessageText: String = "",
    val lastMessageTimestamp: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0
) {
    fun toFirestoreMap(): Map<String, Any> {
        return mapOf(
            "sessionId" to sessionId,
            "category" to category,
            "seekerAlias" to seekerAlias,
            "counsellorName" to counsellorName,
            "isAssigned" to isAssigned,
            "createdAt" to createdAt,
            "lastMessageText" to lastMessageText,
            "lastMessageTimestamp" to lastMessageTimestamp,
            "unreadCount" to unreadCount
        )
    }

    companion object {
        fun fromFirestoreMap(data: Map<String, Any>): AnonymousChatSession {
            return AnonymousChatSession(
                sessionId = data["sessionId"] as? String ?: "",
                category = data["category"] as? String ?: "GENERAL",
                seekerAlias = data["seekerAlias"] as? String ?: "Anonymous Seeker",
                counsellorName = data["counsellorName"] as? String ?: "Empanelled Counsellor",
                isAssigned = data["isAssigned"] as? Boolean ?: false,
                createdAt = (data["createdAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                lastMessageText = data["lastMessageText"] as? String ?: "",
                lastMessageTimestamp = (data["lastMessageTimestamp"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                unreadCount = (data["unreadCount"] as? Number)?.toInt() ?: 0
            )
        }
    }
}
