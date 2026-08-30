package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class IntakeCategory(val displayName: String, val subtitle: String, val iconResName: String) {
    ADDICTION(
        displayName = "Addiction Support",
        subtitle = "Behavior-based, non-judgmental intake for substance or alcohol dependency",
        iconResName = "medical_services"
    ),
    ABUSE(
        displayName = "Domestic & Women's Safety",
        subtitle = "Incident-based, confidential safety intake with protective escalation",
        iconResName = "shield"
    ),
    MENTAL_HEALTH(
        displayName = "Mental Health & Wellbeing",
        subtitle = "Screening for depression, anxiety, trauma, and emotional distress",
        iconResName = "psychology"
    ),
    ACADEMIC_STRESS(
        displayName = "Academic & Exam Stress",
        subtitle = "Pressure, sleep disruption, burnout, and student crisis support",
        iconResName = "school"
    )
}

enum class SeverityTier(val title: String, val badgeColorHex: Long, val description: String) {
    SELF_HELP(
        title = "Tier 1: Self-Help",
        badgeColorHex = 0xFF10B981,
        description = "Low frequency & mild impact. Curated toolkit, coping strategies, and optional passive check-in."
    ),
    COUNSELLING(
        title = "Tier 2: Counselling",
        badgeColorHex = 0xFF38BDF8,
        description = "Moderate frequency/duration. Allocated to a verified empanelled counselor."
    ),
    REHAB_SPECIALIST(
        title = "Tier 3: Specialist / Rehab",
        badgeColorHex = 0xFFF59E0B,
        description = "High frequency or strong behavioral impact. Routed to a certified specialist or facility."
    ),
    URGENT_SOS(
        title = "Tier 4: Immediate SOS / Crisis",
        badgeColorHex = 0xFFEF4444,
        description = "Immediate danger, active violence, or self-harm risk. Emergency helplines surfaced with top priority."
    )
}

enum class ReporterRole(val label: String) {
    SELF("Reporting for Myself"),
    THIRD_PARTY("Reporting on behalf of a Loved One / Friend / Peer")
}

enum class ConsentType(val label: String, val detail: String) {
    RESOURCE_PACK_ONLY(
        label = "Resource pack only (100% Anonymous)",
        detail = "Receive curated self-help guides & directories. No centre or person will contact you."
    ),
    DIRECT_AFFECTED_PERSON(
        label = "Contact affected person directly",
        detail = "Empanelled professional reaches out directly to the person in need."
    ),
    REPORTER_FIRST(
        label = "Contact me (Reporter) first safely",
        detail = "Centre contacts you at your specified safe window using a coded cover story."
    )
}

enum class CaseStatus(val label: String, val badgeColorHex: Long) {
    RECEIVED("Case Received", 0xFF64748B),
    TRIAGED("Automated Triage Complete", 0xFF38BDF8),
    ASSIGNED_COUNSELLOR("Assigned to Specialist", 0xFF818CF8),
    RESOURCE_SENT("Resource Pack Dispatched", 0xFF10B981),
    CONTACT_SCHEDULED("Safe Contact Scheduled", 0xFFF59E0B),
    ESCALATED_PROTECTIVE("Escalated to Protective Unit", 0xFFEF4444),
    RESOLVED("Case Resolved / Closed", 0xFF0D9488)
}

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val trackingToken: String,
    val category: String, // IntakeCategory.name
    val reporterRole: String, // ReporterRole.name
    val relationshipToPerson: String,
    val answersSummary: String,
    val severityTier: String, // SeverityTier.name
    val severityScore: Int,
    val scoreBreakdown: String,
    val isSosTriggered: Boolean,
    val consentType: String, // ConsentType.name
    val safeContactWindow: String = "",
    val codedCoverStory: String = "",
    val contactName: String = "",
    val contactPhoneNumber: String = "",
    val roughLocationCity: String = "National / Default",
    val assignedCenterId: Long? = null,
    val assignedCenterName: String = "Empanelled Triage Center",
    val status: String = CaseStatus.TRIAGED.name,
    val statusNotes: String = "Automated score calculated. Case queued for confidential triage.",
    val isThreatenedForReporting: Boolean = false,
    val situationEscalated: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "centers")
data class CenterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val categorySupported: String, // IntakeCategory.name or ALL
    val centerType: String,
    val city: String,
    val state: String,
    val contactPhone: String,
    val helplineNumber: String,
    val address: String,
    val isGovernmentEmpanelled: Boolean,
    val rating: Float,
    val availableHours: String
)

data class HelplineResource(
    val title: String,
    val number: String,
    val description: String,
    val category: String,
    val is24x7: Boolean,
    val tollFree: Boolean,
    val titleTa: String = "",
    val descriptionTa: String = "",
    val titleHi: String = "",
    val descriptionHi: String = "",
    val titleTe: String = "",
    val titleKn: String = "",
    val titleMl: String = ""
) {
    fun getLocalizedTitle(lang: com.example.localization.AppLanguage): String = when (lang) {
        com.example.localization.AppLanguage.TAMIL -> titleTa.ifBlank { title }
        com.example.localization.AppLanguage.HINDI -> titleHi.ifBlank { title }
        com.example.localization.AppLanguage.TELUGU -> titleTe.ifBlank { title }
        com.example.localization.AppLanguage.KANNADA -> titleKn.ifBlank { title }
        com.example.localization.AppLanguage.MALAYALAM -> titleMl.ifBlank { title }
        else -> title
    }

    fun getLocalizedDescription(lang: com.example.localization.AppLanguage): String = when (lang) {
        com.example.localization.AppLanguage.TAMIL -> descriptionTa.ifBlank { description }
        com.example.localization.AppLanguage.HINDI -> descriptionHi.ifBlank { description }
        else -> description
    }
}

data class AwarenessQuizQuestion(
    val id: String,
    val text: String,
    val subtitle: String,
    val category: IntakeCategory,
    val options: List<QuizOption>
)

data class QuizOption(
    val text: String,
    val points: Int,
    val isCrisisTrigger: Boolean = false
)

data class SupportCircleMember(
    val role: String, // "Self", "Mother", "Father", "Sibling", "Friend", "Teacher"
    val relation: String,
    val subtitle: String = "",
    val name: String = "",
    val phone: String = "",
    val isEmergencyContact: Boolean = false,
    val status: String = "Connected",
    val avatarColorHex: Long = 0xFF0D4739,
    val heartColorHex: Long = 0xFF10B981
)

data class DailyMoodRecord(
    val dayLabel: String, // "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    val moodType: String, // "JOYFUL", "CALM", "NEUTRAL", "ANXIOUS", "DISTRESSED"
    val score: Int, // 1 to 10
    val journalNote: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

data class RecoveryGoal(
    val id: String,
    val title: String,
    val targetDays: Int,
    val completedDays: Int,
    val category: String = "Wellness"
)

data class RecoveryMilestone(
    val id: String,
    val title: String,
    val description: String,
    val badgeLabel: String,
    val isUnlocked: Boolean,
    val unlockedDate: String? = null
)

data class DailyAffirmation(
    val quote: String,
    val theme: String,
    val author: String = "Sahayak Wellness Companion"
)

