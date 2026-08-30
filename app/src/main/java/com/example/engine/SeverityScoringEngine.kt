package com.example.engine

import com.example.data.model.IntakeCategory
import com.example.data.model.SeverityTier

data class IntakeAnswers(
    val category: IntakeCategory,
    // Addiction fields
    val physicalSigns: Set<String> = emptySet(),
    val behavioralSigns: Set<String> = emptySet(),
    val addictionDuration: String = "",
    val addictionFrequency: String = "",
    val isAddictionEscalating: Boolean = false,
    val roughSubstanceGuess: String = "Unsure",

    // Abuse fields
    val abuseIncidents: Set<String> = emptySet(),
    val physicalHarmLevel: String = "None", // None, Bruising, Visible injury requiring care, Hospital visit
    val wasAnyonePresent: String = "No",
    val abuseFrequency: String = "",
    val hasEscalatedRecently: Boolean = false,
    val hasWeaponInvolved: Boolean = false,
    val hasThreatenedToKill: Boolean = false,
    val hasHurtPet: Boolean = false,
    val hasControlOverMoneyOrMovement: Boolean = false,

    // Mental Health fields
    val moodSadnessScore: Int = 0, // 0-3
    val sleepDisruptionScore: Int = 0, // 0-3
    val appetiteEnergyScore: Int = 0, // 0-3
    val hopelessnessScore: Int = 0, // 0-3
    val socialWithdrawalScore: Int = 0, // 0-3
    val hasSuicidalIdeation: Boolean = false,

    // Academic stress fields
    val workloadLevel: String = "Moderate", // Mild, Moderate, Heavy, Overwhelming
    val examProximity: String = "Over a month away", // Within a week, Within a month, Over a month away
    val academicSleepHours: String = "6-8 hours", // < 4 hours, 4-6 hours, 6-8 hours
    val academicSupportSystem: String = "Adequate", // Strong, Adequate, Minimal, None
    val academicCopingDifficulty: String = "Manageable", // Manageable, Challenging, Severe, Paralyzing
    val hasPanicEpisodes: Boolean = false,

    // General free text context
    val additionalContextNote: String = ""
)

data class ScoringResult(
    val tier: SeverityTier,
    val totalScore: Int,
    val isEmergencySos: Boolean,
    val explanationRules: List<String>,
    val protectiveEscalationRequired: Boolean,
    val suggestedHelplineNumber: String,
    val recommendedActionSummary: String
)

object SeverityScoringEngine {

    fun evaluate(answers: IntakeAnswers): ScoringResult {
        return when (answers.category) {
            IntakeCategory.ADDICTION -> evaluateAddiction(answers)
            IntakeCategory.ABUSE -> evaluateAbuse(answers)
            IntakeCategory.MENTAL_HEALTH -> evaluateMentalHealth(answers)
            IntakeCategory.ACADEMIC_STRESS -> evaluateAcademic(answers)
        }
    }

    private fun evaluateAddiction(answers: IntakeAnswers): ScoringResult {
        val rules = mutableListOf<String>()
        var score = 0

        // Physical indicators (10 pts each)
        val physCount = answers.physicalSigns.size
        score += physCount * 10
        if (physCount > 0) {
            rules.add("Physical indicators observed ($physCount signs: ${answers.physicalSigns.take(3).joinToString()}) [+$score pts]")
        }

        // Behavioral indicators (8 pts each)
        val behavCount = answers.behavioralSigns.size
        val behavScore = behavCount * 8
        score += behavScore
        if (behavCount > 0) {
            rules.add("Behavioral changes recorded ($behavCount signs) [+$behavScore pts]")
        }

        // Escalation flag (+25 pts)
        if (answers.isAddictionEscalating) {
            score += 25
            rules.add("Observed rapid escalation in dependency pattern [+25 pts]")
        }

        // Duration weight
        when (answers.addictionDuration) {
            "> 1 year" -> { score += 20; rules.add("Chronic duration > 1 year [+20 pts]") }
            "6-12 months" -> { score += 15; rules.add("Medium-long duration 6-12 months [+15 pts]") }
            "1-6 months" -> { score += 10; rules.add("Duration 1-6 months [+10 pts]") }
            else -> { score += 5; rules.add("Recent onset < 1 month [+5 pts]") }
        }

        // High risk substance note
        if (answers.roughSubstanceGuess == "Injectable") {
            score += 30
            rules.add("High clinical risk category (Injectable suspected) [+30 pts]")
        }

        // Tier Decision Tree
        val isEmergency = score >= 85 || answers.roughSubstanceGuess == "Injectable" && answers.isAddictionEscalating
        val tier = when {
            isEmergency -> SeverityTier.URGENT_SOS
            score >= 50 -> SeverityTier.REHAB_SPECIALIST
            score >= 25 -> SeverityTier.COUNSELLING
            else -> SeverityTier.SELF_HELP
        }

        val actionSummary = when (tier) {
            SeverityTier.URGENT_SOS -> "High physical risk detected. Immediate medical de-addiction and crisis helpline recommended."
            SeverityTier.REHAB_SPECIALIST -> "Multi-symptom chronic dependency indicated. Routed to empanelled De-addiction & Rehabilitation Centre."
            SeverityTier.COUNSELLING -> "Moderate behavioral and lifestyle impact. Allocated to a licensed addiction counselor."
            SeverityTier.SELF_HELP -> "Early-stage observation. Curated family guidance pack and self-management toolkit assigned."
        }

        return ScoringResult(
            tier = tier,
            totalScore = score,
            isEmergencySos = isEmergency,
            explanationRules = rules,
            protectiveEscalationRequired = false,
            suggestedHelplineNumber = "14416", // Tele-MANAS
            recommendedActionSummary = actionSummary
        )
    }

    private fun evaluateAbuse(answers: IntakeAnswers): ScoringResult {
        val rules = mutableListOf<String>()
        var score = 0
        var isEmergency = false
        var protectiveRequired = true

        // DASH-Style Critical Risk Escalation Questions (Immediate SOS Triggers)
        if (answers.hasWeaponInvolved) {
            isEmergency = true
            score += 45
            rules.add("CRITICAL DANGER: Weapon involved or threatened [+45 pts -> SOS Override]")
        }
        if (answers.hasThreatenedToKill) {
            isEmergency = true
            score += 45
            rules.add("CRITICAL DANGER: Threats to kill or extreme violence made [+45 pts -> SOS Override]")
        }
        if (answers.physicalHarmLevel == "Hospital visit" || answers.physicalHarmLevel == "Visible injury requiring care") {
            score += 35
            rules.add("Mandatory Physical Violence Flag (${answers.physicalHarmLevel}) [+35 pts]")
        } else if (answers.physicalHarmLevel == "Bruising") {
            score += 20
            rules.add("Physical harm indicator (Bruising) recorded [+20 pts]")
        }

        // Additional DASH factors
        if (answers.hasEscalatedRecently) {
            score += 15
            rules.add("Abuse frequency/severity escalating recently [+15 pts]")
        }
        if (answers.hasHurtPet) {
            score += 15
            rules.add("Aggression directed at pets/dependents [+15 pts]")
        }
        if (answers.hasControlOverMoneyOrMovement) {
            score += 15
            rules.add("Coercive control over finances/movement [+15 pts]")
        }

        // Incident categories count
        val incCount = answers.abuseIncidents.size
        score += incCount * 10
        if (incCount > 0) {
            rules.add("Recorded incident types: ${answers.abuseIncidents.joinToString()} [+$${incCount * 10} pts]")
        }

        val tier = when {
            isEmergency || score >= 70 -> SeverityTier.URGENT_SOS
            score >= 40 -> SeverityTier.REHAB_SPECIALIST // Protective Unit / Shelter
            score >= 20 -> SeverityTier.COUNSELLING
            else -> SeverityTier.SELF_HELP
        }

        val actionSummary = when (tier) {
            SeverityTier.URGENT_SOS -> "High imminent danger. Skipping standard delays: Direct access to National Women Helpline 181, Police 100, and One-Stop Crisis Centers."
            SeverityTier.REHAB_SPECIALIST -> "Severe coercive abuse pattern. Routed to Protection Officer & Empanelled Crisis Support Unit alongside counselling."
            SeverityTier.COUNSELLING -> "Moderate abuse/threat patterns. Confidential trauma-informed counsellor assigned with safe contact protocol."
            SeverityTier.SELF_HELP -> "Safety planning toolkit, legal rights summary under PWDVA, and emergency contacts packet generated."
        }

        return ScoringResult(
            tier = tier,
            totalScore = score,
            isEmergencySos = isEmergency || tier == SeverityTier.URGENT_SOS,
            explanationRules = rules,
            protectiveEscalationRequired = protectiveRequired,
            suggestedHelplineNumber = "181", // Women's Helpline
            recommendedActionSummary = actionSummary
        )
    }

    private fun evaluateMentalHealth(answers: IntakeAnswers): ScoringResult {
        val rules = mutableListOf<String>()

        // 1. Mandatory Immediate SOS Override if Suicidal Ideation / Self-harm is present
        if (answers.hasSuicidalIdeation) {
            rules.add("CRITICAL OVERRIDE: Active suicidal thoughts or self-harm ideation reported. Scoring bypassed for immediate safety protocol.")
            return ScoringResult(
                tier = SeverityTier.URGENT_SOS,
                totalScore = 100,
                isEmergencySos = true,
                explanationRules = rules,
                protectiveEscalationRequired = false,
                suggestedHelplineNumber = "9820466726", // AASRA Suicide Crisis
                recommendedActionSummary = "Immediate crisis intervention activated. AASRA (9820466726) and Tele-MANAS (14416) surfaced immediately."
            )
        }

        // Scaled PHQ-9/GAD-7 adapted sum (0 - 15)
        val rawSum = answers.moodSadnessScore +
                answers.sleepDisruptionScore +
                answers.appetiteEnergyScore +
                answers.hopelessnessScore +
                answers.socialWithdrawalScore

        rules.add("PHQ-9/GAD-7 adapted screening score: $rawSum / 15")
        if (answers.hopelessnessScore >= 2) rules.add("Elevated hopelessness and emotional exhaustion flagged (+high impact)")
        if (answers.sleepDisruptionScore >= 2) rules.add("Significant insomnia/sleep deprivation flagged")
        if (answers.socialWithdrawalScore >= 2) rules.add("Severe social withdrawal and loss of engagement observed")

        val tier = when {
            rawSum >= 12 -> SeverityTier.REHAB_SPECIALIST // Clinical Psychiatric evaluation
            rawSum >= 6 -> SeverityTier.COUNSELLING
            else -> SeverityTier.SELF_HELP
        }

        val actionSummary = when (tier) {
            SeverityTier.URGENT_SOS -> "Immediate crisis triage."
            SeverityTier.REHAB_SPECIALIST -> "High-severity psychological distress. Priority appointment with a licensed clinical psychologist / psychiatrist."
            SeverityTier.COUNSELLING -> "Moderate distress & functional disruption. Allocated to empanelled mental health professional."
            SeverityTier.SELF_HELP -> "Mild situational stress. Delivered evidence-based grounding toolkit, CBT exercises, and passive 2-week check-in."
        }

        return ScoringResult(
            tier = tier,
            totalScore = rawSum * 6, // Normalized to 0-90
            isEmergencySos = false,
            explanationRules = rules,
            protectiveEscalationRequired = false,
            suggestedHelplineNumber = "14416", // Tele-MANAS
            recommendedActionSummary = actionSummary
        )
    }

    private fun evaluateAcademic(answers: IntakeAnswers): ScoringResult {
        val rules = mutableListOf<String>()
        var score = 0

        when (answers.workloadLevel) {
            "Overwhelming" -> { score += 30; rules.add("Workload reported as overwhelming [+30 pts]") }
            "Heavy" -> { score += 20; rules.add("Heavy academic pressure [+20 pts]") }
            "Moderate" -> { score += 10; rules.add("Moderate workload [+10 pts]") }
            else -> { score += 5; rules.add("Mild academic pressure [+5 pts]") }
        }

        when (answers.academicCopingDifficulty) {
            "Paralyzing" -> { score += 35; rules.add("Paralyzing panic / inability to function [+35 pts]") }
            "Severe" -> { score += 25; rules.add("Severe coping distress [+25 pts]") }
            "Challenging" -> { score += 15; rules.add("Challenging stress level [+15 pts]") }
            else -> { score += 5; rules.add("Manageable difficulty [+5 pts]") }
        }

        if (answers.academicSleepHours == "< 4 hours") {
            score += 20
            rules.add("Severe sleep disruption (<4 hrs/night) [+20 pts]")
        }

        if (answers.academicSupportSystem == "None") {
            score += 20
            rules.add("Zero social or family support network reported [+20 pts]")
        }

        if (answers.hasPanicEpisodes) {
            score += 20
            rules.add("Acute panic attacks or somatic breakdowns reported [+20 pts]")
        }

        val isEmergency = score >= 80 || (answers.academicCopingDifficulty == "Paralyzing" && answers.academicSupportSystem == "None")
        val tier = when {
            isEmergency -> SeverityTier.URGENT_SOS
            score >= 50 -> SeverityTier.REHAB_SPECIALIST
            score >= 25 -> SeverityTier.COUNSELLING
            else -> SeverityTier.SELF_HELP
        }

        val actionSummary = when (tier) {
            SeverityTier.URGENT_SOS -> "Acute panic and burnout crisis. Instant access to student crisis helpline and immediate decompression support."
            SeverityTier.REHAB_SPECIALIST -> "High severe burnout with somatic symptoms. Routed to specialized youth psychotherapist."
            SeverityTier.COUNSELLING -> "Moderate exam stress and sleep disruption. Allocated to academic stress & guidance counselor."
            SeverityTier.SELF_HELP -> "Mild exam anxiety. Provided exam pacing guide, sleep hygiene protocol, and cognitive reframing toolkit."
        }

        return ScoringResult(
            tier = tier,
            totalScore = score,
            isEmergencySos = isEmergency,
            explanationRules = rules,
            protectiveEscalationRequired = false,
            suggestedHelplineNumber = "14416", // Tele-MANAS
            recommendedActionSummary = actionSummary
        )
    }
}
