package com.example.engine

import com.example.localization.AppLanguage

enum class CrisisCategory(
    val id: String,
    val defaultNameEn: String,
    val defaultNameTa: String,
    val defaultNameHi: String,
    val defaultNameTe: String,
    val defaultNameKn: String,
    val defaultNameMl: String
) {
    DOMESTIC_VIOLENCE(
        "DOMESTIC_VIOLENCE",
        "Domestic Violence & Abuse",
        "குடும்ப வன்முறை & கொடுமை",
        "घरेलू हिंसा एवं प्रताड़ना",
        "గృహ హింస & వేధింపులు",
        "ಗೃಹ ಹಿಂಸಾಚಾರ & ದೌರ್ಜನ್ಯ",
        "ഗാർഹിക പീഡനം"
    ),
    CHILD_ABUSE(
        "CHILD_ABUSE",
        "Child Abuse & POCSO Protection",
        "குழந்தைகள் மீதான வன்முறை & பாதுகாப்பு (POCSO)",
        "बाल शोषण एवं पॉक्सो सुरक्षा",
        "పిల్లల వేధింపులు & రక్షణ (POCSO)",
        "ಮಕ್ಕಳ ಮೇಲಿನ ದೌರ್ಜನ್ಯ & ರಕ್ಷಣೆ",
        "കുട്ടികൾക്കെതിരെയുള്ള അതിക്രമം"
    ),
    CYBER_CRIME(
        "CYBER_CRIME",
        "Cyber Crime & Financial Fraud",
        "சைபர் கிரைம் & நிதி மோசடி",
        "साइबर अपराध एवं वित्तीय धोखाधड़ी",
        "సైబర్ నేరం & ఆర్థిక మోసం",
        "ಸೈಬರ್ ಅಪರಾಧ & ಹಣಕಾಸು ವಂಚನೆ",
        "സൈബർ കുറ്റകൃത്യം & തട്ടിപ്പ്"
    ),
    HUMAN_TRAFFICKING(
        "HUMAN_TRAFFICKING",
        "Human Trafficking & Forced Labor",
        "ஆள் கடத்தல் & கட்டாய வேலை",
        "मानव तस्करी एवं बंधुआ मजदूरी",
        "మానవ అక్రమ రవాణా",
        "ಮಾನವ ಕಳ್ಳಸಾಗಣೆ & ಬಲವಂತದ ದುಡಿಮೆ",
        "മനുഷ്യക്കടത്ത് & ചൂഷണം"
    ),
    MENTAL_HEALTH(
        "MENTAL_HEALTH",
        "Mental Health Crisis & Suicide Prevention",
        "மனநல அவசர உதவி & தற்கொலை தடுப்பு",
        "मानसिक स्वास्थ्य एवं आत्महत्या रोकथाम",
        "మానసిక ఆరోగ్య అత్యవసర సహాయం",
        "ಮಾನಸಿಕ ಆರೋಗ್ಯ & ಆತ್ಮಹತ್ಯೆ ತಡೆಗಟ್ಟುವಿಕೆ",
        "മാനസികാരോഗ്യ പ്രതിസന്ധി"
    ),
    MISSING_PERSON(
        "MISSING_PERSON",
        "Missing Person / Abduction",
        "காணாமல் போனவர் & கடத்தல் எச்சரிக்கை",
        "लापता व्यक्ति एवं अपहरण सूचना",
        "తప్పిపోయిన వ్యక్తి & అపహరణ",
        "ಕಾಣೆಯಾದ ವ್ಯಕ್ತಿ & ಅಪಹರಣ",
        "കാണാതായ വ്യക്തി"
    ),
    WOMEN_SAFETY(
        "WOMEN_SAFETY",
        "Women Safety & Stalking in Public",
        "பெண்கள் பாதுகாப்பு & பொது இட அச்சுறுத்தல்",
        "महिला सुरक्षा एवं पीछा करना/छेड़छाड़",
        "మహిళల భద్రత & వేధింపులు",
        "ಮಹಿಳಾ ಸುರಕ್ಷತೆ & ಬೀದಿ ದೌರ್ಜನ್ಯ",
        "സ്ത്രീ സുരക്ഷ & അതിക്രമങ്ങൾ"
    ),
    ELDER_ABUSE(
        "ELDER_ABUSE",
        "Elder Abuse & Abandonment",
        "முதியோர் கொடுமை & கைவிடப்படுதல்",
        "वरिष्ठ नागरिक प्रताड़ना एवं उपेक्षा",
        "వృద్ధుల వేధింపులు & నిరాదరణ",
        "ಹಿರಿಯ ನಾಗರಿಕರ ಮೇಲಿನ ದೌರ್ಜನ್ಯ",
        "മുതിർന്ന പൗരന്മാർക്കെതിരെയുള്ള അതിക്രമം"
    ),
    SCHOOL_HARASSMENT(
        "SCHOOL_HARASSMENT",
        "School / College Ragging & Bullying",
        "பள்ளி/கல்லூரி ராகிங் & அச்சுறுத்தல்",
        "स्कूल/कॉलेज रैगिंग एवं प्रताड़ना",
        "స్కూల్/కాలేజీ ర్యాగింగ్ & బెదిరింపులు",
        "ಶಾಲೆ/ಕಾಲೇಜು ರ‍್ಯಾಗಿಂಗ್ & ಪೀಡನೆ",
        "സ്കൂൾ/കോളേജ് റാഗിംഗ് & പീഡനം"
    ),
    WORKPLACE_HARASSMENT(
        "WORKPLACE_HARASSMENT",
        "Workplace Harassment (POSH Act)",
        "பணியிட பாலியல் துன்புறுத்தல் (POSH)",
        "कार्यस्थल उत्पीड़न (POSH कानून)",
        "కార్యాలయ వేధింపులు (POSH చట్టం)",
        "ಕೆಲಸದ ಸ್ಥಳದಲ್ಲಿ ಕಿರುಕುಳ (POSH)",
        "തൊഴിലിടങ്ങളിലെ പീഡനം (POSH)"
    ),
    GENERAL_DISTRESS(
        "GENERAL_DISTRESS",
        "General Emergency & Legal Aid",
        "பொது அவசர உதவி & சட்ட ஆலோசனை",
        "सामान्य आपातकाल एवं विधिक सहायता",
        "సాధారణ అత్యవసర & న్యాయ సహాయం",
        "ಸಾಮಾನ್ಯ ತುರ್ತು & ಕಾನೂನು ನೆರವು",
        "പൊതു അടിയന്തര സഹായം"
    )
}

enum class SafetyRiskLevel(
    val tier: String,
    val colorHex: Long,
    val maxResponseMinutes: Int,
    val labelEn: String,
    val labelTa: String,
    val labelHi: String,
    val labelTe: String,
    val labelKn: String,
    val labelMl: String
) {
    CRITICAL(
        "CRITICAL",
        0xFFDC2626,
        3,
        "Critical Danger (Immediate Threat)",
        "உடனடி ஆபத்து (அதிதீவிரம்)",
        "अत्यधिक गंभीर (तत्काल खतरा)",
        "తీవ్రమైన ప్రమాదం (తక్షణ ముప్పు)",
        "ತೀವ್ರ ಅಪಾಯ (ತಕ್ಷಣದ ಬೆದರಿಕೆ)",
        "അടിയന്തര ഭീഷണി (തീവ്രം)"
    ),
    HIGH(
        "HIGH",
        0xFFEA580C,
        7,
        "High Risk (Urgent Intervention)",
        "அதிக ஆபத்து (உடனடி நடவடிக்கை)",
        "उच्च जोखिम (त्वरित हस्तक्षेप)",
        "అధిక ప్రమాదం (త్వరిత జోక్యం)",
        "ಹೆಚ್ಚಿನ ಅಪಾಯ (ತುರ್ತು ಕ್ರಮ)",
        "ഉയർന്ന അപകടസാധ്യത"
    ),
    MEDIUM(
        "MEDIUM",
        0xFFD97706,
        20,
        "Moderate Risk (Safety Support Needed)",
        "நடுத்தர ஆபத்து (பாதுகாப்பு உதவி தேவை)",
        "मध्यम जोखिम (सुरक्षा सहयोग अपेक्षित)",
        "మధ్యస్థ ప్రమాదం (రక్షణ సహాయం అవసరం)",
        "ಮಧ್ಯಮ ಅಪಾಯ (ರಕ್ಷಣಾ ನೆರವು ಅಗತ್ಯ)",
        "ഇടത്തരം അപകടസാധ്യത"
    ),
    LOW(
        "LOW",
        0xFF16A34A,
        60,
        "Advisory & Prevention",
        "ஆலோசனை & முன்னெச்சரிக்கை",
        "परामर्श एवं रोकथाम",
        "సలహా & నివారణ",
        "ಸಲಹೆ & ತಡೆಗಟ್ಟುವಿಕೆ",
        "ഉപദേശം & പ്രതിരോധം"
    )
}

data class EmergencyActionChannel(
    val title: String,
    val number: String,
    val agencyName: String,
    val isPrimary: Boolean = true,
    val badge: String = "1-TAP DISPATCH",
    val description: String
)

data class IntelligentSafetyResponse(
    val detectedLanguage: AppLanguage,
    val category: CrisisCategory,
    val riskLevel: SafetyRiskLevel,
    val primaryChannel: EmergencyActionChannel,
    val secondaryChannel: EmergencyActionChannel?,
    val immediateSafetyInstruction: String,
    val legalProtectionNotice: String,
    val conversationalReply: String,
    val escalationRequired: Boolean,
    val quickActionChips: List<String>
)

object IntelligentSafetyRouter {

    private val TAMIL_PHONETIC_KEYWORDS = listOf(
        "enakku", "udhavi", "vendum", "thevai", "tharkolai", "kanavar", "adikkirar", "adikrar", "adikranga",
        "ponnu", "payyan", "kashtam", "romba", "bayama", "irukku", "kaapathunga", "pasanga", "panranga",
        "panran", "panren", "aabathu", "thozhil", "veetla", "kudumbam", "kudumba", "amma", "appa",
        "nalla", "illai", "theriyala", "yen", "enna", "eppadi", "enge", "yar", "etharkku", "valikuthu",
        "saganum", "maranam", "mosadi", "mirattal", "vanmurai", "kodumai", "seikiraarkal", "thiruttu",
        "panam", "kadathal", "kaanamal", "vayadhana", "periyavanga", "azhugiren", "azhugai", "thooku",
        "maathirai", "vanakkam", "thevapaduthu", "kaapathi", "azhaikka", "padhukaapu"
    )

    private val HINDI_PHONETIC_KEYWORDS = listOf(
        "madad", "chahiye", "bachao", "shikayat", "pati", "marpeet", "preshan", "marna", "atmahatya",
        "pareshani", "ghar", "ladka", "ladki", "bacha", "bachi", "dhamki", "paisa", "namaste", "dhokha"
    )

    private val TELUGU_PHONETIC_KEYWORDS = listOf(
        "sahayam", "kavali", "bhayam", "chaduvu", "kodutunnaru", "chachipovali", "namaskaram"
    )

    private val KANNADA_PHONETIC_KEYWORDS = listOf(
        "sahaya", "beku", "madadi", "bayavagide", "hoditaare", "saayabeku", "namaskara"
    )

    private val MALAYALAM_PHONETIC_KEYWORDS = listOf(
        "sahayam", "venam", "pedi", "thallunnu", "marikkanam", "namaskaram"
    )

    /**
     * Detects input language based on Unicode script blocks and characteristic phonetic keywords.
     */
    fun detectLanguage(text: String): AppLanguage {
        var tamilCount = 0
        var devanagariCount = 0
        var teluguCount = 0
        var kannadaCount = 0
        var malayalamCount = 0

        for (char in text) {
            val code = char.code
            when (code) {
                in 0x0B80..0x0BFF -> tamilCount++
                in 0x0900..0x097F -> devanagariCount++
                in 0x0C00..0x0C7F -> teluguCount++
                in 0x0C80..0x0CFF -> kannadaCount++
                in 0x0D00..0x0D7F -> malayalamCount++
            }
        }

        val maxIndic = maxOf(tamilCount, devanagariCount, teluguCount, kannadaCount, malayalamCount)
        if (maxIndic > 0) {
            return when {
                tamilCount == maxIndic -> AppLanguage.TAMIL
                devanagariCount == maxIndic -> AppLanguage.HINDI
                teluguCount == maxIndic -> AppLanguage.TELUGU
                kannadaCount == maxIndic -> AppLanguage.KANNADA
                malayalamCount == maxIndic -> AppLanguage.MALAYALAM
                else -> AppLanguage.ENGLISH
            }
        }

        // Check phonetic transliterations in Latin script
        val lower = text.lowercase()
        val words = lower.split(Regex("\\W+"))

        val tamilScore = words.count { w -> TAMIL_PHONETIC_KEYWORDS.any { w == it || w.startsWith(it) } }
        val hindiScore = words.count { w -> HINDI_PHONETIC_KEYWORDS.any { w == it || w.startsWith(it) } }
        val teluguScore = words.count { w -> TELUGU_PHONETIC_KEYWORDS.any { w == it || w.startsWith(it) } }
        val kannadaScore = words.count { w -> KANNADA_PHONETIC_KEYWORDS.any { w == it || w.startsWith(it) } }
        val malayalamScore = words.count { w -> MALAYALAM_PHONETIC_KEYWORDS.any { w == it || w.startsWith(it) } }

        val maxScore = maxOf(tamilScore, hindiScore, teluguScore, kannadaScore, malayalamScore)
        if (maxScore > 0) {
            return when {
                tamilScore == maxScore -> AppLanguage.TAMIL
                hindiScore == maxScore -> AppLanguage.HINDI
                teluguScore == maxScore -> AppLanguage.TELUGU
                kannadaScore == maxScore -> AppLanguage.KANNADA
                malayalamScore == maxScore -> AppLanguage.MALAYALAM
                else -> AppLanguage.ENGLISH
            }
        }

        return AppLanguage.ENGLISH
    }

    /**
     * Autonomous Crisis Classification & Emergency Channel Dispatch Engine.
     */
    fun routeIncident(userText: String, fallbackLanguage: AppLanguage = AppLanguage.ENGLISH): IntelligentSafetyResponse {
        val detected = detectLanguage(userText)
        val finalLang = if (detected != AppLanguage.ENGLISH) detected else fallbackLanguage
        val lower = userText.lowercase()

        // 1. MISSING PERSON / ABDUCTION
        if (containsAny(lower,
                "missing", "lost person", "abducted", "kidnapped", "not returned home", "cannot find", "disappeared",
                "காணவில்லை", "கடத்தப்பட்டார்", "வீட்டிற்கு வரவில்லை", "ஆளை காணோம்", "காணாமல் போன", "குழந்தையை காணோம்",
                "kanom", "kanavillai", "kadathitaanga", "veetuku varala", "missing child",
                "लापता", "गुमशुदा", "अपहरण", "घर नहीं लौटा", "गायब",
                "తప్పిపోయారు", "కిడ్నాప్", "ఇంటికి రాలేదు",
                "ಕಾಣೆಯಾಗಿದ್ದಾರೆ", "ಅಪಹರಣ", "ಮನೆಗೆ ಬಂದಿಲ್ಲ",
                "കാണാനില്ല", "തട്ടിക്കൊണ്ടുപോയി", "വീട്ടിൽ തിരിച്ചെത്തിയില്ല"
            )
        ) {
            return buildMissingPersonResponse(finalLang, userText)
        }

        // 2. MENTAL HEALTH CRISIS & SUICIDE PREVENTION
        if (containsAny(lower,
                "suicide", "suicidal", "kill myself", "die", "end my life", "depression", "hopeless", "panic attack", "self harm", "cutting", "overdose", "hanging",
                "தற்கொலை", "சாக வேண்டும்", "வாழ விருப்பமில்லை", "மன அழுத்தம்", "பயம்", "சுய தீங்கு", "உயிரை விட", "தூக்கு", "மாத்திரை", "அழுகிறேன்", "மன உளைச்சல்",
                "tharkolai", "saaganum", "vaazha virupamilla", "mana azhutham", "bayama irukku", "saganum pola irukku", "maranam", "cutting",
                "आत्महत्या", "मरना चाहता", "जान दे दूंगा", "डिप्रेशन", "उम्मीद नहीं", "खुद को नुकसान", "फांसी", "तनाव",
                "ఆత్మహత్య", "చనిపోవాలని", "మానసిక ఒత్తిడి",
                "ಆತ್ಮಹತ್ಯೆ", "ಸಾಯಲು", "ಮಾನಸಿಕ ಖಿನ್ನತೆ",
                "ആത്മഹത്യ", "മരിക്കാൻ", "വിഷാദം"
            )
        ) {
            return buildMentalHealthResponse(finalLang, userText)
        }

        // 3. HUMAN TRAFFICKING & FORCED EXPLOITATION
        if (containsAny(lower,
                "trafficking", "confined", "locked up", "passport confiscated", "forced labor", "brothel", "sold", "kidnapped for work",
                "ஆள் கடத்தல்", "அடைத்து வைக்கப்பட்டுள்ளார்", "பாஸ்போர்ட் பறிப்பு", "கட்டாய வேலை", "விற்பனை", "அடிமை",
                "kadathal", "adaithu", "passport", "kattaya velai",
                "मानव तस्करी", "बंधक बना लिया", "पासपोर्ट जब्त", "जबरन काम", "बेच दिया",
                "మానవ అక్రమ రవాణా", "బందీ", "పాస్‌పోర్ట్ స్వాధీనం",
                "ಮಾನವ ಕಳ್ಳಸಾಗಣೆ", "ಬಂಧನ", "ಪಾಸ್‌ಪೋರ್ಟ್ ಕಸಿದುಕೊಂಡಿದ್ದಾರೆ",
                "മനുഷ്യക്കടത്ത്", "തടങ്കലിൽ", "പാസ്പോർട്ട് പിടിച്ചുവെച്ചു"
            )
        ) {
            return buildHumanTraffickingResponse(finalLang, userText)
        }

        // 4. CHILD ABUSE & POCSO PROTECTION
        if (containsAny(lower,
                "child", "kid", "minor", "baby", "son", "daughter", "school boy", "school girl", "pocso", "molest", "bad touch", "child abuse",
                "குழந்தை", "சிறுமி", "சிறுவன்", "மகள்", "மகன்", "பாலியல்", "தவறான தொடுதல்", "பாதுகாப்பற்ற", "குழந்தைகள்",
                "kuzhandhai", "sirumi", "siruvan", "en magal", "en magan", "bad touch", "pocso", "molestation",
                "बच्चा", "बच्ची", "नाबालिग", "बेटा", "बेटी", "यौन शोषण", "गलत स्पर्श", "पॉक्सो",
                "పిల్లలు", "చిన్నారులు", "అసభ్య ప్రవర్తన",
                "ಮಕ್ಕಳು", "ಲೈಂಗಿಕ ದೌರ್ಜನ್ಯ",
                "കുട്ടികൾ", "ലൈംഗിക അതിക്രമം"
            )
        ) {
            return buildChildAbuseResponse(finalLang, userText)
        }

        // 5. CYBER CRIME & FINANCIAL FRAUD
        if (containsAny(lower,
                "hacked", "bank", "scam", "otp", "blackmail", "nude photo", "morph", "cyber", "phishing", "money stolen", "unauthorized transaction",
                "ஹேக்", "வங்கி", "பணம் பறிபோனது", "மோசடி", "மிரட்டல்", "புகைப்படம் மிரட்டல்", "சைபர்", "ஓடிபி", "பணம் ஏமாந்து",
                "panam poiruchu", "bank account", "hack", "nude photo", "miratran", "cyber crime", "otp share",
                "हैक", "बैंक खाता", "धोखाधड़ी", "ब्लैकमेल", "न्यूड फोटो", "पैसे कट गए", "साइबर अपराध", "ओटीपी",
                "హ్యాక్", "బ్యాంక్ ఖాతా", "మోసం", "బ్లాక్‌మెయిల్",
                "ಹ್ಯಾಕ್", "ಬ್ಯಾಂಕ್ ಖಾತೆ", "ವಂಚನೆ", "ಬ್ಲಾಕ್‌ಮೇಲ್",
                "ഹാക്ക്", "ബാങ്ക് അക്കൗണ്ട്", "തട്ടിപ്പ്", "ബ്ലാക്ക്മെയിൽ"
            )
        ) {
            return buildCyberCrimeResponse(finalLang, userText)
        }

        // 6. ELDER ABUSE & ABANDONMENT
        if (containsAny(lower,
                "elderly", "old parents", "abandoned", "denied food", "senior citizen", "property snatch", "grandma beaten", "grandpa",
                "முதியோர்", "வயதான பெற்றோர்", "உணவு தரவில்லை", "கைவிடப்பட்டார்", "சொத்து பறிப்பு", "தாத்தா", "பாட்டி",
                "periyavanga", "vayadhana", "thatha", "paatti", "senior citizen", "unavu tharala",
                "बुजुर्ग", "वृद्ध माता-पिता", "घर से निकाल दिया", "खाना नहीं देते", "संपत्ति छीन ली",
                "వృద్ధులు", "వృద్ధ తల్లిదండ్రులు", "నిరాదరణ",
                "ಹಿರಿಯರು", "ವಯಸ್ಸಾದ ಪೋಷಕರು", "ಆಸ್ತಿ ಕಸಿದುಕೊಂಡಿದ್ದಾರೆ",
                "മുതിർന്നവർ", "പ്രായമായ മാതാപിതാക്കൾ", "ഭക്ഷണം നൽകുന്നില്ല"
            )
        ) {
            return buildElderAbuseResponse(finalLang, userText)
        }

        // 7. SCHOOL / COLLEGE RAGGING & BULLYING
        if (containsAny(lower,
                "ragging", "hostel bullying", "senior threat", "forced to do humiliating", "college torture", "college harassment",
                "ராகிங்", "ஹாஸ்டல் அச்சுறுத்தல்", "சீனியர் மிரட்டல்", "கல்லூரி கொடுமை", "கல்லூரி", "பள்ளி மிரட்டல்",
                "ragging panranga", "hostel la thollai", "senior miratran", "college ragging",
                "रैगिंग", "हॉस्टल प्रताड़ना", "सीनियर की धमकी", "कॉलेज में बदसलूकी",
                "ర్యాగింగ్", "హాస్టల్ వేధింపులు",
                "ರ‍್ಯಾಗಿಂಗ್", "ಹಾಸ್ಟೆಲ್ ಕಿರುಕುಳ",
                "റാഗിംഗ്", "ഹോസ്റ്റൽ പീഡനം"
            )
        ) {
            return buildSchoolHarassmentResponse(finalLang, userText)
        }

        // 8. WORKPLACE HARASSMENT (POSH)
        if (containsAny(lower,
                "boss", "manager", "workplace harassment", "quid pro quo", "sexual harassment office", "threatens job", "posh committee", "office harassment", "workplace",
                "பணியிடம்", "மேலாளர்", "வேலை மிரட்டல்", "பாலியல் தொல்லை அலுவலகம்", "அலுவலகம்", "வேலை பறிப்பு",
                "boss thollai", "manager threat", "office harassment", "velai la prachanai", "posh",
                "कार्यस्थल", "बॉस", "नौकरी से निकालने की धमकी", "यौन उत्पीड़न ऑफिस",
                "ఆఫీస్ వేధింపులు", "మేనేజర్ బెదిరింపు",
                "ಕೆಲಸದ ಸ್ಥಳದಲ್ಲಿ ಕಿರುಕುಳ", "ಬಾಸ್ ಬೆದರಿಕೆ",
                "ഓഫീസ് പീഡനം", "തൊഴിൽ ഭീഷണി"
            )
        ) {
            return buildWorkplaceHarassmentResponse(finalLang, userText)
        }

        // 9. DOMESTIC VIOLENCE & ABUSE
        if (containsAny(lower,
                "husband", "wife", "beating", "beat me", "domestic", "in-laws", "violence at home", "slapped", "torture", "dowry", "threatens to kill", "marital",
                "கணவர்", "அடிக்கிறார்", "அடி", "குடும்ப", "மாமியார்", "வரதட்சணை", "துன்புறுத்தல்", "கொன்றுவிடுவேன்", "வீட்டில் கொடுமை",
                "kanavar adikkirar", "veetla adikranga", "mamiyar", "dowry", "kodumai", "thitturar",
                "पति", "मारपीट", "घरेलू", "ससुराल", "दहेज", "प्रताड़ना", "मारता", "जान से मारने",
                "భర్త", "కొడుతున్నారు", "గృహ", "అత్తగారు", "వరకట్నం", "హింస",
                "ಗಂಡ", "ಹೊಡೆಯುತ್ತಾನೆ", "ದೌರ್ಜನ್ಯ", "ವರದಕ್ಷಿಣೆ", "ಹಿಂಸೆ",
                "ഭർത്താവ്", "മർദ്ദിക്കുന്നു", "പീഡനം", "സ്ത്രീധനം", "ഗാർഹിക"
            )
        ) {
            return buildDomesticViolenceResponse(finalLang, userText)
        }

        // 10. WOMEN SAFETY & PUBLIC STALKING
        if (containsAny(lower,
                "stalking", "following me", "street harassment", "eve teasing", "isolated road", "cab driver threat", "unsafe cab", "touching inappropriately",
                "பின்தொடர்கிறார்", "தெருவில் தொல்லை", "பாதுகாப்பற்ற வழி", "ஆட்டோ/டாக்ஸி மிரட்டல்", "துரத்துகிறார்", "பயமா இருக்கு",
                "pin thodargirar", "theruvil thollai", "unsafe road", "auto driver threat", "stalking",
                "पीछा कर रहा", "छेड़छाड़", "अकेली सड़क", "टैक्सी ड्राइवर धमकी", "गलत हरकत",
                "వెంబడిస్తున్నారు", "ఈవ్ టీజింగ్", "నిర్జన ప్రదేశం",
                "ಹಿಂಬಾಲಿಸುತ್ತಿದ್ದಾರೆ", "ರಸ್ತೆ ಕಿರುಕುಳ", "ಅಸುರಕ್ಷಿತ",
                "പിന്തുടരുന്നു", "ശല്യം ചെയ്യുന്നു", "സുരക്ഷിതമല്ലാത്ത വഴി"
            )
        ) {
            return buildWomenSafetyResponse(finalLang, userText)
        }

        // DEFAULT: Intelligent General Emergency Triage
        return buildGeneralTriageResponse(finalLang, userText)
    }

    private fun containsAny(text: String, vararg keywords: String): Boolean {
        for (kw in keywords) {
            if (text.contains(kw.lowercase())) return true
        }
        return false
    }

    // ==========================================
    // 1. DOMESTIC VIOLENCE SCENARIO BUILDER
    // ==========================================
    private fun buildDomesticViolenceResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "பெண்கள் உதவி எண் (1091 / சகி 181)"
                AppLanguage.HINDI -> "महिला हेल्पलाइन (1091 / 181)"
                AppLanguage.TELUGU -> "మహిళా హెల్ప్‌లైన్ (1091 / 181)"
                AppLanguage.KANNADA -> "ಮಹಿಳಾ ಸಹಾಯವಾಣಿ (1091 / 181)"
                AppLanguage.MALAYALAM -> "വനിതാ ഹെൽപ്പ് ലൈൻ (1091 / 181)"
                else -> "Women Helpline (1091 / Sakhi 181)"
            },
            number = "1091",
            agencyName = when (lang) {
                AppLanguage.TAMIL -> "தேசிய பெண்கள் ஆணையம் & மகளிர் பாதுகாப்பு பிரிவு"
                else -> "National Commission for Women / State WCD"
            },
            isPrimary = true,
            badge = "1-TAP CALL",
            description = when (lang) {
                AppLanguage.TAMIL -> "உடனடி மீட்பு, இலவச தங்குமிடம், மருத்துவ உதவி மற்றும் பாதுகாப்பு சட்டம் (PWDVA 2005)."
                AppLanguage.HINDI -> "घरेलू हिंसा से तत्काल बचाव, आश्रय गृह एवं मुफ्त कानूनी सहायता।"
                else -> "Immediate rescue, shelter home placement, and protective counseling under PWDVA 2005."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "அவசர காவல் கட்டுப்பாட்டு அறை (112)"
                AppLanguage.HINDI -> "आपातकालीन पुलिस (112)"
                AppLanguage.TELUGU -> "పోలీస్ అత్యవసర విభాగం (112)"
                AppLanguage.KANNADA -> "ಪೊಲೀಸ್ ತುರ್ತು ನಿಯಂತ್ರಣ (112)"
                AppLanguage.MALAYALAM -> "പോലീസ് കൺട്രോൾ റൂം (112)"
                else -> "Emergency Police Dispatch (112)"
            },
            number = "112",
            agencyName = when (lang) {
                AppLanguage.TAMIL -> "மாநில அவசர காவல் ரோந்து அமைப்பு"
                else -> "State Police Emergency Response System"
            },
            isPrimary = false,
            badge = "POLICE SOS",
            description = when (lang) {
                AppLanguage.TAMIL -> "லைவ் ஜி.பி.எஸ் (Live GPS) இருப்பிடத்துடன் பி.சி.ஆர் ரோந்து வாகனம் உடனடி வருகை."
                else -> "Direct PCR van dispatch with live GPS beacon."
            }
        )

        val reply = when (lang) {
            AppLanguage.TAMIL -> "உங்கள் பாதுகாப்பு எங்கள் முதல் முன்னுரிமை. உடனடியாக பாதுகாப்பான அறைக்குள் செல்லுங்கள் அல்லது அண்டை வீட்டாரிடம் செல்லுங்கள். நீங்கள் தனிமையில் இல்லை — பெண்கள் உதவி எண் 1091 மற்றும் காவல் கட்டுப்பாட்டு அறை 112 தயாராக உள்ளன. கீழே உள்ள பொத்தானை அழுத்தி உடனடியாக பேசலாம் அல்லது ரகசிய புகார் பதிவு செய்யலாம்."
            AppLanguage.HINDI -> "आपकी सुरक्षा हमारी सर्वोच्च प्राथमिकता है। कृपया तुरंत किसी सुरक्षित कमरे या स्थान पर जाएं। आप अकेली नहीं हैं — महिला हेल्पलाइन 1091 और आपातकालीन पुलिस 112 तुरंत सहायता के लिए उपलब्ध हैं।"
            AppLanguage.TELUGU -> "మీ భద్రతే మా ప్రథమ ప్రాధాన్యత. దయచేసి వెంటనే సురక్షితమైన ప్రదేశానికి వెళ్ళండి. మహిళా హెల్ప్‌లైన్ 1091 మరియు పోలీస్ 112 మీకు సహాయం చేయడానికి సిద్ధంగా ఉన్నాయి."
            AppLanguage.KANNADA -> "ನಿಮ್ಮ ಸುರಕ್ಷತೆಯೇ ನಮ್ಮ ಮೊದಲ ಆದ್ಯತೆ. ದಯವಿಟ್ಟು ತಕ್ಷಣ ಸುರಕ್ಷಿತ ಸ್ಥಳಕ್ಕೆ ತೆರಳಿ. ಮಹಿಳಾ ಸಹಾಯವಾಣಿ 1091 ಮತ್ತು ಪೊಲೀಸ್ 112 ಲಭ್ಯವಿದೆ."
            AppLanguage.MALAYALAM -> "നിങ്ങളുടെ സുരക്ഷയാണ് ഞങ്ങൾക്ക് പ്രധാനം. ഉടൻ തന്നെ സുരക്ഷിതമായ ഒരു സ്ഥലത്തേക്ക് മാറുക. വനിതാ ഹെൽപ്പ് ലൈൻ 1091, പോലീസ് 112 എന്നിവ ലഭ്യമാണ്."
            else -> "Your safety is our top priority. Please move to a secure room or exit the premises if safe. You are not alone. Direct dispatch to Women Helpline (1091) and Police (112) is prioritized below."
        }

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.DOMESTIC_VIOLENCE,
            riskLevel = SafetyRiskLevel.HIGH,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "கதவை உள்பக்கமாக பூட்டுங்கள் அல்லது அண்டை வீட்டாரிடம் செல்லுங்கள். தேவைப்பட்டால் கால்குலேட்டர் உருமறைப்பு பயன்முறையைப் பயன்படுத்தவும்."
                AppLanguage.HINDI -> "कमरा अंदर से बंद करें या पड़ोसियों से मदद लें। यदि आवश्यक हो तो कैलकुलेटर कैमोफ्लेज मोड चालू करें।"
                else -> "Lock yourself in a secure room or seek immediate neighbor assistance. Enable Calculator Camouflage if your device is monitored."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "குடும்ப வன்முறை தடுப்புச் சட்டம் (PWDVA 2005 - பிரிவு 18-22): இலவச மருத்துவ சிகிச்சை, அவசர தங்குமிடம், வீட்டிலிருந்து வெளியேற்றப்படாமல் இருப்பதற்கான உரிமை மற்றும் பிணையில் வெளிவர முடியாத பாதுகாப்பு உத்தரவுகள்."
                AppLanguage.HINDI -> "घरेलू हिंसा संरक्षण कानून (PWDVA 2005 धारा 18-22): मुफ्त चिकित्सीय सहायता, आपातकालीन आश्रय और निष्कासन के खिलाफ सुरक्षा का अधिकार।"
                else -> "Section 18-22 PWDVA 2005: Right to free medical aid, emergency shelter, protection against eviction, and immediate non-bailable restraint orders."
            },
            conversationalReply = reply,
            escalationRequired = true,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("அழைப்பு 1091", "காவல் உதவி 112", "சான்றை சேமி", "உருமறைப்பு பயன்முறை")
                AppLanguage.HINDI -> listOf("1091 पर कॉल करें", "पुलिस 112", "साक्ष्य सुरक्षित करें", "कैमोफ्लेज मोड")
                else -> listOf("Call 1091 Now", "Police Dispatch 112", "Record Vault Evidence", "Camouflage Screen")
            }
        )
    }

    // ==========================================
    // 2. CHILD ABUSE & POCSO BUILDER
    // ==========================================
    private fun buildChildAbuseResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "சைல்டுலைன் குழந்தைகள் அவசர உதவி (1098)"
                AppLanguage.HINDI -> "चाइल्डलाइन आपातकालीन (1098)"
                AppLanguage.TELUGU -> "చైల్డ్‌లైన్ అత్యవసర సహాయం (1098)"
                AppLanguage.KANNADA -> "ಚೈಲ್ಡ್‌ಲೈನ್ ತುರ್ತು ನೆರವು (1098)"
                AppLanguage.MALAYALAM -> "ചൈൽഡ് ലൈൻ അടിയന്തര സഹായം (1098)"
                else -> "Childline India (1098)"
            },
            number = "1098",
            agencyName = when (lang) {
                AppLanguage.TAMIL -> "மத்திய பெண்கள் & குழந்தைகள் மேம்பாட்டு அமைச்சகம்"
                else -> "Ministry of Women & Child Development (MWCD)"
            },
            isPrimary = true,
            badge = "CHILD RESCUE",
            description = when (lang) {
                AppLanguage.TAMIL -> "உடனடி குழந்தை மீட்பு, குழந்தைகள் நலக் குழு (CWC) பாதுகாப்பு மற்றும் POCSO சட்ட நடவடிக்கை."
                else -> "Immediate child rescue, child welfare committee (CWC) custody, and mandatory POCSO reporting."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "சிறப்பு குழந்தைகள் பாதுகாப்பு காவல் பிரிவு (112)"
                else -> "Police Cyber & Child Protection (112)"
            },
            number = "112",
            agencyName = "Special Juvenile Police Unit (SJPU)",
            isPrimary = false,
            badge = "SPECIAL UNIT",
            description = "Mandatory 24-hour statutory FIR under Section 19 POCSO Act."
        )

        val reply = when (lang) {
            AppLanguage.TAMIL -> "குழந்தையின் பாதுகாப்பு மிக முக்கியமானது. POCSO சட்டம் மற்றும் சைல்டுலைன் 1098 உடனடியாக குழந்தையை மீட்கவும் பாதுகாக்கவும் நடவடிக்கை எடுக்கும். புகார் அளிப்பவர் மற்றும் குழந்தையின் விவரங்கள் 100% ரகசியமாக வைக்கப்படும்."
            AppLanguage.HINDI -> "बच्चे की सुरक्षा सर्वोपरि है। चाइल्डलाइन 1098 और पॉक्सो (POCSO) संरक्षण दल बच्चे की तत्काल सुरक्षा और पुनर्वास के लिए सक्रिय हैं।"
            else -> "Child protection alert escalated to Critical. Childline (1098) and Special Juvenile Police Units have statutory duty to intervene within 60 minutes under POCSO Act 2012."
        }

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.CHILD_ABUSE,
            riskLevel = SafetyRiskLevel.CRITICAL,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "குழந்தையை அச்சுறுத்தும் நபரிடமிருந்து உடனடியாக தனிமைப்படுத்துங்கள். ஆதாரங்கள் அல்லது மருத்துவ சான்றுகளை அழிக்க வேண்டாம்."
                else -> "Isolate the child immediately from the alleged perpetrator. Do not erase chat logs or medical evidence."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "போக்சோ சட்டம் 2012 (பிரிவு 19): குழந்தைகளுக்கு எதிரான வன்முறையை புகாரளிப்பது சட்டப்படி கட்டாயமானது. தகவல் தருபவருக்கு முழு சட்ட பாதுகாப்பு உண்டு."
                else -> "POCSO Act 2012 & Section 19: Mandatory reporting with absolute whistleblower immunity and state-funded rehabilitation."
            },
            conversationalReply = reply,
            escalationRequired = true,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("சைல்டுலைன் 1098", "காவல்துறை 112", "சான்றை சேமி", "சட்ட உதவி 15100")
                else -> listOf("Call Childline 1098", "Police Emergency 112", "Vault Evidence Item", "Legal Aid 15100")
            }
        )
    }

    // ==========================================
    // 3. MENTAL HEALTH & SUICIDE PREVENTION
    // ==========================================
    private fun buildMentalHealthResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "டெலி-மானாஸ் மனநல உதவி எண் (14416)"
                AppLanguage.HINDI -> "टेली-मानस मानसिक स्वास्थ्य (14416)"
                AppLanguage.TELUGU -> "టెలి-మానస్ సంక్షోభ హెల్ప్‌లైన్ (14416)"
                AppLanguage.KANNADA -> "ಟೆಲಿ-ಮಾನಸ್ ಸಹಾಯವಾಣಿ (14416)"
                AppLanguage.MALAYALAM -> "ടെലി-മാനസ് ഹെൽപ്പ് ലൈൻ (14416)"
                else -> "Tele-MANAS Crisis Line (14416)"
            },
            number = "14416",
            agencyName = "Ministry of Health & NIMHANS 24x7 Triage",
            isPrimary = true,
            badge = "FREE & ANONYMOUS",
            description = when (lang) {
                AppLanguage.TAMIL -> "24 மணி நேரமும் தமிழ் மற்றும் அனைத்து மொழிகளிலும் இலவச ரகசிய மனநல ஆலோசனை மற்றும் ஆதரவு."
                else -> "Direct psychiatric de-escalation, counselor support in regional languages, zero police escalation."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "கிரண் மனநல மறுவாழ்வு உதவி (1800-599-0019)"
                else -> "Kiran Mental Health Helpline (1800-599-0019)"
            },
            number = "1800-599-0019",
            agencyName = "Department of Empowerment of Persons with Disabilities",
            isPrimary = false,
            badge = "24x7 COUNSELING",
            description = "Confidential psychological first-aid and emotional grounding."
        )

        val reply = when (lang) {
            AppLanguage.TAMIL -> "நீங்கள் தனியாக இல்லை. இந்த கடினமான தருணத்தில் நாங்கள் உங்களுடன் இருக்கிறோம். தயவுசெய்து ஆழமாக மூச்சை உள்ளிழுங்கள். டெலி-மானாஸ் (14416) நிபுணர்கள் உங்களிடம் இலவசமாகவும் 100% ரகசியமாகவும் தமிழில் பேச 24 மணி நேரமும் தயாராக உள்ளனர். உங்கள் உயிர் மிகவும் மதிப்புமிக்கது."
            AppLanguage.HINDI -> "आप बिल्कुल अकेले नहीं हैं। इस मुश्किल समय में हम आपके साथ हैं। कृपया एक गहरी सांस लें। टेली-मानस (14416) के विशेषज्ञ आपसे गोपनीय रूप से बात करने के लिए 24 घंटे उपलब्ध हैं।"
            AppLanguage.TELUGU -> "మీరు ఒంటరిగా లేరు. ఈ కష్ట సమయంలో మేము మీకు అండగా ఉంటాము. టెలి-మానస్ (14416) కౌన్సిలర్లు మీకు సహాయం చేయడానికి సిద్ధంగా ఉన్నారు."
            AppLanguage.KANNADA -> "ನೀವು ಒಬ್ಬಂಟಿಯಲ್ಲ. ನಾವು ನಿಮ್ಮ ಜೊತೆಗಿದ್ದೇವೆ. ಟೆಲಿ-ಮಾನಸ್ (14416) ಕೌನ್ಸಿಲರ್‌ಗಳು ಸಹಾಯ ಮಾಡಲು ಸಿದ್ಧರಾಗಿದ್ದಾರೆ."
            AppLanguage.MALAYALAM -> "നിങ്ങൾ തനിച്ചല്ല. ടെലി-മാനസ് (14416) കൗൺസിലർമാർ നിങ്ങളെ സഹായിക്കാൻ 24 മണിക്കൂറും സജ്ജമാണ്."
            else -> "You are not alone, and there is genuine support for you right now. Take a slow, deep breath. Verified clinical counselors on Tele-MANAS (14416) are waiting to listen without any judgment."
        }

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.MENTAL_HEALTH,
            riskLevel = SafetyRiskLevel.CRITICAL,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "ஒரு கிளாஸ் குளிர்ந்த தண்ணீர் குடியுங்கள். 4 வினாடிகள் மூச்சை உள்ளிழுத்து மெதுவாக வெளிவிடுங்கள். 14416 எண்ணை அழைத்து ஒருமுறை பேசுங்கள்."
                else -> "Drink a glass of water. Stay in an open room and speak to a dedicated crisis counselor on 14416 right now."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "மனநலப் பராமரிப்புச் சட்டம் 2017 (பிரிவு 115): மருத்துவ சிகிச்சை பெறுவது உங்கள் அடிப்படை உரிமை. எந்தவித குற்றவியல் நடவடிக்கையும் கிடையாது, முழு மருத்துவ ரகசியம் காக்கப்படும்."
                else -> "Mental Healthcare Act 2017 (Section 115): Right to compassionate healthcare, zero criminal charges, and strict medical confidentiality."
            },
            conversationalReply = reply,
            escalationRequired = false,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("டெலி-மானாஸ் 14416", "கிரண் 1800-599-0019", "சுவாச பயிற்சி", "ரகசிய அரட்டை")
                else -> listOf("Dial Tele-MANAS 14416", "Call Kiran 1800-599-0019", "Breathing Exercise", "Anonymous Chat")
            }
        )
    }

    // ==========================================
    // 4. CYBER CRIME & FINANCIAL FRAUD
    // ==========================================
    private fun buildCyberCrimeResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "தேசிய சைபர் கிரைம் உதவி எண் (1930)"
                AppLanguage.HINDI -> "राष्ट्रीय साइबर अपराध हेल्पलाइन (1930)"
                AppLanguage.TELUGU -> "జాతీయ సైబర్ క్రైమ్ హెల్ప్‌లైన్ (1930)"
                AppLanguage.KANNADA -> "ರಾಷ್ಟ್ರೀಯ ಸೈಬರ್ ಅಪರಾಧ ಸಹಾಯವಾಣಿ (1930)"
                AppLanguage.MALAYALAM -> "ദേശീയ സൈബർ കുറ്റകൃത്യ ഹെൽപ്പ് ലൈൻ (1930)"
                else -> "National Cyber Crime Helpline (1930)"
            },
            number = "1930",
            agencyName = "Indian Cyber Crime Coordination Centre (I4C)",
            isPrimary = true,
            badge = "ACCOUNT FREEZE",
            description = when (lang) {
                AppLanguage.TAMIL -> "தங்க மணிநேர (Golden Hour) நடவடிக்கை: மோசடி செய்யப்பட்ட தொகையை வங்கிகளுக்கு இடையே உடனடியாக முடக்குதல்."
                else -> "Golden Hour fraud intervention: Triggers immediate inter-bank holding lien to freeze transferred money."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "சைபர் கிரைம் புகார் போர்டல் (cybercrime.gov.in)"
                else -> "National Cyber Crime Reporting Portal"
            },
            number = "1930",
            agencyName = "cybercrime.gov.in",
            isPrimary = false,
            badge = "E-COMPLAINT",
            description = "Formal FIR for online blackmail, morphing, and non-consensual imagery."
        )

        val reply = when (lang) {
            AppLanguage.TAMIL -> "நிதி மோசடி அல்லது சைபர் மிரட்டல் ஏற்பட்டால், முதல் 'Golden Hour' மிகவும் முக்கியமானது. உடனே 1930 எண்ணை அழைத்து வங்கி பரிவர்த்தனையை முடக்கவும். ஆதாரங்களை அழிக்காமல் எங்களின் என்க்ரிப்ட் செய்யப்பட்ட Vault-ல் பத்திரப்படுத்துங்கள்."
            AppLanguage.HINDI -> "साइबर धोखाधड़ी या ब्लैकमेल के मामलों में शुरुआती 'गोल्डन आवर' बेहद महत्वपूर्ण है। तुरंत 1930 पर कॉल करके बैंक खातों में ट्रांजैक्शन फ्रीज करवाएं।"
            else -> "Immediate action required for Cyber Financial Fraud & Extortion. Call 1930 immediately to trigger the Citizen Financial Cyber Fraud Reporting System to freeze stolen funds in transit."
        }

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.CYBER_CRIME,
            riskLevel = SafetyRiskLevel.MEDIUM,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "1930-ஐ உடனே அழைக்கவும். வங்கி கடவுச்சொல்லை மாற்றவும். மிரட்டல் ஸ்கிரீன்ஷாட்களை அழிக்காமல் பாதுகாக்கவும்."
                else -> "Call 1930 immediately. Freeze ATM/NetBanking credentials. Do not delete chat logs or extortion messages."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "தகவல் தொழில்நுட்ப சட்டம் 2000 (பிரிவு 66C, 66D, 66E, 67A): ஆன்லைன் மிரட்டல் மற்றும் பண மோசடிக்கு கடுமையான பிணையில்லா தண்டனைகள்."
                else -> "Information Technology Act 2000 (Sec 66C, 66D, 66E, 67A) & Bharatiya Nyaya Sanhita (Sec 318): Strict non-bailable fraud & privacy violation penalties."
            },
            conversationalReply = reply,
            escalationRequired = false,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("சைபர் செல் 1930", "சான்றை பெட்டகத்தில் பூட்டு", "கணக்கு முடக்கம்", "போலீஸ் 112")
                else -> listOf("Call 1930 Cyber Cell", "Seal Evidence in Vault", "Bank Account Freeze", "File Formal E-Report")
            }
        )
    }

    // ==========================================
    // 5. HUMAN TRAFFICKING & EXPLOITATION
    // ==========================================
    private fun buildHumanTraffickingResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "ஆள் கடத்தல் தடுப்பு சிறப்பு படை (AHTU - 112)"
                else -> "Anti-Human Trafficking Unit (AHTU - 112)"
            },
            number = "112",
            agencyName = "Ministry of Home Affairs AHTU Units",
            isPrimary = true,
            badge = "STATUTORY RESCUE",
            description = "Coordinated state anti-trafficking taskforce for immediate raid, cordoning, and victim extraction."
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "தேசிய மனித உரிமைகள் ஆணையம் (NHRC 14433)"
                else -> "National Human Rights Commission (NHRC)"
            },
            number = "14433",
            agencyName = "NHRC Victim Protection Cell",
            isPrimary = false,
            badge = "LEGAL CELL",
            description = "Inter-state coordination and bonded labor liberation."
        )

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.HUMAN_TRAFFICKING,
            riskLevel = SafetyRiskLevel.CRITICAL,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "கடத்தல்காரர்களிடம் நேரடியாக மோத வேண்டாம். உங்கள் அமைதியான ஜி.பி.எஸ் சிக்னலை அனுப்பிவிட்டு உருமறைப்பு பயன்முறையை இயக்கவும்."
                else -> "Do not confront captors. Transmit your silent GPS beacon and use disguised calculator mode."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "இந்திய அரசியலமைப்பு பிரிவு 23 & பி.என்.எஸ் பிரிவு 143: ஆள் கடத்தல் மற்றும் கட்டாய உழைப்பு முழுமையாக தடை செய்யப்பட்டுள்ளது."
                else -> "Article 23 Indian Constitution & Sec 143 Bharatiya Nyaya Sanhita: Total prohibition of trafficking with mandatory state compensation."
            },
            conversationalReply = when (lang) {
                AppLanguage.TAMIL -> "ஆள் கடத்தல் அல்லது கட்டாய உழைப்பு விவகாரத்தில் உங்களின் இருப்பிடம் மற்றும் அடையாளம் மிக ரகசியமாக வைக்கப்படும். சிறப்பு AHTU பிரிவுக்கு தகவல் அனுப்பப்பட்டுள்ளது."
                else -> "Trafficking alert registered at Critical Severity. Silent emergency beacon and GPS coordinates can be dispatched directly to Anti-Human Trafficking Units."
            },
            escalationRequired = true,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("காவல் உதவி 112", "ஜி.பி.எஸ் அனுப்பு", "உருமறைப்பு திரை", "சான்று பெட்டகம்")
                else -> listOf("Silent Police 112", "Send Live GPS", "Camouflage Screen", "Evidence Vault")
            }
        )
    }

    // ==========================================
    // 6. MISSING PERSON / ABDUCTION
    // ==========================================
    private fun buildMissingPersonResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "காணாமல் போனவர்கள் உதவி எண் (1094)"
                else -> "Missing Persons Helpline (1094)"
            },
            number = "1094",
            agencyName = "National Missing Persons Bureau & TrackChild",
            isPrimary = true,
            badge = "ALL-POINTS BULLETIN",
            description = "Immediate broadcast across police checkpoints, railway stations, and automated facial match systems."
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "காவல் கட்டுப்பாட்டு அறை (112)"
                else -> "Police Emergency Response Control (112)"
            },
            number = "112",
            agencyName = "Emergency Response Support System (ERSS)",
            isPrimary = false,
            badge = "POLICE DISPATCH",
            description = "Direct police radio broadcast and local patrol dispatch."
        )

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.MISSING_PERSON,
            riskLevel = SafetyRiskLevel.HIGH,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "கடைசியாக பார்த்த இடம், புகைப்படம், அணிந்திருந்த ஆடை மற்றும் மொபைல் IMEI எண்ணை உடனடியாக காவல் கட்டுப்பாட்டு அறைக்கு வழங்கவும். 24 மணி நேரம் காத்திருக்க வேண்டிய அவசியமில்லை."
                else -> "Provide last known location, photo, clothes worn, and phone IMEI to police immediately (no 24-hour waiting rule)."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "உச்ச நீதிமன்ற உத்தரவு: காணாமல் போனவர்கள் வழக்கில் 24 மணி நேர காத்திருப்பு விதி கிடையாது; தகவல் கிடைத்த உடனே காவல் துறை வழக்கு பதிவு செய்ய வேண்டும்."
                else -> "Supreme Court Mandate (Bachpan Bachao Andolan vs UOI): Zero waiting period. Police must register FIR immediately upon report."
            },
            conversationalReply = when (lang) {
                AppLanguage.TAMIL -> "காணாமல் போனவர்கள் குறித்து புகார் அளிக்க 24 மணி நேரம் காத்திருக்க வேண்டிய அவசியமில்லை. காவல் கட்டுப்பாட்டு அறை 112 மற்றும் 1094 உடனடியாக தேடுதல் வேட்டையை தொடங்கும்."
                else -> "Immediate Missing Person protocol activated. Indian law mandates immediate FIR without any 24-hour waiting delay."
            },
            escalationRequired = true,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("உதவி எண் 1094", "காவல்துறை 112", "ஜி.பி.எஸ் பகிர்வு", "புகார் பதிவு")
                else -> listOf("Missing Helpline 1094", "Police Control 112", "Share Last Known GPS", "File Missing Case")
            }
        )
    }

    // ==========================================
    // 7. WOMEN SAFETY & STREET STALKING
    // ==========================================
    private fun buildWomenSafetyResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "பெண்கள் பாதுகாப்பு உதவி எண் (1091)"
                AppLanguage.HINDI -> "महिला सुरक्षा हेल्पलाइन (1091)"
                AppLanguage.TELUGU -> "మహిళా రక్షణ హెల్ప్‌లైన్ (1091)"
                AppLanguage.KANNADA -> "ಮಹಿಳಾ ಸುರಕ್ಷತಾ ಸಹಾಯವಾಣಿ (1091)"
                AppLanguage.MALAYALAM -> "വനിതാ സുരക്ഷാ ഹെൽപ്പ് ലൈൻ (1091)"
                else -> "Women Safety Helpline (1091)"
            },
            number = "1091",
            agencyName = when (lang) {
                AppLanguage.TAMIL -> "மகளிர் காவல் ரோந்து படை (Pink Patrol)"
                else -> "State Women Police Patrol Units"
            },
            isPrimary = true,
            badge = "RAPID PATROL",
            description = when (lang) {
                AppLanguage.TAMIL -> "அருகிலுள்ள பிங்க் ரோந்து படை அல்லது நிர்பயா வாகனம் மூலம் உடனடி உதவி."
                else -> "Immediate interception by nearest mobile Pink Patrol / Nirbhaya squad."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "தேசிய அவசர காவல் மீட்பு (112)"
                else -> "National Emergency Integrated Response (112)"
            },
            number = "112",
            agencyName = "Emergency Response Support System (ERSS)",
            isPrimary = false,
            badge = "LIVE GPS DISPATCH",
            description = "PCR Van dispatched with live GPS satellite tracking."
        )

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.WOMEN_SAFETY,
            riskLevel = SafetyRiskLevel.HIGH,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "மக்கள் நடமாட்டம் உள்ள வெளிச்சமான இடத்திற்கு (கடைகள், மெட்ரோ, பெட்ரோல் பங்க்) செல்லுங்கள். உங்கள் குடும்பத்தினருக்கு லைவ் ஜி.பி.எஸ்-ஐ பகிருங்கள்."
                else -> "Move towards well-lit public areas (shops, metro station, fuel pump). Share your live GPS with trusted contacts below."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "பாரதிய நியாய சன்ஹிதா பிரிவு 78 (பின்தொடர்தல்) & பிரிவு 74: பெண்களுக்கு எதிரான அச்சுறுத்தல் பிணையில்லா கடுமையான குற்றமாகும்."
                else -> "Section 78 Bharatiya Nyaya Sanhita (Stalking) & Sec 74 (Assault/Criminal force against woman): Cognizable & non-bailable offense."
            },
            conversationalReply = when (lang) {
                AppLanguage.TAMIL -> "பயப்பட வேண்டாம். மக்கள் நடமாட்டம் உள்ள பொது இடத்திற்கு செல்லுங்கள். உங்கள் லைவ் ஜி.பி.எஸ் (Live GPS) இருப்பிடத்தை உடனடியாக உங்கள் குடும்பத்தினருக்கும் 1091 மகளிர் ரோந்து காவல்துறைக்கும் அனுப்பலாம்."
                else -> "Move immediately to a crowded, well-lit public space. Your live GPS tracking is active and 1-tap dispatch to Pink Patrol (1091) is ready below."
            },
            escalationRequired = true,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("ரோந்து படை 1091", "காவல்துறை 112", "பாதுகாப்பு டைமர்", "ஜி.பி.எஸ் எஸ்எம்எஸ்")
                else -> listOf("Call 1091 Patrol", "Dispatch Police 112", "Start Safety Timer", "Broadcast GPS SMS")
            }
        )
    }

    // ==========================================
    // 8. ELDER ABUSE & ABANDONMENT
    // ==========================================
    private fun buildElderAbuseResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "எல்டர்லைன் முதியோர் உதவி எண் (14567)"
                AppLanguage.HINDI -> "एल्डरलाइन वरिष्ठ नागरिक हेल्पलाइन (14567)"
                else -> "Elderline National Helpline (14567)"
            },
            number = "14567",
            agencyName = "Ministry of Social Justice & Empowerment",
            isPrimary = true,
            badge = "SENIOR CARE",
            description = when (lang) {
                AppLanguage.TAMIL -> "முதியோர் மீட்பு, முதியோர் இல்ல மறுவாழ்வு மற்றும் இலவச பராமரிப்பு தீர்ப்பாய மனு தாக்கல்."
                else -> "Field rescue, old age home rehabilitation, and free maintenance tribunal filing."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "நல்சா இலவச முதியோர் சட்ட உதவி (15100)"
                else -> "NALSA Free Senior Legal Aid (15100)"
            },
            number = "15100",
            agencyName = "National Legal Services Authority",
            isPrimary = false,
            badge = "LEGAL AID",
            description = "Free government advocate for property restoration."
        )

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.ELDER_ABUSE,
            riskLevel = SafetyRiskLevel.MEDIUM,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "எல்டர்லைன் அதிகாரிகள் நேரடியாக வந்து உணவு, தங்குமிடம் மற்றும் மருத்துவ பரிசோதனை வசதிகளை செய்து தருவார்கள்."
                else -> "Elderline field officers can conduct on-site welfare checks and provide food, shelter, and medical assistance."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "பெற்றோர் மற்றும் மூத்த குடிமக்கள் பராமரிப்பு & நலன் சட்டம் 2007: கட்டாய மாதாந்திர பராமரிப்பு தொகை மற்றும் ஏமாற்றி பெறப்பட்ட சொத்து பத்திரங்களை ரத்து செய்யும் அதிகாரம்."
                else -> "Maintenance and Welfare of Parents and Senior Citizens Act 2007: Mandatory monthly maintenance and cancellation of property gift deeds obtained via coercion."
            },
            conversationalReply = when (lang) {
                AppLanguage.TAMIL -> "முதியோர் நலன் மற்றும் உரிமைகள் சட்டம் 2007-ன் படி முதியோரை கைவிடுவது அல்லது துன்புறுத்துவது தண்டனைக்குரிய குற்றமாகும். எல்டர்லைன் 14567 இலவச உணவு, தங்குமிடம் மற்றும் சட்ட பாதுகாப்பை வழங்கும்."
                else -> "Senior citizen protection protocol triggered. Elderline 14567 and District Maintenance Tribunals provide free legal support and shelter."
            },
            escalationRequired = false,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("எல்டர்லைன் 14567", "சட்ட உதவி 15100", "சான்றை சேமி", "காவல்துறை 112")
                else -> listOf("Call Elderline 14567", "Legal Aid 15100", "Document Evidence", "Police 112")
            }
        )
    }

    // ==========================================
    // 9. SCHOOL / COLLEGE RAGGING
    // ==========================================
    private fun buildSchoolHarassmentResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "தேசிய ராகிங் எதிர்ப்பு உதவி எண் (1800-180-5522)"
                AppLanguage.HINDI -> "राष्ट्रीय एंटी-रैगिंग हेल्पलाइन (1800-180-5522)"
                else -> "UGC National Anti-Ragging Helpline"
            },
            number = "1800-180-5522",
            agencyName = "University Grants Commission 24x7 Call Center",
            isPrimary = true,
            badge = "ZERO TOLERANCE",
            description = when (lang) {
                AppLanguage.TAMIL -> "மாவட்ட ஆட்சியர் மற்றும் கல்லூரி ராகிங் எதிர்ப்பு குழுவுக்கு உடனடி தகவல் (24 மணி நேரத்திற்குள் கட்டாய விசாரணை)."
                else -> "Immediate escalation to District Magistrate and college Anti-Ragging Squad with mandatory inquiry within 24 hours."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "சைல்டுலைன் / மாணவர் பாதுகாப்பு (1098)"
                else -> "Childline / National Student Protection (1098)"
            },
            number = "1098",
            agencyName = "MWCD & Education Ministry",
            isPrimary = false,
            badge = "ANONYMOUS INQUIRY",
            description = "Completely anonymous complaint processing without campus retaliation."
        )

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.SCHOOL_HARASSMENT,
            riskLevel = SafetyRiskLevel.HIGH,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "விடுதியில் தனிமையில் இருக்க வேண்டாம். உங்கள் பெயர் மற்றும் அடையாளம் யாருக்கும் தெரியாமல் முற்றிலும் ரகசியமாக புகார் பதிவு செய்யப்படும்."
                else -> "Do not remain isolated in dormitory rooms. The complaint can be lodged completely anonymously to protect against campus reprisal."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "UGC ராகிங் எதிர்ப்பு ஒழுங்குமுறைகள் 2009 & உச்ச நீதிமன்ற தீர்ப்பு: தவறிழைப்பவர்கள் உடனடியாக இடைநீக்கம் செய்யப்படுவார்கள்; நடவடிக்கை எடுக்காத கல்லூரியின் அங்கீகாரம் ரத்து செய்யப்படும்."
                else -> "UGC Anti-Ragging Regulations 2009 & Supreme Court Directives: Mandatory suspension of offenders and institution de-recognition for non-compliance."
            },
            conversationalReply = when (lang) {
                AppLanguage.TAMIL -> "கல்லூரி ராகிங் மற்றும் கேலி வன்முறை சட்டப்படி கடுமையான குற்றமாகும். பல்கலைக்கழக மானியக் குழுவின் (UGC) 1800-180-5522 எண் உங்கள் பெயரை வெளியிடாமல் உடனடி நடவடிக்கை எடுக்கும்."
                else -> "Anti-Ragging Protocol active. Under UGC guidelines, complaints are confidential and trigger mandatory District Magistrate inquiry within 24 hours."
            },
            escalationRequired = false,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("ராகிங் எதிர்ப்பு 1800-180-5522", "சைல்டுலைன் 1098", "ஆடியோ/வீடியோ சேமி", "ஆலோசகர் அரட்டை")
                else -> listOf("Call Anti-Ragging 1800-180-5522", "Childline 1098", "Secure Audio/Video in Vault", "Talk to Counselor")
            }
        )
    }

    // ==========================================
    // 10. WORKPLACE HARASSMENT (POSH ACT)
    // ==========================================
    private fun buildWorkplaceHarassmentResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "பெண்கள் உதவி எண் / POSH செல் (1091)"
                else -> "Women Helpline / POSH (1091)"
            },
            number = "1091",
            agencyName = "National Commission for Women - SHe-Box & POSH Cell",
            isPrimary = true,
            badge = "POSH DIRECT",
            description = when (lang) {
                AppLanguage.TAMIL -> "பணியிட பாலியல் புகார்களுக்கான மத்திய அரசு SHe-Box போர்டல் மற்றும் உடனடி விசாரணை."
                else -> "Direct escalation to Ministry of Women & Child Development SHe-Box portal for formal inquiry against employer."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "நல்சா உழைக்கும் பெண்கள் சட்ட உதவி (15100)"
                else -> "NALSA Free Working Women Legal Aid"
            },
            number = "15100",
            agencyName = "National Legal Services Authority",
            isPrimary = false,
            badge = "FREE LAWYER",
            description = "Confidential legal representation before Local Complaints Committee (LCC)."
        )

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.WORKPLACE_HARASSMENT,
            riskLevel = SafetyRiskLevel.MEDIUM,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "நடந்த சம்பவங்கள், மின்னஞ்சல்கள், குறுஞ்செய்திகள் மற்றும் சாட்சிகளின் விவரங்களை எங்களின் ரகசிய பெட்டகத்தில் (Evidence Vault) பதிவு செய்து வையுங்கள்."
                else -> "Keep contemporaneous written records of all incidents, emails, messages, and witness accounts in the encrypted Evidence Vault."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "பணியிடத்தில் பெண்கள் மீதான பாலியல் வன்கொடுமை தடுப்புச் சட்டம் (POSH Act 2013): உள் புகார் குழு (IC) கட்டாய விசாரணை, விசாரணைக் காலத்தில் ஊதியத்துடன் விடுப்பு, வேலை நீக்கம் செய்ய தடை."
                else -> "Sexual Harassment of Women at Workplace (Prevention, Prohibition and Redressal) Act 2013: Mandatory Internal Committee (IC) hearing, paid leave during inquiry, and protection against termination."
            },
            conversationalReply = when (lang) {
                AppLanguage.TAMIL -> "பணியிட பாலியல் துன்புறுத்தல் தடுப்புச் சட்டம் (POSH Act 2013) உங்களுக்கு முழு சட்டப் பாதுகாப்பை அளிக்கிறது. சான்றுகளை எங்களின் என்க்ரிப்ட் செய்யப்பட்ட Vault-ல் பாதுகாப்பாக சேமித்து SHe-Box மூலமாகவோ அல்லது 1091 மூலமாகவோ புகார் அளிக்கலாம்."
                else -> "POSH Act 2013 protection protocol active. You are entitled to a time-bound confidential inquiry, transfer/paid leave during proceedings, and zero retaliatory termination."
            },
            escalationRequired = false,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("மகளிர் ஆணையம் 7827170170", "சட்ட உதவி 15100", "சான்றை பெட்டகத்தில் பூட்டு", "ரகசிய புகார்")
                else -> listOf("Call NCW 7827170170", "Legal Aid 15100", "Seal Chat/Email in Vault", "Anonymous Portal File")
            }
        )
    }

    // ==========================================
    // GENERAL TRIAGE FALLBACK
    // ==========================================
    private fun buildGeneralTriageResponse(lang: AppLanguage, text: String): IntelligentSafetyResponse {
        val primary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "தேசிய அவசர உதவி எண் (112)"
                AppLanguage.HINDI -> "राष्ट्रीय आपातकालीन नंबर (112)"
                AppLanguage.TELUGU -> "జాతీయ అత్యవసర నంబర్ (112)"
                AppLanguage.KANNADA -> "ರಾಷ್ಟ್ರೀಯ ತುರ್ತು ಸಂಖ್ಯೆ (112)"
                AppLanguage.MALAYALAM -> "ദേശീയ അടിയന്തര നമ്പർ (112)"
                else -> "National Emergency Number (112)"
            },
            number = "112",
            agencyName = when (lang) {
                AppLanguage.TAMIL -> "ஒருங்கிணைந்த அவசர உதவி அமைப்பு (காவல்துறை, தீயணைப்பு, ஆம்புலன்ஸ்)"
                else -> "Single Emergency Response System (Police, Fire, Medical)"
            },
            isPrimary = true,
            badge = "ALL SERVICES",
            description = when (lang) {
                AppLanguage.TAMIL -> "காவல்துறை, ஆம்புலன்ஸ் மற்றும் பேரிடர் மீட்புக்கான ஒற்றை தேசிய அவசர உதவி எண்."
                else -> "Unified national response center bridging police, fire, ambulance, and disaster rescue."
            }
        )

        val secondary = EmergencyActionChannel(
            title = when (lang) {
                AppLanguage.TAMIL -> "இலவச சட்ட உதவி & சமூக ஆலோசனை (15100)"
                AppLanguage.HINDI -> "निःशुल्क कानूनी सहायता (15100)"
                else -> "Free Legal Aid & Social Triage (15100)"
            },
            number = "15100",
            agencyName = "National Legal Services Authority (NALSA)",
            isPrimary = false,
            badge = "FREE COUNSEL",
            description = "Free government legal assistance and mediation."
        )

        val reply = when (lang) {
            AppLanguage.TAMIL -> "வணக்கம். SAYANAK பாதுகாப்பு அமைப்பில் நீங்கள் இணைக்கப்பட்டுள்ளீர்கள். உங்கள் பிரச்சனை அல்லது அவசர தேவையை எங்களுக்கு எந்த மொழியிலும் தெரிவியுங்கள் — அதற்கேற்ப சரியான உதவி எண் மற்றும் சட்ட வழிகாட்டலை உடனடியாக வழங்குகிறோம்."
            AppLanguage.HINDI -> "नमस्ते। आप SAYANAK सुरक्षा प्रणाली से जुड़े हैं। अपनी समस्या या आपातकालीन स्थिति हमें बताएं — हम तुरंत सही हेल्पलाइन और कानूनी सहायता प्रदान करेंगे।"
            AppLanguage.TELUGU -> "నమస్కారం. మీరు SAYANAK భద్రతా వ్యవస్థకు అనుసంధానించబడ్డారు. మీ సమస్యను మాకు తెలియజేయండి."
            AppLanguage.KANNADA -> "ನಮಸ್ಕಾರ. ನೀವು SAYANAK ಸುರಕ್ಷತಾ ವ್ಯವಸ್ಥೆಗೆ ಸಂಪರ್ಕಗೊಂಡಿದ್ದೀರಿ. ನಿಮ್ಮ ಸಮಸ್ಯೆಯನ್ನು ನಮಗೆ ತಿಳಿಸಿ."
            AppLanguage.MALAYALAM -> "നമസ്കാരം. SAYANAK സുരക്ഷാ സംവിധാനത്തിലേക്ക് സ്വാഗതം. നിങ്ങളുടെ പ്രശ്നം ഞങ്ങളെ അറിയിക്കുക."
            else -> "Hello. You are connected to the SAYANAK AI Safety Operating System. Tell us what is happening, and we will automatically route you to the exact specialized emergency agency, legal counsel, and confidential shelter."
        }

        return IntelligentSafetyResponse(
            detectedLanguage = lang,
            category = CrisisCategory.GENERAL_DISTRESS,
            riskLevel = SafetyRiskLevel.LOW,
            primaryChannel = primary,
            secondaryChannel = secondary,
            immediateSafetyInstruction = when (lang) {
                AppLanguage.TAMIL -> "கீழே உள்ள விரைவு பொத்தானைத் தேர்ந்தெடுக்கவும் அல்லது உங்கள் சூழ்நிலையை தமிழில் தட்டச்சு செய்யவும்."
                else -> "Select a quick prompt or type your situation in any regional language."
            },
            legalProtectionNotice = when (lang) {
                AppLanguage.TAMIL -> "SAYANAK-ல் உள்ள அனைத்து தகவல்களும் எண்ட்-டு-எண்ட் என்க்ரிப்ஷன் மூலம் பாதுகாக்கப்படுகின்றன. உங்கள் அடையாளம் 100% ரகசியமாக வைக்கப்படும்."
                else -> "All reports in SAYANAK are protected under end-to-end anonymity and zero phone/IMEI telemetry storage."
            },
            conversationalReply = reply,
            escalationRequired = false,
            quickActionChips = when (lang) {
                AppLanguage.TAMIL -> listOf("காவல் அவசரம் 112", "பெண்கள் உதவி 1091", "சைபர் கிரைம் 1930", "மனநல உதவி 14416")
                AppLanguage.HINDI -> listOf("आपातकालीन 112", "महिला हेल्पलाइन 1091", "साइबर अपराध 1930", "टेली-मानस 14416")
                else -> listOf("Emergency 112", "Women Helpline 1091", "Cyber Crime 1930", "Tele-MANAS 14416")
            }
        )
    }
}
