package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.IntakeCategory
import com.example.data.model.ReporterRole
import com.example.data.repository.SampleData
import com.example.localization.AppLanguage
import com.example.localization.LocalizedStrings
import com.example.ui.components.AnonymityTrustBanner
import com.example.ui.components.CompanionWelcomeHeroCard
import com.example.ui.components.DailyWellnessCheckinCard
import com.example.ui.components.HelplineCard
import com.example.ui.components.MoodTrackingWidget
import com.example.ui.components.PositiveAffirmationCard
import com.example.ui.components.RecoveryProgressTrackerCard
import com.example.ui.components.SimpleHowItWorksVisual
import com.example.ui.components.SupportCircleSection
import com.example.ui.components.TrustIndicatorBadgesRow
import com.example.ui.theme.BlueAccent
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BluePrimaryLight
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CardBorderHighlight
import com.example.ui.theme.InputBackground
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.SecondaryCyanLight
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun HomeScreen(
    viewModel: com.example.ui.viewmodel.SahayakViewModel,
    modifier: Modifier = Modifier
) {
    var searchTokenText by remember { mutableStateOf("") }

    val supportCircle by viewModel.supportCircleMembers.collectAsState()
    val currentMood by viewModel.currentMood.collectAsState()
    val wellbeingScore by viewModel.emotionalWellbeingScore.collectAsState()
    val moodHistory by viewModel.moodHistory.collectAsState()
    val journalNote by viewModel.todayJournalNote.collectAsState()
    val streakDays by viewModel.recoveryStreakDays.collectAsState()
    val goals by viewModel.recoveryGoals.collectAsState()
    val milestones by viewModel.recoveryMilestones.collectAsState()
    val selectedFeeling by viewModel.wellnessCheckinFeeling.collectAsState()
    val wellnessSuggestion by viewModel.personalizedWellnessSuggestion.collectAsState()
    val currentAffirmation by viewModel.currentAffirmation.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isLargeText by viewModel.isLargeTextEnabled.collectAsState()
    val isLowLiteracy by viewModel.isLowLiteracyModeEnabled.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 0. Language Selector & Accessibility Bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Language / மொழி / भाषा",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable { viewModel.toggleLargeText() },
                                color = if (isLargeText) MaterialTheme.colorScheme.primary else Color.Transparent,
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                            ) {
                                Text(
                                    text = "A+",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = if (isLargeText) Color.White else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // 6 Languages row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AppLanguage.values().forEach { lang ->
                            val isSelected = lang == currentLang
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                    .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent, RoundedCornerShape(8.dp))
                                    .clickable { viewModel.setLanguage(lang) }
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = lang.nativeName,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }

        // 1. Personalized Welcome Card with Support Companion Character (SAYANAK Hero)
        item {
            CompanionWelcomeHeroCard(
                onStartAnonymousChat = { viewModel.startAnonymousChat() },
                onEmergencySos = { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.EMERGENCY_SOS) }
            )
        }

        // 1.5 Hackathon Special Innovation Modules: Vault, Contacts, Impact Telemetry
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "SAYANAK Core Protection Suite",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.EVIDENCE_VAULT) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981).copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.EnhancedEncryption, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))
                            }
                            Text(
                                text = "Evidence Vault",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "AES-256 KeyStore + SHA-256 Seal",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 13.sp
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.TRUSTED_CONTACTS) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF38BDF8).copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.ContactPhone, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(18.dp))
                            }
                            Text(
                                text = "Trusted Circle",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Live GPS & Auto Safety Check-In",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 13.sp
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.IMPACT_DASHBOARD) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF59E0B).copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Assessment, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(18.dp))
                            }
                            Text(
                                text = "Impact Live",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "12k+ Rescued & SDG Alignment",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // 2. Trust Indicator Badges
        item {
            TrustIndicatorBadgesRow()
        }

        // 3. How Can We Help You Today Section (Report Incident, Chat Anonymously, Find Support Resources)
        item {
            HowCanWeHelpSection(
                onReportIncident = { viewModel.startIntake(role = ReporterRole.SELF) },
                onChatAnonymously = { viewModel.startAnonymousChat() },
                onFindResources = { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.RESOURCES) }
            )
        }

        // 4. Support Circle Cards (Self, Mother, Father, Sibling, Friend, Teacher)
        item {
            SupportCircleSection(
                members = supportCircle,
                onEditMember = { member ->
                    viewModel.updateSupportContact(
                        role = member.role,
                        name = member.name,
                        phone = member.phone,
                        isEmergency = member.isEmergencyContact
                    )
                }
            )
        }

        // 5. Community Together Banner ("Together, we can create a safer and kinder world. 💚")
        item {
            CommunityTogetherBanner()
        }

        // 6. Quick Action Grid (6 Items: Anonymous Chat, Start Assessment, Find Support Centre, Track Existing Case, Self Help Resources, Emergency Support)
        item {
            HomeScreenQuickActionsSection(
                onStartChat = { viewModel.startAnonymousChat() },
                onStartAssessment = { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.INTAKE_ROLE_CATEGORY) },
                onFindCenters = { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.DIRECTORY) },
                onTrackCase = { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.TRACK_REPORT) },
                onSelfHelpResources = { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.RESOURCES) },
                onEmergencySupport = { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.EMERGENCY_SOS) }
            )
        }

        // 7. Positive Daily Affirmation
        item {
            PositiveAffirmationCard(
                affirmation = currentAffirmation,
                onRefresh = { viewModel.nextAffirmation() }
            )
        }

        // 8. Daily Mood Tracking Widget & Trend Graph
        item {
            MoodTrackingWidget(
                currentMood = currentMood,
                wellbeingScore = wellbeingScore,
                moodHistory = moodHistory,
                journalNote = journalNote,
                onRecordMood = { mood, note ->
                    viewModel.recordMood(mood, note)
                }
            )
        }

        // 9. Daily Wellness Check-in Card (One-tap feelings + tailored guidance)
        item {
            DailyWellnessCheckinCard(
                selectedFeeling = selectedFeeling,
                suggestion = wellnessSuggestion,
                onSelectFeeling = { feeling ->
                    viewModel.checkInWellness(feeling)
                }
            )
        }

        // 10. Recovery Progress Tracker (Streak, weekly goals, wellness score, milestones)
        item {
            RecoveryProgressTrackerCard(
                streakDays = streakDays,
                wellnessScore = wellbeingScore,
                goals = goals,
                milestones = milestones,
                onIncrementStreak = { viewModel.incrementRecoveryStreak() }
            )
        }

        // 8. Confidential Intake Assessment Card (For Myself / For Loved One)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_soft_entry_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF13233F),
                                    Color(0xFF1E335A),
                                    Color(0xFF0F1A2E)
                                )
                            )
                        )
                        .border(1.dp, CardBorderHighlight, RoundedCornerShape(20.dp))
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = BluePrimary.copy(alpha = 0.25f),
                                shape = RoundedCornerShape(20.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, BluePrimaryLight.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(BlueAccent)
                                    )
                                    Text(
                                        text = "ANONYMOUS SAFE TRIAGE",
                                        color = BlueAccent,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 0.8.sp
                                    )
                                }
                            }

                            Text(
                                text = "Zero Tracking",
                                color = Slate400,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Concerned about a habit, distress, or someone you care about?",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 26.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "No clinical jargon, no photo uploads, and zero judgment. Take a 2-minute confidential check to get matched with verified support.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Slate300,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { viewModel.startIntake(role = ReporterRole.SELF) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BluePrimary,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("report_for_myself_button")
                                    .height(46.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Person,
                                    contentDescription = "For Myself",
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "For Myself",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            Button(
                                onClick = { viewModel.startIntake(role = ReporterRole.THIRD_PARTY) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Slate850,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1.15f)
                                    .testTag("report_for_someone_else_button")
                                    .height(46.dp)
                                    .border(1.dp, CardBorderHighlight, RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FamilyRestroom,
                                    contentDescription = "For Loved One",
                                    modifier = Modifier.size(18.dp),
                                    tint = SecondaryCyanLight
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "For Loved One",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 9. Live Anonymous Support Chat Feature Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.startAnonymousChat() }
                    .testTag("home_anonymous_chat_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderHighlight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(BluePrimary, SecondaryCyan)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Anonymous Support Chat",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Live Anonymous Chat",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = BluePrimary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp),
                                border = androidx.compose.foundation.BorderStroke(0.5.dp, BluePrimary.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "SECURE & ENCRYPTED",
                                    color = BluePrimaryLight,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Speak confidentially with empanelled counselors. Zero phone number or identity exposure.",
                            color = Slate300,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Open Chat",
                        tint = BluePrimaryLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 10. 3-Step Visual Graphic: How Sahayak Protects You
        item {
            SimpleHowItWorksVisual()
        }

        // 11. Anonymity Trust Banner
        item {
            AnonymityTrustBanner()
        }

        // 12. 4 Core Categories with clear simple everyday language
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Choose a Support Area",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "4 Categories",
                        color = BluePrimaryLight,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                CategoryRowItem(
                    category = IntakeCategory.ADDICTION,
                    icon = Icons.Default.MedicalServices,
                    iconTint = Color(0xFF60A5FA),
                    chips = listOf("Alcohol", "Vaping / Smoking", "Prescription Pills"),
                    onClick = { viewModel.startIntake(category = IntakeCategory.ADDICTION) }
                )

                CategoryRowItem(
                    category = IntakeCategory.ABUSE,
                    icon = Icons.Default.Shield,
                    iconTint = Color(0xFFF87171),
                    chips = listOf("Safety Guidance", "Bullying", "Domestic Distress"),
                    onClick = { viewModel.startIntake(category = IntakeCategory.ABUSE) }
                )

                CategoryRowItem(
                    category = IntakeCategory.MENTAL_HEALTH,
                    icon = Icons.Default.Psychology,
                    iconTint = Color(0xFF38BDF8),
                    chips = listOf("Anxiety", "Panic", "Depression & Mood"),
                    onClick = { viewModel.startIntake(category = IntakeCategory.MENTAL_HEALTH) }
                )

                CategoryRowItem(
                    category = IntakeCategory.ACADEMIC_STRESS,
                    icon = Icons.Default.School,
                    iconTint = Color(0xFFFBBF24),
                    chips = listOf("Exam Fear", "Burnout", "Career Pressure"),
                    onClick = { viewModel.startIntake(category = IntakeCategory.ACADEMIC_STRESS) }
                )
            }
        }

        // 13. Awareness Layer Feature Section (Signs & Teen check)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Early Awareness & Peer Check",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.SIGNS_EXPLAINER) },
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(BluePrimary.copy(alpha = 0.15f))
                                    .border(1.dp, BluePrimary.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "Signs explainer",
                                    tint = BluePrimaryLight,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Signs It's Not a Phase",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Recognize chronic abuse, hidden addiction & subtle distress markers.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate400,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.TEEN_CHECK) },
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SecondaryCyan.copy(alpha = 0.15f))
                                    .border(1.dp, SecondaryCyan.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = "Teen check",
                                    tint = SecondaryCyanLight,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Teen Peer Checkup",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Confidential checklist for students & friends with zero judgment.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate400,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // 14. Anonymous Case Tracker & Retaliation Flag Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderHighlight)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(BluePrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Token",
                                tint = BluePrimaryLight,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Track Case or Flag Retaliation",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Use your secure anonymous token (e.g., SHK-7291-NX44)",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate400,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = searchTokenText,
                            onValueChange = { searchTokenText = it },
                            placeholder = { Text("e.g. SHK-7291-NX44", fontSize = 12.sp, color = Slate400) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = InputBackground,
                                unfocusedContainerColor = InputBackground,
                                focusedBorderColor = BluePrimary,
                                unfocusedBorderColor = Slate700,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("search_token_input")
                        )

                        Button(
                            onClick = {
                                if (searchTokenText.isNotBlank()) {
                                    viewModel.lookupToken(searchTokenText)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BluePrimary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .testTag("lookup_token_button")
                        ) {
                            Text("Track", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // 15. 24/7 Crisis Helplines
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Immediate Verified Helplines",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Surface(
                        color = BluePrimary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "24/7 FREE",
                            fontSize = 10.sp,
                            color = BlueAccent,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                SampleData.helplines.take(3).forEach { helpline ->
                    HelplineCard(helpline = helpline)
                }
            }
        }

        // 16. Empanelled Center Portal Link
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo(com.example.ui.viewmodel.AppScreen.COUNSELLOR_PORTAL) },
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderHighlight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF818CF8).copy(alpha = 0.2f))
                                .border(1.dp, Color(0xFF818CF8).copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SupportAgent,
                                contentDescription = "Counsellor Portal",
                                tint = Color(0xFFA5B4FC),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Centre & Counsellor Portal",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Triage queue, consent-gated review & case assignment",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate400,
                                fontSize = 11.sp
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Open Portal",
                        tint = Slate400
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CategoryRowItem(
    category: IntakeCategory,
    icon: ImageVector,
    iconTint: Color,
    chips: List<String>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("category_${category.name.lowercase()}"),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconTint.copy(alpha = 0.15f))
                        .border(1.dp, iconTint.copy(alpha = 0.35f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = category.displayName,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = category.displayName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = category.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Select ${category.displayName}",
                    tint = Slate400,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Plain tags to help normal people instantly know what's covered
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                chips.forEach { chip ->
                    Surface(
                        color = Slate850,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, CardBorder)
                    ) {
                        Text(
                            text = chip,
                            color = Slate300,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HowCanWeHelpSection(
    onReportIncident: () -> Unit,
    onChatAnonymously: () -> Unit,
    onFindResources: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "How can we help you today?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F241D),
            fontSize = 16.sp
        )

        HelpActionCard(
            title = "Report an Incident",
            subtitle = "Share what happened anonymously.",
            icon = Icons.Default.Assignment,
            badgeColor = Color(0xFF10B981),
            testTag = "help_action_report",
            onClick = onReportIncident
        )

        HelpActionCard(
            title = "Chat Anonymously",
            subtitle = "Talk to Sayanak anytime.",
            icon = Icons.Default.Chat,
            badgeColor = Color(0xFF0D4739),
            testTag = "help_action_chat",
            onClick = onChatAnonymously
        )

        HelpActionCard(
            title = "Find Support Resources",
            subtitle = "Explore helpful resources.",
            icon = Icons.Default.MenuBook,
            badgeColor = Color(0xFF8B5CF6),
            testTag = "help_action_resources",
            onClick = onFindResources
        )
    }
}

@Composable
private fun HelpActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    badgeColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(badgeColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = badgeColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = Slate300,
                    fontSize = 11.5.sp
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Go",
                tint = Slate400,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun CommunityTogetherBanner(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF193A32)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A5249))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3BB273).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.FamilyRestroom,
                    contentDescription = null,
                    tint = Color(0xFF3BB273),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Together, we can create a safer and kinder world. 💚",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Encourage open dialogues without fear of retaliation.",
                    color = Color(0xFFD0D8D4),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun HomeScreenQuickActionsSection(
    onStartChat: () -> Unit,
    onStartAssessment: () -> Unit,
    onFindCenters: () -> Unit,
    onTrackCase: () -> Unit,
    onSelfHelpResources: () -> Unit,
    onEmergencySupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickActionTile(
                title = "Anonymous Chat",
                subtitle = "Live Counselor & AI",
                icon = Icons.Default.Chat,
                iconTint = Color(0xFF0D4739),
                testTag = "quick_action_chat",
                modifier = Modifier.weight(1f),
                onClick = onStartChat
            )
            QuickActionTile(
                title = "Start Assessment",
                subtitle = "Confidential Triage",
                icon = Icons.Default.Assignment,
                iconTint = Color(0xFF10B981),
                testTag = "quick_action_assessment",
                modifier = Modifier.weight(1f),
                onClick = onStartAssessment
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickActionTile(
                title = "Find Support Centre",
                subtitle = "Verified Directory",
                icon = Icons.Default.MedicalServices,
                iconTint = Color(0xFF2D6A4F),
                testTag = "quick_action_directory",
                modifier = Modifier.weight(1f),
                onClick = onFindCenters
            )
            QuickActionTile(
                title = "Track Case",
                subtitle = "Token Status Lookup",
                icon = Icons.Default.Search,
                iconTint = Color(0xFF3B82F6),
                testTag = "quick_action_track",
                modifier = Modifier.weight(1f),
                onClick = onTrackCase
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickActionTile(
                title = "Self Help Resources",
                subtitle = "Guides & Calming",
                icon = Icons.Default.MenuBook,
                iconTint = Color(0xFF8B5CF6),
                testTag = "quick_action_resources",
                modifier = Modifier.weight(1f),
                onClick = onSelfHelpResources
            )
            QuickActionTile(
                title = "Emergency SOS",
                subtitle = "Immediate Help 112",
                icon = Icons.Default.Call,
                iconTint = Color(0xFFEF4444),
                testTag = "quick_action_sos",
                modifier = Modifier.weight(1f),
                onClick = onEmergencySupport
            )
        }
    }
}

@Composable
private fun QuickActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(78.dp)
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconTint.copy(alpha = 0.12f))
                    .border(1.dp, iconTint.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.5.sp,
                    maxLines = 1
                )
            }
        }
    }
}

