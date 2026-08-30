package com.example

import com.example.data.model.AnonymousChatMessage
import com.example.data.model.IntakeCategory
import com.example.data.model.MessageSenderRole
import com.example.data.model.SeverityTier
import com.example.engine.IntakeAnswers
import com.example.engine.SeverityScoringEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testSeverityScoringEngine_AbuseEmergencyEvaluation() {
    val crisisAnswers = IntakeAnswers(
      category = IntakeCategory.ABUSE,
      hasThreatenedToKill = true,
      hasWeaponInvolved = true,
      physicalHarmLevel = "Hospital visit",
      hasEscalatedRecently = true
    )

    val assessment = SeverityScoringEngine.evaluate(crisisAnswers)
    assertEquals(SeverityTier.URGENT_SOS, assessment.tier)
    assertTrue(assessment.isEmergencySos)
    assertTrue(assessment.protectiveEscalationRequired)
  }

  @Test
  fun testSeverityScoringEngine_ModerateStressEvaluation() {
    val moderateAnswers = IntakeAnswers(
      category = IntakeCategory.ACADEMIC_STRESS,
      workloadLevel = "Moderate",
      examProximity = "Over a month away",
      academicSleepHours = "6-8 hours",
      academicSupportSystem = "Adequate",
      academicCopingDifficulty = "Manageable",
      hasPanicEpisodes = false
    )

    val assessment = SeverityScoringEngine.evaluate(moderateAnswers)
    assertTrue(assessment.tier in listOf(SeverityTier.SELF_HELP, SeverityTier.COUNSELLING))
    assertNotNull(assessment.recommendedActionSummary)
  }

  @Test
  fun testAnonymousChatMessage_FirestoreSerialization() {
    val msg = AnonymousChatMessage(
      id = "msg-1234",
      sessionId = "SHK-CHAT-9999",
      senderRole = MessageSenderRole.SEEKER.name,
      senderAlias = "Anonymous Seeker",
      messageText = "Hello, I need help with anxiety",
      category = "MENTAL_HEALTH",
      isSafetyAlert = false
    )

    val map = msg.toFirestoreMap()
    assertEquals("msg-1234", map["id"])
    assertEquals("SHK-CHAT-9999", map["sessionId"])
    assertEquals("Hello, I need help with anxiety", map["messageText"])

    val restored = AnonymousChatMessage.fromFirestoreMap(map)
    assertEquals(msg.id, restored.id)
    assertEquals(msg.messageText, restored.messageText)
    assertEquals(msg.senderRole, restored.senderRole)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario1_DomesticViolence_Tamil() {
    val input = "என்னை என் கணவர் அடிக்கிறார் எனக்கு உதவி தேவை"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.DOMESTIC_VIOLENCE, triage.category)
    assertEquals(com.example.localization.AppLanguage.TAMIL, triage.detectedLanguage)
    assertEquals("1091", triage.primaryChannel.number)
    assertEquals(com.example.engine.SafetyRiskLevel.HIGH, triage.riskLevel)
    assertTrue(triage.conversationalReply.isNotEmpty())
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario2_ChildAbuse_English() {
    val input = "My child is being abused and harassed"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.CHILD_ABUSE, triage.category)
    assertEquals("1098", triage.primaryChannel.number)
    assertEquals(com.example.engine.SafetyRiskLevel.CRITICAL, triage.riskLevel)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario3_CyberFraud_English() {
    val input = "My bank account was hacked and money was debited"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.CYBER_CRIME, triage.category)
    assertEquals("1930", triage.primaryChannel.number)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario4_MentalHealth_Tamil() {
    val input = "எனக்கு வாழ பிடிக்கவில்லை தற்கொலை எண்ணம் வருகிறது"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.MENTAL_HEALTH, triage.category)
    assertEquals(com.example.localization.AppLanguage.TAMIL, triage.detectedLanguage)
    assertEquals("14416", triage.primaryChannel.number)
    assertEquals(com.example.engine.SafetyRiskLevel.CRITICAL, triage.riskLevel)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario5_Stalking_English() {
    val input = "A stranger has been following me on my way home"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.WOMEN_SAFETY, triage.category)
    assertEquals("1091", triage.primaryChannel.number)
    assertEquals("112", triage.secondaryChannel?.number)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario6_ElderAbuse_Hindi() {
    val input = "बुजुर्ग माता-पिता के साथ दुर्व्यवहार हो रहा है और घर से निकाल दिया"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.ELDER_ABUSE, triage.category)
    assertEquals(com.example.localization.AppLanguage.HINDI, triage.detectedLanguage)
    assertEquals("14567", triage.primaryChannel.number)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario7_HostelRagging_English() {
    val input = "Seniors are ragging and bullying students in the college hostel"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.SCHOOL_HARASSMENT, triage.category)
    assertEquals("1800-180-5522", triage.primaryChannel.number)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario8_WorkplacePosh_English() {
    val input = "My manager is threatening and sexually harassing me at office"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.WORKPLACE_HARASSMENT, triage.category)
    assertEquals("1091", triage.primaryChannel.number)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario9_HumanTrafficking_English() {
    val input = "People are kept in forced confinement for trafficking and unpaid labor"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.HUMAN_TRAFFICKING, triage.category)
    assertEquals("112", triage.primaryChannel.number)
    assertEquals(com.example.engine.SafetyRiskLevel.CRITICAL, triage.riskLevel)
  }

  @Test
  fun testIntelligentSafetyRouter_Scenario10_MissingPerson_Hindi() {
    val input = "हमारा बच्चा कल शाम से लापता है और अपहरण का डर है"
    val triage = com.example.engine.IntelligentSafetyRouter.routeIncident(input)

    assertEquals(com.example.engine.CrisisCategory.MISSING_PERSON, triage.category)
    assertEquals("1094", triage.primaryChannel.number)
    assertEquals("112", triage.secondaryChannel?.number)
  }
}
