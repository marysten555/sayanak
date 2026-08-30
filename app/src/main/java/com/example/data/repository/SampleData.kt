package com.example.data.repository

import com.example.data.model.CaseStatus
import com.example.data.model.CenterEntity
import com.example.data.model.ConsentType
import com.example.data.model.HelplineResource
import com.example.data.model.IntakeCategory
import com.example.data.model.ReportEntity
import com.example.data.model.ReporterRole
import com.example.data.model.SeverityTier

data class ResourceGuide(
    val id: String,
    val title: String,
    val category: IntakeCategory,
    val readTimeMinutes: Int,
    val summary: String,
    val steps: List<String>,
    val tags: List<String>
)

object SampleData {

    val helplines = listOf(
        HelplineResource(
            title = "Police Emergency Response (112)",
            number = "112",
            description = "Immediate police assistance and active danger emergency dispatch",
            category = "Emergency",
            is24x7 = true,
            tollFree = true,
            titleTa = "காவல்துறை அவசர உதவி (112)",
            descriptionTa = "உடனடி காவல் உதவி மற்றும் அவசர பாதுகாப்பு படை",
            titleHi = "राष्ट्रीय आपातकालीन पुलिस (112)",
            descriptionHi = "तत्काल पुलिस सहायता और संकट मोचन दस्ता"
        ),
        HelplineResource(
            title = "Tele MANAS (Ministry of Health)",
            number = "14416",
            description = "24/7 Comprehensive Mental Health & Psychological Counselling in 20+ regional languages",
            category = "Mental Health",
            is24x7 = true,
            tollFree = true,
            titleTa = "டெலி-மானாஸ் மனநல உதவி எண் (14416)",
            descriptionTa = "24/7 இலவச மனநல ஆலோசனை மற்றும் உளவியல் உதவி (தமிழ் உள்பட பல மொழிகளில்)",
            titleHi = "टेली-मानस मानसिक स्वास्थ्य (14416)",
            descriptionHi = "24/7 निःशुल्क मानसिक स्वास्थ्य परामर्श एवं मनोवैज्ञानिक सहायता"
        ),
        HelplineResource(
            title = "Women's Helpline (Sakhi - 181)",
            number = "181",
            description = "24/7 Emergency response, domestic violence rescue, medical aid & shelter referral",
            category = "Domestic Safety",
            is24x7 = true,
            tollFree = true,
            titleTa = "பெண்கள் உதவி எண் (சகி - 181)",
            descriptionTa = "குடும்ப வன்முறை மீட்பு, தங்குமிடம் மற்றும் இலவச சட்ட உதவி",
            titleHi = "महिला हेल्पलाइन (सखी - 181)",
            descriptionHi = "घरेलू हिंसा से बचाव, आश्रय एवं कानूनी सहायता"
        ),
        HelplineResource(
            title = "Women Police Helpline (1091)",
            number = "1091",
            description = "24/7 Rapid response patrol for stalking, harassment and domestic abuse",
            category = "Women Safety",
            is24x7 = true,
            tollFree = true,
            titleTa = "பெண்கள் காவல் உதவி எண் (1091)",
            descriptionTa = "தெருவில் தொல்லை, பின்தொடர்தல் மற்றும் குடும்ப கொடுமைக்கு உடனடி ரோந்து உதவி",
            titleHi = "महिला पुलिस हेल्पलाइन (1091)",
            descriptionHi = "छेड़छाड़ एवं घरेलू प्रताड़ना के खिलाफ त्वरित पुलिस सुरक्षा"
        ),
        HelplineResource(
            title = "Childline / Youth Support (1098)",
            number = "1098",
            description = "24/7 Free emergency assistance for youth, students and children under distress (POCSO Protection)",
            category = "Youth & Student",
            is24x7 = true,
            tollFree = true,
            titleTa = "சைல்டுலைன் குழந்தைகள் பாதுகாப்பு (1098)",
            descriptionTa = "குழந்தைகள் மற்றும் மாணவர்களுக்கான 24/7 அவசர பாதுகாப்பு உதவி (POCSO)",
            titleHi = "चाइल्डलाइन बाल सुरक्षा (1098)",
            descriptionHi = "बच्चों एवं किशोरों के लिए 24/7 आपातकालीन सहायता (पॉक्सो सुरक्षा)"
        ),
        HelplineResource(
            title = "National Cyber Crime Helpline (1930)",
            number = "1930",
            description = "Golden Hour fraud prevention: Freeze unauthorized transactions & report cyber harassment",
            category = "Cyber Crime",
            is24x7 = true,
            tollFree = true,
            titleTa = "தேசிய சைபர் கிரைம் உதவி எண் (1930)",
            descriptionTa = "ஆன்லைன் நிதி மோசடி தடுப்பு மற்றும் வங்கி கணக்கு முடக்கம், சைபர் மிரட்டல் உதவி",
            titleHi = "राष्ट्रीय साइबर अपराध हेल्पलाइन (1930)",
            descriptionHi = "वित्तीय धोखाधड़ी रोकने और साइबर ब्लैकमेलिंग रिपोर्ट हेतु"
        ),
        HelplineResource(
            title = "AASRA Suicide Crisis Helpline",
            number = "9820466726",
            description = "24/7 Non-judgmental crisis intervention and suicide prevention support",
            category = "Crisis / SOS",
            is24x7 = true,
            tollFree = false,
            titleTa = "ஆஸ்ரா தற்கொலை தடுப்பு உதவி",
            descriptionTa = "24/7 ரகசிய தற்கொலை தடுப்பு மற்றும் மன அமைதிக்கான உதவி",
            titleHi = "आसरा आत्महत्या रोकथाम हेल्पलाइन",
            descriptionHi = "24/7 गोपनीय संकट हस्तक्षेप और आत्महत्या रोकथाम"
        ),
        HelplineResource(
            title = "KIRAN Helpline (DEPwD)",
            number = "1800-599-0019",
            description = "Toll-free 24/7 mental health rehabilitation and panic management helpline",
            category = "Mental Health",
            is24x7 = true,
            tollFree = true,
            titleTa = "கிரண் மனநல மறுவாழ்வு உதவி (1800-599-0019)",
            descriptionTa = "பதட்டம் மற்றும் தீவிர மன அழுத்தத்திற்கான இலவச அரசு உதவி எண்",
            titleHi = "किरण मानसिक स्वास्थ्य पुनर्वास",
            descriptionHi = "तनाव, चिंता और अवसाद हेतु 24/7 टोल-फ्री हेल्पलाइन"
        ),
        HelplineResource(
            title = "National De-Addiction Helpline",
            number = "1800-11-0031",
            description = "Government helpline for substance abuse consultation & government rehab referral",
            category = "Addiction",
            is24x7 = true,
            tollFree = true,
            titleTa = "தேசிய போதை மறுவாழ்வு உதவி எண் (1800-11-0031)",
            descriptionTa = "போதை பழக்கத்திலிருந்து விடுபட இலவச அரசு மருத்துவ ஆலோசனை",
            titleHi = "राष्ट्रीय नशामुक्ति हेल्पलाइन",
            descriptionHi = "नशा छुड़ाने एवं सरकारी पुनर्वास केंद्र हेतु परामर्श"
        ),
        HelplineResource(
            title = "National Anti-Ragging Helpline",
            number = "1800-180-5522",
            description = "UGC 24x7 toll-free helpline for college/school ragging & campus harassment",
            category = "Student & Ragging",
            is24x7 = true,
            tollFree = true,
            titleTa = "தேசிய ராகிங் எதிர்ப்பு உதவி எண் (1800-180-5522)",
            descriptionTa = "கல்லூரி ராகிங் மற்றும் கேலி வன்முறைக்கு எதிரான 24 மணி நேர இலவச உதவி",
            titleHi = "राष्ट्रीय एंटी-रैगिंग हेल्पलाइन",
            descriptionHi = "कॉलेज/हॉस्टल में रैगिंग एवं उत्पीड़न के विरुद्ध 24 घंटे सहायता"
        ),
        HelplineResource(
            title = "Elderline Senior Citizen Helpline (14567)",
            number = "14567",
            description = "National helpline for abandoned, neglected or abused senior citizens",
            category = "Elder Support",
            is24x7 = true,
            tollFree = true,
            titleTa = "எல்டர்லைன் முதியோர் உதவி எண் (14567)",
            descriptionTa = "கைவிடப்பட்ட மற்றும் கொடுமைக்குள்ளாகும் முதியோருக்கான இலவச அரசு உதவி",
            titleHi = "एल्डरलाइन वरिष्ठ नागरिक हेल्पलाइन",
            descriptionHi = "बुजुर्गों की देखभाल, सुरक्षा एवं भरण-पोषण सहायता"
        ),
        HelplineResource(
            title = "NALSA Free Legal Aid Helpline (15100)",
            number = "15100",
            description = "Free government legal consultation, protection orders & advocate representation",
            category = "Legal Aid",
            is24x7 = true,
            tollFree = true,
            titleTa = "நல்சா (NALSA) இலவச சட்ட உதவி (15100)",
            descriptionTa = "பாதிக்கப்பட்டோருக்கான இலவச அரசு வழக்கறிஞர் மற்றும் சட்ட ஆலோசனை",
            titleHi = "नालसा (NALSA) निःशुल्क कानूनी सहायता (15100)",
            descriptionHi = "पीड़ितों के लिए निःशुल्क सरकारी वकील एवं विधिक परामर्श"
        )
    )

    /**
     * Dynamically filters and prioritizes helplines based on the victim's report category and danger tier.
     */
    fun getSuggestedHelplinesForCategory(categoryStr: String, isUrgent: Boolean = false): List<HelplineResource> {
        val cat = categoryStr.uppercase()
        val primaryMatches = mutableListOf<HelplineResource>()

        when {
            cat.contains("ABUSE") || cat.contains("DOMESTIC") || cat.contains("VIOLENCE") || cat.contains("WOMEN") -> {
                primaryMatches.addAll(helplines.filter { it.number in listOf("1091", "181", "112", "15100") })
            }
            cat.contains("CHILD") || cat.contains("POCSO") || cat.contains("YOUTH") -> {
                primaryMatches.addAll(helplines.filter { it.number in listOf("1098", "112", "15100") })
            }
            cat.contains("CYBER") || cat.contains("FINANCIAL") || cat.contains("FRAUD") -> {
                primaryMatches.addAll(helplines.filter { it.number in listOf("1930", "112", "14416") })
            }
            cat.contains("ADDICTION") || cat.contains("SUBSTANCE") || cat.contains("DRUG") -> {
                primaryMatches.addAll(helplines.filter { it.number in listOf("1800-11-0031", "14416", "1800-599-0019") })
            }
            cat.contains("ACADEMIC") || cat.contains("STUDENT") || cat.contains("RAGGING") || cat.contains("EXAM") -> {
                primaryMatches.addAll(helplines.filter { it.number in listOf("1800-180-5522", "14416", "1098", "1800-599-0019") })
            }
            cat.contains("ELDER") || cat.contains("SENIOR") -> {
                primaryMatches.addAll(helplines.filter { it.number in listOf("14567", "15100", "112") })
            }
            cat.contains("TRAFFICKING") || cat.contains("MISSING") -> {
                primaryMatches.addAll(helplines.filter { it.number in listOf("112", "1098", "15100") })
            }
            else -> { // MENTAL_HEALTH / GENERAL
                primaryMatches.addAll(helplines.filter { it.number in listOf("14416", "1800-599-0019", "9820466726", "112") })
            }
        }

        // If urgent / escalated, ensure 112 is always at the top
        val emergencyPolice = helplines.firstOrNull { it.number == "112" }
        val result = mutableListOf<HelplineResource>()
        if (isUrgent && emergencyPolice != null) {
            result.add(emergencyPolice)
        }
        for (item in primaryMatches) {
            if (!result.contains(item)) {
                result.add(item)
            }
        }
        // Fill up with general crisis helplines if list is short
        for (item in helplines) {
            if (!result.contains(item)) {
                result.add(item)
            }
            if (result.size >= 4) break
        }
        return result
    }

    val defaultCenters = listOf(
        CenterEntity(
            name = "NIMHANS Crisis & Tele-MANAS Hub",
            categorySupported = "ALL",
            centerType = "Apex Government Mental Health Institute",
            city = "Bengaluru",
            state = "Karnataka",
            contactPhone = "080-46110007",
            helplineNumber = "14416",
            address = "Hosur Road, Lakkasandra, Bengaluru",
            isGovernmentEmpanelled = true,
            rating = 4.9f,
            availableHours = "24/7 Emergency & Outpatient"
        ),
        CenterEntity(
            name = "AIIMS National Drug Dependence Treatment Centre (NDDTC)",
            categorySupported = IntakeCategory.ADDICTION.name,
            centerType = "Government Specialized De-Addiction Centre",
            city = "New Delhi",
            state = "Delhi NCR",
            contactPhone = "011-26588500",
            helplineNumber = "1800-11-0031",
            address = "Sector 19, Kamla Nehru Nagar, Ghaziabad / Delhi",
            isGovernmentEmpanelled = true,
            rating = 4.8f,
            availableHours = "Mon-Sat 8:00 AM - 6:00 PM"
        ),
        CenterEntity(
            name = "Sakhi One-Stop Crisis Center (Women & Child Dev)",
            categorySupported = IntakeCategory.ABUSE.name,
            centerType = "Government Crisis & Protection Unit",
            city = "New Delhi",
            state = "Delhi NCR",
            contactPhone = "011-23381611",
            helplineNumber = "181",
            address = "Civil Lines, Integrated Women Shelter, New Delhi",
            isGovernmentEmpanelled = true,
            rating = 4.7f,
            availableHours = "24/7 Emergency Walk-in & Shelter"
        ),
        CenterEntity(
            name = "Vandrevala Foundation Mental Health Center",
            categorySupported = IntakeCategory.MENTAL_HEALTH.name,
            centerType = "Empanelled Non-Profit Crisis Clinic",
            city = "Mumbai",
            state = "Maharashtra",
            contactPhone = "022-25706000",
            helplineNumber = "9999666555",
            address = "Powai Plaza, Hiranandani Gardens, Mumbai",
            isGovernmentEmpanelled = true,
            rating = 4.8f,
            availableHours = "24/7 Tele-counselling & In-person"
        ),
        CenterEntity(
            name = "Hope Trust De-addiction & Wellness",
            categorySupported = IntakeCategory.ADDICTION.name,
            centerType = "Verified De-Addiction Center",
            city = "Hyderabad",
            state = "Telangana",
            contactPhone = "040-23390000",
            helplineNumber = "1800-11-0031",
            address = "Banjara Hills Road No 12, Hyderabad",
            isGovernmentEmpanelled = true,
            rating = 4.6f,
            availableHours = "24/7 Residential Care"
        ),
        CenterEntity(
            name = "Sneha Youth & Suicide Prevention Center",
            categorySupported = IntakeCategory.ACADEMIC_STRESS.name,
            centerType = "Empanelled Youth Mental Health Center",
            city = "Chennai",
            state = "Tamil Nadu",
            contactPhone = "044-24640050",
            helplineNumber = "14416",
            address = "No. 11, Park View Road, R.A. Puram, Chennai",
            isGovernmentEmpanelled = true,
            rating = 4.9f,
            availableHours = "8:00 AM - 10:00 PM Daily"
        )
    )

    val seedReports = listOf(
        ReportEntity(
            trackingToken = "SHK-7291-NX44",
            category = IntakeCategory.ADDICTION.name,
            reporterRole = ReporterRole.THIRD_PARTY.name,
            relationshipToPerson = "Sibling",
            answersSummary = "Bloodshot eyes, sudden weight loss, secretive about whereabouts, missing valuables. Observed > 6 months and escalating.",
            severityTier = SeverityTier.REHAB_SPECIALIST.name,
            severityScore = 72,
            scoreBreakdown = "Observed rapid escalation in dependency pattern (+25 pts); Physical indicators observed (3 signs) (+30 pts); Behavioral changes recorded (4 signs) (+32 pts); Chronic duration > 6 months (+15 pts)",
            isSosTriggered = false,
            consentType = ConsentType.REPORTER_FIRST.name,
            safeContactWindow = "Weekdays 2:00 PM - 4:00 PM",
            codedCoverStory = "Calling regarding college alumni feedback survey",
            contactName = "Anonymous Sibling",
            contactPhoneNumber = "+91 98765 43210",
            roughLocationCity = "Bengaluru",
            assignedCenterName = "AIIMS NDDTC / Empanelled De-addiction Cell",
            status = CaseStatus.ASSIGNED_COUNSELLOR.name,
            statusNotes = "Assigned to Senior Clinical Addictions Specialist Dr. V. Nair. Outbound call scheduled in safe window.",
            createdAt = System.currentTimeMillis() - 86400000L * 2
        ),
        ReportEntity(
            trackingToken = "SHK-4819-AZ90",
            category = IntakeCategory.ABUSE.name,
            reporterRole = ReporterRole.SELF.name,
            relationshipToPerson = "Self",
            answersSummary = "Verbal threats, property damage, bruising. Escalating frequency. Coercive control over finances.",
            severityTier = SeverityTier.URGENT_SOS.name,
            severityScore = 85,
            scoreBreakdown = "Mandatory Physical Violence Flag (Bruising) (+20 pts); Coercive control over finances/movement (+15 pts); Abuse frequency escalating recently (+15 pts); Multi-incident pattern (+35 pts)",
            isSosTriggered = true,
            consentType = ConsentType.RESOURCE_PACK_ONLY.name,
            safeContactWindow = "Do NOT call (Resource Pack Only)",
            codedCoverStory = "",
            contactName = "",
            contactPhoneNumber = "",
            roughLocationCity = "Delhi NCR",
            assignedCenterName = "Sakhi One-Stop Crisis Center",
            status = CaseStatus.ESCALATED_PROTECTIVE.name,
            statusNotes = "Protective resources & 181 safety protocol generated. Legal rights guide under PWDVA delivered.",
            createdAt = System.currentTimeMillis() - 86400000L * 1
        ),
        ReportEntity(
            trackingToken = "SHK-3108-KL55",
            category = IntakeCategory.ACADEMIC_STRESS.name,
            reporterRole = ReporterRole.SELF.name,
            relationshipToPerson = "Self",
            answersSummary = "Competitive exam in 2 weeks. Severe insomnia (<4 hrs sleep), intense panic attacks, feeling paralyzing fear of failure.",
            severityTier = SeverityTier.COUNSELLING.name,
            severityScore = 48,
            scoreBreakdown = "Exam proximity within 2 weeks (+20 pts); Severe sleep disruption (<4 hrs) (+20 pts); High coping distress (+15 pts)",
            isSosTriggered = false,
            consentType = ConsentType.DIRECT_AFFECTED_PERSON.name,
            safeContactWindow = "Evenings after 7:00 PM",
            codedCoverStory = "Academic study skills mentor callback",
            contactName = "Student",
            contactPhoneNumber = "+91 94444 11223",
            roughLocationCity = "Hyderabad",
            assignedCenterName = "Sneha Youth & Suicide Prevention Center",
            status = CaseStatus.CONTACT_SCHEDULED.name,
            statusNotes = "Academic stress specialist assigned. De-escalation & sleep hygiene tele-session scheduled.",
            createdAt = System.currentTimeMillis() - 3600000L * 5
        )
    )

    val resourceGuides = listOf(
        ResourceGuide(
            id = "guide-1",
            title = "Recognizing Hidden Addiction in a Loved One",
            category = IntakeCategory.ADDICTION,
            readTimeMinutes = 4,
            summary = "Behavior-based signs when substance type is unknown. How to communicate safely without triggering aggressive denial.",
            steps = listOf(
                "Do not confront during active intoxication or high tension. Wait for a quiet, sober moment.",
                "Focus purely on observable behaviors and health changes, never on moral accusations.",
                "Express concern from your personal perspective: 'I noticed you seem exhausted and anxious, and I care about your safety.'",
                "Set clear personal boundaries regarding money and safety while offering unconditional emotional support for professional help.",
                "Utilize the national de-addiction helpline (1800-11-0031) for confidential family guidance."
            ),
            tags = listOf("Addiction", "Family Support", "De-escalation")
        ),
        ResourceGuide(
            id = "guide-2",
            title = "Personal Safety & Evidence-Free Domestic Safety Protocol",
            category = IntakeCategory.ABUSE,
            readTimeMinutes = 5,
            summary = "Essential safety planning for individuals or family members noticing coercive control or abuse.",
            steps = listOf(
                "Memorize emergency contacts (Women Helpline 181, Police 112). Keep phone charged and emergency SOS gesture enabled.",
                "Identify a safe physical exit route and a trusted friend or neighbor who can act as a safe refuge in an emergency.",
                "Keep essential documents (Aadhaar, bank passbook, certificates) or copies stored securely outside the house.",
                "Under the Protection of Women from Domestic Violence Act (PWDVA 2005), you have the legal right to free protection orders, residence rights, and free legal aid without needing photo proof.",
                "Use Sahayak's Quick-Exit camouflage button whenever browsing support resources in an unsafe environment."
            ),
            tags = listOf("Domestic Safety", "PWDVA 2005", "Safety Planning", "Legal Rights")
        ),
        ResourceGuide(
            id = "guide-3",
            title = "Acute Panic & 5-4-3-2-1 Somatic Grounding Toolkit",
            category = IntakeCategory.MENTAL_HEALTH,
            readTimeMinutes = 3,
            summary = "Immediate nervous system regulation for intense anxiety, panic attacks, or overwhelming emotional floods.",
            steps = listOf(
                "Acknowledge 5 things you can SEE around you right now (the grain of a desk, a light switch, a shadow).",
                "Acknowledge 4 things you can physically TOUCH (the texture of your clothes, the cool floor, your fingertips pressed together).",
                "Acknowledge 3 things you can HEAR (traffic in the distance, a fan humming, your own breath).",
                "Acknowledge 2 things you can SMELL or enjoy the thought of smelling.",
                "Acknowledge 1 positive statement about yourself: 'I am safe in this immediate moment, and this surge of adrenaline will pass within minutes.'"
            ),
            tags = listOf("Panic Attack", "Grounding", "Mental Health", "CBT")
        ),
        ResourceGuide(
            id = "guide-4",
            title = "Pre-Exam Paralyzing Stress & Sleep Recovery Plan",
            category = IntakeCategory.ACADEMIC_STRESS,
            readTimeMinutes = 4,
            summary = "Evidence-based strategies to break free from study paralysis and restore cognitive functioning before major exams.",
            steps = listOf(
                "Adopt the 25-5 Pomodoro rhythm: 25 minutes of focused reading followed by a strict 5-minute break away from screens.",
                "Protect sleep as a biological study aid: Memories consolidate during non-REM sleep. 6 hours of sleep yields higher retention than pulling an all-nighter.",
                "Differentiate between 'controllable effort' vs 'uncontrollable outcome'. Focus solely on the single question in front of you.",
                "If panic strikes during study, splash cold water on your face to activate the mammalian dive reflex and slow your heart rate.",
                "Connect with peer support or call student helpline 1098 / Tele MANAS 14416 if feelings of worthlessness emerge."
            ),
            tags = listOf("Exam Stress", "Sleep Hygiene", "Students", "Burnout")
        )
    )

    val signsNotJustAPhase = mapOf(
        IntakeCategory.ADDICTION to listOf(
            "Secretiveness: Sudden locking of doors, unexplained financial deficits, or radical change in peer group.",
            "Physical Fluctuations: Frequent tremors, uncharacteristic slurred speech, sudden drastic weight drops, or bloodshot eyes.",
            "Emotional Volatility: Explosive aggression when questioned about daily schedule or money.",
            "Neglect of Core Responsibilities: Complete drop in attendance, abandonment of longtime hobbies or family ties."
        ),
        IntakeCategory.ABUSE to listOf(
            "Coercive Isolation: Being systematically prevented from meeting family, friends, or having personal financial autonomy.",
            "Walking on Eggshells: Constantly altering behavior to prevent irrational outbursts or mood swings.",
            "Minimization: Normalizing severe verbal humiliation, property destruction, or physical threats as 'just stress'.",
            "Escalating Frequency: Episodes that used to happen once a year now repeating weekly or with increased intensity."
        ),
        IntakeCategory.MENTAL_HEALTH to listOf(
            "Persistent Anhedonia: Inability to feel pleasure or interest in any activities for more than 2 consecutive weeks.",
            "Severe Sleep Inversion: Extreme insomnia or sleeping 14+ hours while remaining completely exhausted.",
            "Somatic Collapse: Chronic unexplained headaches, body aches, digestive issues linked to emotional stress.",
            "Hopelessness Language: Expressing statements like 'Everyone would be better off without me' or sudden giving away of possessions."
        ),
        IntakeCategory.ACADEMIC_STRESS to listOf(
            "Cognitive Paralysis: Spending hours sitting in front of books unable to read a single page due to intense dread.",
            "Panic Attacks: Shortness of breath, dizziness, and shaking whenever exams or mock results are mentioned.",
            "Total Social Withdrawal: Refusing meals with family, staying locked in the room, and avoiding all peer calls.",
            "Identity Merging: Equating exam performance with personal human worth and survival."
        )
    )
}
