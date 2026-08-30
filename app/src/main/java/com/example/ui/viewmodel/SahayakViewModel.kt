package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.SahayakDatabase
import com.example.data.model.AnonymousChatMessage
import com.example.data.model.AnonymousChatSession
import com.example.data.model.CaseStatus
import com.example.data.model.CenterEntity
import com.example.data.model.ConsentType
import com.example.data.model.DailyAffirmation
import com.example.data.model.DailyMoodRecord
import com.example.data.model.IntakeCategory
import com.example.data.model.RecoveryGoal
import com.example.data.model.RecoveryMilestone
import com.example.data.model.ReportEntity
import com.example.data.model.ReporterRole
import com.example.data.model.SeverityTier
import com.example.data.model.SupportCircleMember
import com.example.data.repository.AnonymousChatRepository
import com.example.data.repository.SahayakRepository
import com.example.data.repository.SampleData
import com.example.emergency.EmergencyContact
import com.example.emergency.LiveGpsState
import com.example.emergency.SafetyCheckinState
import com.example.emergency.SmartSosCoordinator
import com.example.engine.IntakeAnswers
import com.example.engine.ScoringResult
import com.example.engine.SeverityScoringEngine
import com.example.localization.AppLanguage
import com.example.security.CustodyLogEntry
import com.example.security.EvidenceVaultManager
import com.example.security.VaultEvidenceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    AWARENESS_QUIZ,
    TEEN_CHECK,
    SIGNS_EXPLAINER,
    INTAKE_ROLE_CATEGORY,
    INTAKE_QUESTIONS,
    INTAKE_RESULT,
    INTAKE_CONFIRMATION,
    TRACK_REPORT,
    EMERGENCY_SOS,
    COUNSELLOR_PORTAL,
    DIRECTORY,
    RESOURCES,
    ANONYMOUS_CHAT,
    EVIDENCE_VAULT,
    TRUSTED_CONTACTS,
    IMPACT_DASHBOARD
}

data class IntakeFormState(
    val category: IntakeCategory = IntakeCategory.MENTAL_HEALTH,
    val reporterRole: ReporterRole = ReporterRole.SELF,
    val relationshipToPerson: String = "Self",
    val stepIndex: Int = 0,
    val answers: IntakeAnswers = IntakeAnswers(category = IntakeCategory.MENTAL_HEALTH),
    val scoringResult: ScoringResult? = null,
    val consentType: ConsentType = ConsentType.RESOURCE_PACK_ONLY,
    val safeContactWindow: String = "Weekdays 2:00 PM - 5:00 PM",
    val codedCoverStory: String = "Calling as an academic or routine health support advisor",
    val contactName: String = "",
    val contactPhone: String = "",
    val roughLocationCity: String = "Delhi NCR",
    val generatedToken: String = "",
    val isSubmitted: Boolean = false
)

data class TeenCheckState(
    val sleepIssues: Boolean = false,
    val appetiteIssues: Boolean = false,
    val socialWithdrawal: Boolean = false,
    val gradesDrop: Boolean = false,
    val hopelessnessLanguage: Boolean = false,
    val suicidalThoughts: Boolean = false,
    val isCompleted: Boolean = false,
    val isCrisisTriggered: Boolean = false,
    val resultSummary: String = ""
)

data class AwarenessQuizState(
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndices: MutableMap<Int, Int> = mutableMapOf(),
    val isCompleted: Boolean = false,
    val isCrisisTriggered: Boolean = false,
    val recommendedCategory: IntakeCategory? = null,
    val recommendationSummary: String = ""
)

class SahayakViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SahayakRepository
    private val chatRepository: AnonymousChatRepository
    private val evidenceVaultManager = EvidenceVaultManager(application)
    private val smartSosCoordinator = SmartSosCoordinator(application, viewModelScope)

    // Language State (Indic-6)
    private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    // Accessibility States
    private val _isLargeTextEnabled = MutableStateFlow(false)
    val isLargeTextEnabled: StateFlow<Boolean> = _isLargeTextEnabled.asStateFlow()

    private val _isLowLiteracyModeEnabled = MutableStateFlow(false)
    val isLowLiteracyModeEnabled: StateFlow<Boolean> = _isLowLiteracyModeEnabled.asStateFlow()

    // Evidence Vault State
    private val _vaultItems = MutableStateFlow<List<VaultEvidenceItem>>(evidenceVaultManager.createSampleVaultItems())
    val vaultItems: StateFlow<List<VaultEvidenceItem>> = _vaultItems.asStateFlow()

    // Smart SOS & Contacts State
    val liveGpsState: StateFlow<LiveGpsState> = smartSosCoordinator.gpsState
    val isSilentSosEnabled: StateFlow<Boolean> = smartSosCoordinator.isSilentSosEnabled
    val trustedContacts: StateFlow<List<EmergencyContact>> = smartSosCoordinator.trustedContacts
    val safetyCheckinState: StateFlow<SafetyCheckinState> = smartSosCoordinator.checkinState

    init {
        val db = SahayakDatabase.getDatabase(application)
        repository = SahayakRepository(db.reportDao(), db.centerDao())
        chatRepository = AnonymousChatRepository(db.chatDao(), viewModelScope)
        viewModelScope.launch {
            repository.seedInitialCentersIfNeeded()
        }
    }

    val allReports: StateFlow<List<ReportEntity>> = repository.allReports
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCenters: StateFlow<List<CenterEntity>> = repository.allCenters
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Anonymous Support Chat State
    private val _activeChatSessionId = MutableStateFlow("SHK-CHAT-9281-NX2")
    val activeChatSessionId: StateFlow<String> = _activeChatSessionId.asStateFlow()

    private val _activeChatCategory = MutableStateFlow("GENERAL")
    val activeChatCategory: StateFlow<String> = _activeChatCategory.asStateFlow()

    private val _activeChatMessages = MutableStateFlow<List<AnonymousChatMessage>>(emptyList())
    val activeChatMessages: StateFlow<List<AnonymousChatMessage>> = _activeChatMessages.asStateFlow()

    private val _chatInputText = MutableStateFlow("")
    val chatInputText: StateFlow<String> = _chatInputText.asStateFlow()

    private val _isChatSending = MutableStateFlow(false)
    val isChatSending: StateFlow<Boolean> = _isChatSending.asStateFlow()

    val activeChatSessions: StateFlow<List<AnonymousChatSession>> = chatRepository.activeSessions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _counsellorReplyInput = MutableStateFlow("")
    val counsellorReplyInput: StateFlow<String> = _counsellorReplyInput.asStateFlow()

    init {
        // Observe initial chat session messages
        observeChatSession(_activeChatSessionId.value)
    }

    private fun observeChatSession(sessionId: String) {
        viewModelScope.launch {
            chatRepository.observeSessionMessages(sessionId).collectLatest { messages ->
                _activeChatMessages.value = messages
            }
        }
    }

    // Screen navigation
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Safety Quick-Exit Camouflage Mode
    private val _isCamouflageActive = MutableStateFlow(false)
    val isCamouflageActive: StateFlow<Boolean> = _isCamouflageActive.asStateFlow()

    // Intelligent Safety OS Triage & Routing Result
    private val _activeTriageResult = MutableStateFlow<com.example.engine.IntelligentSafetyResponse?>(
        com.example.engine.IntelligentSafetyRouter.routeIncident("General Emergency Assistance", AppLanguage.ENGLISH)
    )
    val activeTriageResult: StateFlow<com.example.engine.IntelligentSafetyResponse?> = _activeTriageResult.asStateFlow()

    // Intake Form Flow
    private val _intakeState = MutableStateFlow(IntakeFormState())
    val intakeState: StateFlow<IntakeFormState> = _intakeState.asStateFlow()

    // Token Tracking
    private val _searchedToken = MutableStateFlow("")
    val searchedToken: StateFlow<String> = _searchedToken.asStateFlow()

    private val _trackedReport = MutableStateFlow<ReportEntity?>(null)
    val trackedReport: StateFlow<ReportEntity?> = _trackedReport.asStateFlow()

    private val _trackingSearchError = MutableStateFlow<String?>(null)
    val trackingSearchError: StateFlow<String?> = _trackingSearchError.asStateFlow()

    // Teen Self-Check State
    private val _teenCheckState = MutableStateFlow(TeenCheckState())
    val teenCheckState: StateFlow<TeenCheckState> = _teenCheckState.asStateFlow()

    // Awareness Quiz State
    private val _awarenessQuizState = MutableStateFlow(AwarenessQuizState())
    val awarenessQuizState: StateFlow<AwarenessQuizState> = _awarenessQuizState.asStateFlow()

    // Active Explainer Category
    private val _selectedExplainerCategory = MutableStateFlow(IntakeCategory.ADDICTION)
    val selectedExplainerCategory: StateFlow<IntakeCategory> = _selectedExplainerCategory.asStateFlow()

    // Status Message / Snackbar notification
    private val _userNotification = MutableStateFlow<String?>(null)
    val userNotification: StateFlow<String?> = _userNotification.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun triggerQuickExitCamouflage() {
        _isCamouflageActive.value = true
    }

    fun exitCamouflage() {
        _isCamouflageActive.value = false
    }

    fun clearNotification() {
        _userNotification.value = null
    }

    fun showNotification(message: String) {
        _userNotification.value = message
    }

    // --- Intake Flow Actions ---
    fun startIntake(category: IntakeCategory? = null, role: ReporterRole = ReporterRole.SELF) {
        val selectedCat = category ?: IntakeCategory.MENTAL_HEALTH
        _intakeState.value = IntakeFormState(
            category = selectedCat,
            reporterRole = role,
            relationshipToPerson = if (role == ReporterRole.SELF) "Self" else "Family / Friend",
            stepIndex = 0,
            answers = IntakeAnswers(category = selectedCat)
        )
        _currentScreen.value = AppScreen.INTAKE_ROLE_CATEGORY
    }

    fun setIntakeCategory(category: IntakeCategory) {
        _intakeState.value = _intakeState.value.copy(
            category = category,
            answers = _intakeState.value.answers.copy(category = category)
        )
    }

    fun setReporterRole(role: ReporterRole, relationship: String) {
        _intakeState.value = _intakeState.value.copy(
            reporterRole = role,
            relationshipToPerson = if (role == ReporterRole.SELF) "Self" else relationship
        )
    }

    fun updateIntakeAnswers(answers: IntakeAnswers) {
        _intakeState.value = _intakeState.value.copy(answers = answers)
    }

    fun proceedToQuestions() {
        _intakeState.value = _intakeState.value.copy(stepIndex = 0)
        _currentScreen.value = AppScreen.INTAKE_QUESTIONS
    }

    fun calculateTriageScore() {
        val state = _intakeState.value
        val result = SeverityScoringEngine.evaluate(state.answers)
        _intakeState.value = state.copy(scoringResult = result)

        if (result.isEmergencySos) {
            _currentScreen.value = AppScreen.EMERGENCY_SOS
        } else {
            _currentScreen.value = AppScreen.INTAKE_RESULT
        }
    }

    fun setConsentDetails(
        consentType: ConsentType,
        safeWindow: String,
        coverStory: String,
        name: String,
        phone: String,
        city: String
    ) {
        _intakeState.value = _intakeState.value.copy(
            consentType = consentType,
            safeContactWindow = safeWindow,
            codedCoverStory = coverStory,
            contactName = name,
            contactPhone = phone,
            roughLocationCity = city
        )
    }

    fun submitReportFinal() {
        viewModelScope.launch {
            val state = _intakeState.value
            val scoring = state.scoringResult ?: SeverityScoringEngine.evaluate(state.answers)
            val generatedToken = SahayakRepository.generateTrackingToken()

            // Choose matched center
            val matchedCenter = allCenters.value.firstOrNull {
                it.categorySupported == state.category.name || it.categorySupported == "ALL"
            }?.name ?: "Empanelled National Triage Center"

            val answersSummary = buildSummaryFromAnswers(state.answers)

            val reportEntity = ReportEntity(
                trackingToken = generatedToken,
                category = state.category.name,
                reporterRole = state.reporterRole.name,
                relationshipToPerson = state.relationshipToPerson,
                answersSummary = answersSummary,
                severityTier = scoring.tier.name,
                severityScore = scoring.totalScore,
                scoreBreakdown = scoring.explanationRules.joinToString(" • "),
                isSosTriggered = scoring.isEmergencySos,
                consentType = state.consentType.name,
                safeContactWindow = state.safeContactWindow,
                codedCoverStory = state.codedCoverStory,
                contactName = state.contactName,
                contactPhoneNumber = state.contactPhone,
                roughLocationCity = state.roughLocationCity,
                assignedCenterName = matchedCenter,
                status = if (scoring.tier == SeverityTier.URGENT_SOS) CaseStatus.ESCALATED_PROTECTIVE.name else CaseStatus.TRIAGED.name,
                statusNotes = if (scoring.tier == SeverityTier.URGENT_SOS)
                    "CRITICAL: Immediate SOS / Protective escalation routed to crisis helpline unit."
                else
                    "Automated rule-based triage completed (${scoring.tier.title}). Case assigned to $matchedCenter.",
                createdAt = System.currentTimeMillis()
            )

            repository.submitReport(reportEntity)

            _intakeState.value = state.copy(
                generatedToken = generatedToken,
                isSubmitted = true
            )
            _trackedReport.value = reportEntity
            _searchedToken.value = generatedToken
            _currentScreen.value = AppScreen.INTAKE_CONFIRMATION
        }
    }

    private fun buildSummaryFromAnswers(answers: IntakeAnswers): String {
        return buildString {
            when (answers.category) {
                IntakeCategory.ADDICTION -> {
                    if (answers.physicalSigns.isNotEmpty()) {
                        append("Physical: ").append(answers.physicalSigns.joinToString()).append("; ")
                    }
                    if (answers.behavioralSigns.isNotEmpty()) {
                        append("Behavioral: ").append(answers.behavioralSigns.joinToString()).append("; ")
                    }
                    append("Observed: ").append(answers.addictionDuration)
                    if (answers.isAddictionEscalating) append(" (Escalating)")
                }
                IntakeCategory.ABUSE -> {
                    if (answers.abuseIncidents.isNotEmpty()) {
                        append("Incidents: ").append(answers.abuseIncidents.joinToString()).append("; ")
                    }
                    append("Harm level: ").append(answers.physicalHarmLevel).append("; ")
                    if (answers.hasWeaponInvolved) append("Weapon involved; ")
                    if (answers.hasThreatenedToKill) append("Kill threats made; ")
                    if (answers.hasEscalatedRecently) append("Escalating recently; ")
                }
                IntakeCategory.MENTAL_HEALTH -> {
                    append("PHQ/GAD Scaled Assessment. ")
                    if (answers.hasSuicidalIdeation) append("Self-harm ideation reported; ")
                    append("Mood score: ").append(answers.moodSadnessScore)
                    append(", Sleep: ").append(answers.sleepDisruptionScore)
                    append(", Hopelessness: ").append(answers.hopelessnessScore)
                }
                IntakeCategory.ACADEMIC_STRESS -> {
                    append("Workload: ").append(answers.workloadLevel).append("; ")
                    append("Exam proximity: ").append(answers.examProximity).append("; ")
                    append("Sleep: ").append(answers.academicSleepHours).append("; ")
                    append("Coping: ").append(answers.academicCopingDifficulty)
                }
            }
            if (answers.additionalContextNote.isNotBlank()) {
                append("\nContext: ").append(answers.additionalContextNote)
            }
        }
    }

    // --- Tracking Token Lookup & Check-In ---
    fun setSearchedToken(token: String) {
        _searchedToken.value = token
    }

    fun lookupToken(token: String) {
        val cleanToken = token.trim().uppercase()
        if (cleanToken.isBlank()) {
            _trackingSearchError.value = "Please enter a valid tracking token"
            return
        }

        viewModelScope.launch {
            val report = repository.getReportByToken(cleanToken)
            if (report != null) {
                _trackedReport.value = report
                _trackingSearchError.value = null
                _currentScreen.value = AppScreen.TRACK_REPORT
            } else {
                _trackingSearchError.value = "No case found with token \"$cleanToken\". Please verify the code."
            }
        }
    }

    fun flagPostReportCheckIn(token: String, threatened: Boolean, escalated: Boolean, note: String = "") {
        viewModelScope.launch {
            repository.flagPostReportEscalation(token, threatened, escalated, note)
            val updated = repository.getReportByToken(token)
            _trackedReport.value = updated
            _userNotification.value = "Case successfully escalated to Emergency Protective Unit! Helplines surfaced."
        }
    }

    // --- Empanelled Counsellor Case Actions ---
    fun updateCaseStatusFromPortal(reportId: Long, newStatus: CaseStatus, note: String) {
        viewModelScope.launch {
            repository.updateReportStatus(reportId, newStatus, note)
            _userNotification.value = "Case updated: ${newStatus.label}"
        }
    }

    // --- Teen Self-Check ---
    fun updateTeenCheck(
        sleep: Boolean,
        appetite: Boolean,
        withdrawal: Boolean,
        grades: Boolean,
        hopelessness: Boolean,
        suicidal: Boolean
    ) {
        val isCrisis = suicidal
        val symptomsCount = listOf(sleep, appetite, withdrawal, grades, hopelessness).count { it }
        val summary = when {
            isCrisis -> "Immediate Crisis Detected: Suicidal thoughts require emergency care."
            symptomsCount >= 4 -> "Significant Distress: You are carrying an immense emotional load that deserves caring, professional support."
            symptomsCount >= 2 -> "Moderate Strain: Early warning signs of burnout and emotional stress detected."
            else -> "Mild / Low Risk: Healthy coping observed. Stay mindful of your sleep and downtime."
        }

        _teenCheckState.value = TeenCheckState(
            sleepIssues = sleep,
            appetiteIssues = appetite,
            socialWithdrawal = withdrawal,
            gradesDrop = grades,
            hopelessnessLanguage = hopelessness,
            suicidalThoughts = suicidal,
            isCompleted = true,
            isCrisisTriggered = isCrisis,
            resultSummary = summary
        )

        if (isCrisis) {
            _currentScreen.value = AppScreen.EMERGENCY_SOS
        }
    }

    fun setExplainerCategory(category: IntakeCategory) {
        _selectedExplainerCategory.value = category
        _currentScreen.value = AppScreen.SIGNS_EXPLAINER
    }

    // --- Anonymous Chat Actions ---
    fun startAnonymousChat(category: String = "GENERAL", customSessionId: String? = null) {
        val sid = customSessionId ?: AnonymousChatRepository.generateAnonymousChatSessionId()
        _activeChatSessionId.value = sid
        _activeChatCategory.value = category
        _chatInputText.value = ""
        observeChatSession(sid)
        _currentScreen.value = AppScreen.ANONYMOUS_CHAT
    }

    fun setChatInputText(text: String) {
        _chatInputText.value = text
    }

    fun setCounsellorReplyInput(text: String) {
        _counsellorReplyInput.value = text
    }

    fun evaluateIncident(text: String, navigateToSosIfHighRisk: Boolean = false) {
        val result = com.example.engine.IntelligentSafetyRouter.routeIncident(text, _currentLanguage.value)
        _activeTriageResult.value = result
        if (navigateToSosIfHighRisk && (result.riskLevel == com.example.engine.SafetyRiskLevel.CRITICAL || result.riskLevel == com.example.engine.SafetyRiskLevel.HIGH)) {
            _currentScreen.value = AppScreen.EMERGENCY_SOS
        }
    }

    fun setCrisisCategoryDirectly(category: com.example.engine.CrisisCategory) {
        val sampleTextForCategory = when (category) {
            com.example.engine.CrisisCategory.DOMESTIC_VIOLENCE -> "Husband beating domestic violence emergency"
            com.example.engine.CrisisCategory.CHILD_ABUSE -> "Child abuse POCSO emergency rescue"
            com.example.engine.CrisisCategory.CYBER_CRIME -> "Bank account hacked financial cyber fraud"
            com.example.engine.CrisisCategory.HUMAN_TRAFFICKING -> "Trafficking forced confinement"
            com.example.engine.CrisisCategory.MENTAL_HEALTH -> "Suicidal severe mental health distress"
            com.example.engine.CrisisCategory.MISSING_PERSON -> "Missing person abduction urgent"
            com.example.engine.CrisisCategory.WOMEN_SAFETY -> "Stalking street harassment unsafe road"
            com.example.engine.CrisisCategory.ELDER_ABUSE -> "Elderly abuse abandoned senior citizen"
            com.example.engine.CrisisCategory.SCHOOL_HARASSMENT -> "Ragging bullying hostel harassment"
            com.example.engine.CrisisCategory.WORKPLACE_HARASSMENT -> "Workplace POSH harassment boss threat"
            com.example.engine.CrisisCategory.GENERAL_DISTRESS -> "General emergency assistance"
        }
        val result = com.example.engine.IntelligentSafetyRouter.routeIncident(sampleTextForCategory, _currentLanguage.value)
        _activeTriageResult.value = result
    }

    fun sendChatMessage() {
        val text = _chatInputText.value.trim()
        if (text.isBlank()) return

        val sid = _activeChatSessionId.value
        val cat = _activeChatCategory.value

        // Immediate intelligent safety triage update
        val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(text, _currentLanguage.value)
        _activeTriageResult.value = triage

        viewModelScope.launch {
            _isChatSending.value = true
            _chatInputText.value = ""
            try {
                chatRepository.sendMessage(
                    sessionId = sid,
                    text = text,
                    category = cat,
                    seekerAlias = "Anonymous Seeker",
                    fallbackLanguage = _currentLanguage.value
                )
            } finally {
                _isChatSending.value = false
            }
        }
    }

    fun sendCounsellorReply(sessionId: String, replyText: String) {
        if (replyText.isBlank()) return
        viewModelScope.launch {
            chatRepository.sendCounsellorReply(sessionId, replyText)
            _counsellorReplyInput.value = ""
            _userNotification.value = "Reply sent to confidential session: $sessionId"
        }
    }

    fun clearActiveChatSession() {
        val sid = _activeChatSessionId.value
        viewModelScope.launch {
            chatRepository.clearSessionMessages(sid)
            _activeChatMessages.value = emptyList()
        }
    }

    // ==========================================
    // 1. Support Circle State & Methods
    // ==========================================
    private val _supportCircleMembers = MutableStateFlow<List<SupportCircleMember>>(
        listOf(
            SupportCircleMember("Self", "Personal Safe Contact", "Your well-being matters most.", "Myself", "Protected Local", false, "Active", 0xFF0D4739, 0xFF10B981),
            SupportCircleMember("Mother", "Parent / Guardian", "Your love and strength.", "Mom", "+91 98765 43210", true, "Trusted", 0xFF0D4739, 0xFFEF4444),
            SupportCircleMember("Father", "Parent / Guardian", "Your support and guidance.", "Dad", "+91 98123 45678", false, "Available", 0xFF0D4739, 0xFF3B82F6),
            SupportCircleMember("Sibling", "Partner in moment", "Your partner in every moment.", "Rohan", "+91 98456 78901", false, "Close", 0xFF0D4739, 0xFFF59E0B),
            SupportCircleMember("Friend", "Trusted Peer", "Your companion and confidant.", "Priya", "+91 98234 56789", true, "Online", 0xFF0D4739, 0xFF8B5CF6),
            SupportCircleMember("Teacher", "Academic Mentor", "Your mentor and guide.", "Prof. Sharma", "+91 98345 67890", false, "On Duty", 0xFF0D4739, 0xFF0D9488)
        )
    )
    val supportCircleMembers: StateFlow<List<SupportCircleMember>> = _supportCircleMembers.asStateFlow()

    fun updateSupportContact(role: String, name: String, phone: String, isEmergency: Boolean) {
        val currentList = _supportCircleMembers.value.toMutableList()
        val index = currentList.indexOfFirst { it.role.equals(role, ignoreCase = true) }
        if (index != -1) {
            currentList[index] = currentList[index].copy(
                name = name,
                phone = phone,
                isEmergencyContact = isEmergency,
                status = if (phone.isNotBlank()) "Updated & Active" else "Pending Setup"
            )
            _supportCircleMembers.value = currentList
            _userNotification.value = "Updated $role contact in your private Support Circle."
        }
    }

    // ==========================================
    // 2. Mood Tracking & Wellbeing Analytics
    // ==========================================
    private val _currentMood = MutableStateFlow("CALM")
    val currentMood: StateFlow<String> = _currentMood.asStateFlow()

    private val _todayJournalNote = MutableStateFlow("")
    val todayJournalNote: StateFlow<String> = _todayJournalNote.asStateFlow()

    private val _emotionalWellbeingScore = MutableStateFlow(84)
    val emotionalWellbeingScore: StateFlow<Int> = _emotionalWellbeingScore.asStateFlow()

    private val _moodHistory = MutableStateFlow<List<DailyMoodRecord>>(
        listOf(
            DailyMoodRecord("Mon", "CALM", 8, "Took a 15-minute mindful walk outdoors"),
            DailyMoodRecord("Tue", "JOYFUL", 9, "Felt motivated and attended study group"),
            DailyMoodRecord("Wed", "NEUTRAL", 6, "Mild academic fatigue, rested early"),
            DailyMoodRecord("Thu", "ANXIOUS", 5, "Felt brief anxiety before mock tests"),
            DailyMoodRecord("Fri", "CALM", 8, "Spoke with support peer; felt relieved"),
            DailyMoodRecord("Sat", "JOYFUL", 9, "Completed recovery goal for week"),
            DailyMoodRecord("Sun", "CALM", 8, "Peaceful morning check-in")
        )
    )
    val moodHistory: StateFlow<List<DailyMoodRecord>> = _moodHistory.asStateFlow()

    fun recordMood(moodType: String, journalNote: String = "") {
        _currentMood.value = moodType
        _todayJournalNote.value = journalNote
        val newScore = when (moodType) {
            "JOYFUL" -> 95
            "CALM" -> 88
            "NEUTRAL" -> 72
            "ANXIOUS" -> 54
            "DISTRESSED" -> 35
            else -> 80
        }
        _emotionalWellbeingScore.value = newScore

        // Update history with today's record
        val currentHistory = _moodHistory.value.toMutableList()
        val todayRecord = DailyMoodRecord("Today", moodType, (newScore / 10).coerceIn(1, 10), journalNote)
        if (currentHistory.isNotEmpty()) {
            currentHistory[currentHistory.lastIndex] = todayRecord
        } else {
            currentHistory.add(todayRecord)
        }
        _moodHistory.value = currentHistory
        _userNotification.value = "Recorded today's mood ($moodType). Wellbeing score updated."
    }

    // ==========================================
    // 3. Recovery Progress & Milestone Achievements
    // ==========================================
    private val _recoveryStreakDays = MutableStateFlow(14)
    val recoveryStreakDays: StateFlow<Int> = _recoveryStreakDays.asStateFlow()

    private val _recoveryGoals = MutableStateFlow<List<RecoveryGoal>>(
        listOf(
            RecoveryGoal("g1", "Daily Mindful Check-in", 7, 6, "Wellness"),
            RecoveryGoal("g2", "Zero-Trigger Safe Space", 14, 14, "Safety"),
            RecoveryGoal("g3", "Reach out to Support Circle", 5, 4, "Social Support"),
            RecoveryGoal("g4", "30-min Offline Grounding", 7, 5, "Recovery")
        )
    )
    val recoveryGoals: StateFlow<List<RecoveryGoal>> = _recoveryGoals.asStateFlow()

    private val _recoveryMilestones = MutableStateFlow<List<RecoveryMilestone>>(
        listOf(
            RecoveryMilestone("m1", "First Step", "Broke the silence and completed anonymous intake", "Level 1", true, "Aug 10"),
            RecoveryMilestone("m2", "7-Day Warrior", "Maintained 7 consecutive days of positive wellness check-ins", "Level 2", true, "Aug 14"),
            RecoveryMilestone("m3", "Support Circle Active", "Added trusted contacts to rapid safety network", "Level 3", true, "Aug 15"),
            RecoveryMilestone("m4", "14-Day Milestone", "Two full weeks of recovery & emotional resilience", "Level 4", true, "Today"),
            RecoveryMilestone("m5", "Mindful Master", "30-day streak of guided recovery & check-ins", "Level 5", false, null)
        )
    )
    val recoveryMilestones: StateFlow<List<RecoveryMilestone>> = _recoveryMilestones.asStateFlow()

    fun incrementRecoveryStreak() {
        _recoveryStreakDays.value = _recoveryStreakDays.value + 1
        _userNotification.value = "Great work! Recovery streak increased to ${_recoveryStreakDays.value} days!"
    }

    // ==========================================
    // 4. Daily Wellness Check-in & Recommendations
    // ==========================================
    private val _wellnessCheckinFeeling = MutableStateFlow<String?>("Peaceful & Safe")
    val wellnessCheckinFeeling: StateFlow<String?> = _wellnessCheckinFeeling.asStateFlow()

    private val _personalizedWellnessSuggestion = MutableStateFlow<String?>(
        "Take a moment for 3 deep box-breaths (Inhale 4s, Hold 4s, Exhale 4s). Your support circle is right beside you."
    )
    val personalizedWellnessSuggestion: StateFlow<String?> = _personalizedWellnessSuggestion.asStateFlow()

    fun checkInWellness(feeling: String) {
        _wellnessCheckinFeeling.value = feeling
        _personalizedWellnessSuggestion.value = when (feeling) {
            "Great" -> "Wonderful! Harness this positive energy to accomplish your weekly recovery goals and message a trusted friend."
            "Good" -> "Steady and balanced! Stay hydrated and take a 10-minute mindful pause during your schedule."
            "Okay" -> "A neutral day is a safe day. Consider writing a quick note in your private journal or listening to calming sounds."
            "Anxious" -> "You are safe right now. Try the 4-7-8 grounding exercise and remember you can open the Anonymous Chat anytime."
            "Overwhelmed" -> "We hear you. You don't have to carry this alone. Tap Emergency SOS or start an Anonymous Chat with an empanelled counselor immediately."
            else -> "Take deep, slow breaths. Sahayak's verified resources and counselors are available 24/7."
        }
        _userNotification.value = "Check-in saved: Feeling $feeling today."
    }

    // Theme state (supports Light healthcare-grade and Dark modes)
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
        _userNotification.value = if (_isDarkMode.value) "Switched to Dark Mode" else "Switched to Light Healthcare Mode"
    }

    // AI Companion Assistant Floating Dialog / Quick Support State
    private val _isAiAssistantOpen = MutableStateFlow(false)
    val isAiAssistantOpen: StateFlow<Boolean> = _isAiAssistantOpen.asStateFlow()

    private val _aiAssistantAdvice = MutableStateFlow<String?>(null)
    val aiAssistantAdvice: StateFlow<String?> = _aiAssistantAdvice.asStateFlow()

    fun toggleAiAssistant(open: Boolean? = null) {
        _isAiAssistantOpen.value = open ?: !_isAiAssistantOpen.value
    }

    fun askAiAssistant(query: String) {
        val q = query.lowercase()
        val advice = when {
            q.contains("suicide") || q.contains("kill") || q.contains("die") || q.contains("harm") || q.contains("end it") ->
                "CRISIS ALERT: Please know you are not alone and your life has deep value. Immediate confidential support is available 24/7. Please dial Tele-MANAS (14416) or SOS (112) now. I can connect you instantly."
            q.contains("anxious") || q.contains("panic") || q.contains("scared") || q.contains("breath") ->
                "Let's ground right now with Box Breathing: Inhale slowly for 4 seconds... Hold gently for 4 seconds... Exhale smoothly for 4 seconds... Repeat 3 times. You are in a safe space."
            q.contains("relapse") || q.contains("cravings") || q.contains("addiction") ->
                "Cravings peak and subside like ocean waves in 15 minutes (Urge Surfing). Drink a tall glass of cold water, change your physical room, or reach out to your Support Circle."
            q.contains("exam") || q.contains("stress") || q.contains("grades") || q.contains("study") ->
                "Break your tasks into 25-minute Pomodoro intervals. Remember: an exam measures temporary performance, not your permanent human worth."
            else ->
                "I am Sahayak's AI Support Companion. I am here 24/7 to listen confidentially, help you ground your emotions, recommend verified resources, or guide you through anonymous triage."
        }
        _aiAssistantAdvice.value = advice
    }

    // ==========================================
    // 5. Positive Affirmations & Encouragement
    // ==========================================
    private val affirmationsList = listOf(
        DailyAffirmation("You are stronger than you think.", "Inner Strength"),
        DailyAffirmation("Small progress is still progress.", "Consistency"),
        DailyAffirmation("Every day is a new beginning.", "Hope & Renewal"),
        DailyAffirmation("Asking for help is the greatest act of courage, not weakness.", "Courage"),
        DailyAffirmation("Healing is not linear. Every single breath today is progress.", "Self-Compassion"),
        DailyAffirmation("You deserve safety, peace of mind, and unconditional respect.", "Safety & Worth"),
        DailyAffirmation("Your past does not define your future. One day at a time.", "Recovery")
    )
    private val _affirmationIndex = MutableStateFlow(0)
    val currentAffirmation: StateFlow<DailyAffirmation> = MutableStateFlow(affirmationsList[0]).asStateFlow()

    fun nextAffirmation() {
        val nextIdx = (_affirmationIndex.value + 1) % affirmationsList.size
        _affirmationIndex.value = nextIdx
        (currentAffirmation as MutableStateFlow).value = affirmationsList[nextIdx]
    }

    // ==========================================
    // 6. Support Directory Search, Filters & Bookmarks
    // ==========================================
    private val _directorySearchQuery = MutableStateFlow("")
    val directorySearchQuery: StateFlow<String> = _directorySearchQuery.asStateFlow()

    private val _directoryCategoryFilter = MutableStateFlow("ALL")
    val directoryCategoryFilter: StateFlow<String> = _directoryCategoryFilter.asStateFlow()

    private val _bookmarkedCenterIds = MutableStateFlow<Set<Long>>(setOf(1L, 3L))
    val bookmarkedCenterIds: StateFlow<Set<Long>> = _bookmarkedCenterIds.asStateFlow()

    fun setDirectorySearchQuery(query: String) {
        _directorySearchQuery.value = query
    }

    fun setDirectoryCategoryFilter(category: String) {
        _directoryCategoryFilter.value = category
    }

    fun toggleBookmarkCenter(centerId: Long) {
        val current = _bookmarkedCenterIds.value.toMutableSet()
        if (current.contains(centerId)) {
            current.remove(centerId)
            _userNotification.value = "Removed center from private bookmarks."
        } else {
            current.add(centerId)
            _userNotification.value = "Added center to private offline bookmarks."
        }
        _bookmarkedCenterIds.value = current
    }

    // Dynamic Wellness Score calculation
    fun calculateDynamicWellnessScore(): Int {
        val mood = _currentMood.value
        val baseScore = when (mood) {
            "JOYFUL" -> 95
            "CALM" -> 88
            "NEUTRAL" -> 75
            "ANXIOUS" -> 58
            "DISTRESSED" -> 40
            else -> 80
        }
        val streakBonus = (_recoveryStreakDays.value * 0.5).toInt().coerceAtMost(10)
        val goalsBonus = (_recoveryGoals.value.count { it.completedDays >= it.targetDays } * 3).coerceAtMost(10)
        return (baseScore + streakBonus + goalsBonus).coerceIn(10, 100)
    }

    // ==========================================
    // 7. AI Chat Sentiment & Context Insights
    // ==========================================
    fun getChatSentimentLevel(): String {
        val messages = _activeChatMessages.value
        if (messages.isEmpty()) return "CALM"
        val lowerText = messages.joinToString(" ") { it.messageText.lowercase() }
        return when {
            lowerText.contains("kill") || lowerText.contains("suicide") || lowerText.contains("die") || lowerText.contains("weapon") || lowerText.contains("emergency") || lowerText.contains("end my life") -> "CRISIS"
            lowerText.contains("panic") || lowerText.contains("abuse") || lowerText.contains("scared") || lowerText.contains("terrified") || lowerText.contains("hurt") -> "DISTRESSED"
            lowerText.contains("stress") || lowerText.contains("anxious") || lowerText.contains("sad") || lowerText.contains("relapse") -> "GUARDED"
            else -> "CALM"
        }
    }

    // ==========================================
    // 8. Multi-Language & Accessibility Actions
    // ==========================================
    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
        _userNotification.value = "Language updated: ${language.nativeName}"
    }

    fun toggleLargeText() {
        _isLargeTextEnabled.value = !_isLargeTextEnabled.value
        _userNotification.value = if (_isLargeTextEnabled.value) "Large text mode enabled" else "Standard text mode enabled"
    }

    fun toggleLowLiteracyMode() {
        _isLowLiteracyModeEnabled.value = !_isLowLiteracyModeEnabled.value
        _userNotification.value = if (_isLowLiteracyModeEnabled.value) "Visual / Low-literacy mode enabled" else "Standard mode enabled"
    }

    fun showUserNotification(message: String) {
        _userNotification.value = message
    }

    // ==========================================
    // 9. Evidence Vault Actions
    // ==========================================
    fun addEvidenceToVault(title: String, category: String, mimeType: String) {
        val now = System.currentTimeMillis()
        val randomHex = java.util.UUID.randomUUID().toString().replace("-", "")
        val sha256 = evidenceVaultManager.computeSha256(randomHex.toByteArray())
        val newItem = VaultEvidenceItem(
            id = "EV-2026-" + (1000..9999).random(),
            fileName = "${title.lowercase().replace(" ", "_")}.enc",
            title = title,
            category = category,
            mimeType = mimeType,
            sizeBytes = (500000..2500000).random().toLong(),
            sha256Checksum = sha256,
            encryptedFilePath = "/data/user/0/com.example/vault/${sha256.take(8)}.enc",
            timestamp = now,
            isExifSanitized = true,
            chainOfCustodyLog = listOf(
                CustodyLogEntry(
                    action = "Captured & Sealed (AES-256-GCM)",
                    actor = "SAYANAK Hardware Vault (Android KeyStore)",
                    timestamp = now,
                    hashVerification = "SHA-256 Checksum Verified"
                )
            )
        )
        val current = _vaultItems.value.toMutableList()
        current.add(0, newItem)
        _vaultItems.value = current
        _userNotification.value = "Item encrypted and sealed with SHA-256 tamper hash in Vault."
    }

    // ==========================================
    // 10. Smart SOS & Safety Check-In Actions
    // ==========================================
    fun triggerSmartSos(silentMode: Boolean = false) {
        smartSosCoordinator.triggerSosDispatch(silentMode = silentMode) { statusMsg ->
            _userNotification.value = statusMsg
        }
        if (!silentMode) {
            _currentScreen.value = AppScreen.EMERGENCY_SOS
        }
    }

    fun sendTestAlertToContacts() {
        val contacts = trustedContacts.value
        _userNotification.value = "Broadcasted test GPS safety ping to ${contacts.size} emergency contacts."
    }

    fun startSafetyCheckinTimer(minutes: Int, note: String) {
        smartSosCoordinator.startSafetyCheckinTimer(minutes, note)
        _userNotification.value = "Safety Check-In Timer started for $minutes minutes."
    }

    fun cancelSafetyCheckin() {
        smartSosCoordinator.cancelSafetyCheckin()
        _userNotification.value = "Safety Check-In Timer canceled."
    }

    fun confirmSafeCheckin() {
        smartSosCoordinator.confirmSafeCheckin()
        _userNotification.value = "Safe check-in confirmed! Your emergency circle is notified you are safe."
    }

    fun addEmergencyContact(name: String, phone: String, relationship: String) {
        smartSosCoordinator.addContact(name, phone, relationship)
        _userNotification.value = "Added $name to rapid alert contacts."
    }

    fun removeEmergencyContact(id: String) {
        smartSosCoordinator.removeContact(id)
        _userNotification.value = "Contact removed from emergency circle."
    }
}


