package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import com.example.data.model.HelplineResource
import com.example.data.repository.SampleData
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CaseStatus
import com.example.data.model.ConsentType
import com.example.data.model.ReportEntity
import com.example.data.model.SeverityTier
import com.example.ui.components.CaseStatusBadge
import com.example.ui.components.HelplineCard
import com.example.ui.components.SeverityTierBadge
import com.example.ui.components.makePhoneCall
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.InputBackground
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.SeverityUrgentSos
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.TealPrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SahayakViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReportTrackingScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentLang by viewModel.currentLanguage.collectAsState()
    val report by viewModel.trackedReport.collectAsState()
    val searchError by viewModel.trackingSearchError.collectAsState()
    var showEscalationDialog by remember { mutableStateOf(false) }
    var escalationNote by remember { mutableStateOf("") }
    var isThreatenedSelected by remember { mutableStateOf(false) }
    var isSituationWorsenedSelected by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = "Anonymous Case Tracker",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        if (report == null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = searchError ?: "No active report selected.",
                            color = Slate400,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { viewModel.navigateTo(AppScreen.HOME) },
                            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary, contentColor = Slate950)
                        ) {
                            Text("Return to Home & Search Token")
                        }
                    }
                }
            }
        } else {
            val rep = report!!
            val tier = try { SeverityTier.valueOf(rep.severityTier) } catch (_: Exception) { SeverityTier.COUNSELLING }
            val isUrgent = tier == SeverityTier.URGENT_SOS || rep.isThreatenedForReporting || rep.situationEscalated

            // Top Status & Token Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = if (isUrgent) Color(0xFF450A0A) else Slate900),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, if (isUrgent) SeverityUrgentSos else TealPrimary)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "TRACKING TOKEN",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SecondaryCyan,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = rep.trackingToken,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                            CaseStatusBadge(statusStr = rep.status)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Category: ${rep.category}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                            SeverityTierBadge(tier = tier)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        val dateFormatted = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(rep.createdAt))
                        Text(
                            text = "Submitted: $dateFormatted",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate400,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            color = Slate800,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Triage Routing Status:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TealPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = rep.statusNotes,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate200,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            // Retaliation & Post-Report Check-In Section (Section 5)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Slate800.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (rep.isThreatenedForReporting) SeverityUrgentSos else Slate700)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = "Safety Check-In",
                                tint = if (rep.isThreatenedForReporting) SeverityUrgentSos else SecondaryCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Post-Report Check-In (Retaliation Risk)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "If you are being threatened for reporting, or if the situation has worsened, flag this below. It immediately escalates the case to protective services and surfaces emergency numbers.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate400,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { showEscalationDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (rep.isThreatenedForReporting) Slate800 else SeverityUrgentSos,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("flag_escalation_button")
                                .height(44.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = "Escalate", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (rep.isThreatenedForReporting || rep.situationEscalated) "Flag Additional Safety Update" else "Flag Escalation / Retaliation Threat",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Case Intake Summary & Explainable Decision Breakdown
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Structured Intake Summary",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = rep.answersSummary,
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate200,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Decision Tree Rules Triggered:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryCyan
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = rep.scoreBreakdown,
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate400,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Assigned Support Facility: ${rep.assignedCenterName}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = TealPrimary,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Tailored Emergency Helplines Suggested for this Report (Based on Category & Danger Tier)
            item {
                val isUrgent = rep.isThreatenedForReporting || rep.situationEscalated || rep.severityTier == SeverityTier.URGENT_SOS.name
                val suggestedHelplines = SampleData.getSuggestedHelplinesForCategory(rep.category, isUrgent)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Slate900),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isUrgent) SeverityUrgentSos else TealPrimary)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Helplines",
                                tint = if (isUrgent) SeverityUrgentSos else TealPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = when (currentLang) {
                                    com.example.localization.AppLanguage.TAMIL -> "இந்த புகாருக்கான பிரத்யேக உதவி எண்கள்"
                                    com.example.localization.AppLanguage.HINDI -> "इस रिपोर्ट हेतु अनुशंसित हेल्पलाइन"
                                    else -> "Suggested Helplines For This Report"
                                },
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when (currentLang) {
                                com.example.localization.AppLanguage.TAMIL -> "உங்கள் புகாரின் வகை (${rep.category}) மற்றும் தீவிரத்தன்மைக்கு ஏற்ப தேர்ந்தெடுக்கப்பட்ட அவசர எண்கள்:"
                                com.example.localization.AppLanguage.HINDI -> "आपकी रिपोर्ट श्रेणी (${rep.category}) के आधार पर प्राथमिकता प्राप्त हेल्पलाइन:"
                                else -> "Emergency numbers automatically selected based on your incident category (${rep.category}):"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate400,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        suggestedHelplines.forEach { helpline: HelplineResource ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Slate800),
                                shape = RoundedCornerShape(10.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = helpline.getLocalizedTitle(currentLang),
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                fontSize = 13.sp
                                            )
                                            if (helpline.is24x7) {
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Surface(
                                                    color = TealPrimary.copy(alpha = 0.2f),
                                                    shape = RoundedCornerShape(4.dp)
                                                ) {
                                                    Text(
                                                        text = "24x7",
                                                        color = TealPrimary,
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = helpline.getLocalizedDescription(currentLang),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFFCBD5E1),
                                            fontSize = 11.sp,
                                            lineHeight = 15.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = {
                                            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${helpline.number}"))
                                            context.startActivity(dialIntent)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (helpline.number == "112" || isUrgent) SeverityUrgentSos else TealPrimary,
                                            contentColor = if (helpline.number == "112" || isUrgent) Color.White else Slate950
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = helpline.number, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Escalation Dialog
    if (showEscalationDialog && report != null) {
        AlertDialog(
            onDismissRequest = { showEscalationDialog = false },
            title = {
                Text(
                    text = "Flag Case Escalation",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Select what has happened to upgrade triage severity:",
                        color = Slate200,
                        fontSize = 12.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isThreatenedSelected = !isThreatenedSelected },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Checkbox(
                            checked = isThreatenedSelected,
                            onCheckedChange = { isThreatenedSelected = it },
                            colors = androidx.compose.material3.CheckboxDefaults.colors(checkedColor = SeverityUrgentSos)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "I am being threatened for reporting",
                            color = Color(0xFFFCA5A5),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isSituationWorsenedSelected = !isSituationWorsenedSelected },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Checkbox(
                            checked = isSituationWorsenedSelected,
                            onCheckedChange = { isSituationWorsenedSelected = it },
                            colors = androidx.compose.material3.CheckboxDefaults.colors(checkedColor = SeverityUrgentSos)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "The physical situation or crisis has escalated",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }

                    OutlinedTextField(
                        value = escalationNote,
                        onValueChange = { escalationNote = it },
                        placeholder = { Text("Briefly describe the change in danger...", color = Slate400, fontSize = 11.sp) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InputBackground,
                            unfocusedContainerColor = InputBackground,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.flagPostReportCheckIn(
                            token = report!!.trackingToken,
                            threatened = isThreatenedSelected,
                            escalated = isSituationWorsenedSelected,
                            note = escalationNote
                        )
                        showEscalationDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SeverityUrgentSos, contentColor = Color.White)
                ) {
                    Text("Trigger SOS Protective Escalation", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEscalationDialog = false }) {
                    Text("Cancel", color = Slate400)
                }
            },
            containerColor = Slate900
        )
    }
}

// --- INTELLIGENT SOS RESPONSE ROUTING & CRISIS OVERRIDE CONSOLE ---
@Composable
fun EmergencySosScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activeTriage by viewModel.activeTriageResult.collectAsState()
    val gpsState by viewModel.liveGpsState.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    var isBrowsingAllHelplines by remember { mutableStateOf(false) }

    val triage = activeTriage ?: com.example.engine.IntelligentSafetyRouter.routeIncident("General Emergency Assistance", currentLang)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Header Bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.HOME) },
                        modifier = Modifier.testTag("sos_back_button")
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = "Intelligent SOS Routing",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFFCA5A5)
                        )
                        Text(
                            text = "AI-Powered Direct Triage & Dispatch",
                            fontSize = 11.sp,
                            color = Slate400
                        )
                    }
                }

                // Quick Camouflage Button
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { viewModel.triggerQuickExitCamouflage() },
                    color = Slate800,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
                ) {
                    Text(
                        text = "🔢 Stealth Exit",
                        fontSize = 11.sp,
                        color = Slate200,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // 2. 10 Real-World Scenario Quick Switcher Carousel
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Select Incident Archetype (Auto-Routes SOS)",
                    style = MaterialTheme.typography.labelSmall,
                    color = SecondaryCyan,
                    letterSpacing = 0.5.sp,
                    fontWeight = FontWeight.Bold
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp)
                ) {
                    items(com.example.engine.CrisisCategory.values().toList()) { category ->
                        val isSelected = triage.category == category
                        val catLabel = when (currentLang) {
                            com.example.localization.AppLanguage.TAMIL -> category.defaultNameTa
                            com.example.localization.AppLanguage.HINDI -> category.defaultNameHi
                            com.example.localization.AppLanguage.TELUGU -> category.defaultNameTe
                            com.example.localization.AppLanguage.KANNADA -> category.defaultNameKn
                            com.example.localization.AppLanguage.MALAYALAM -> category.defaultNameMl
                            else -> category.defaultNameEn
                        }

                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { viewModel.setCrisisCategoryDirectly(category) },
                            color = if (isSelected) SeverityUrgentSos else Slate800,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) Color(0xFFFCA5A5) else Slate700
                            )
                        ) {
                            Text(
                                text = catLabel,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate300,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. AI Triage Assessment Banner (Category + Risk Score + Response Time)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF3B0707)),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(triage.riskLevel.colorHex))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color(triage.riskLevel.colorHex))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "EVALUATED INCIDENT TRIAGE",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFCA5A5),
                                letterSpacing = 1.sp
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(triage.riskLevel.colorHex)
                        ) {
                            Text(
                                text = triage.riskLevel.tier,
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (currentLang) {
                            com.example.localization.AppLanguage.TAMIL -> triage.category.defaultNameTa
                            com.example.localization.AppLanguage.HINDI -> triage.category.defaultNameHi
                            else -> triage.category.defaultNameEn
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = triage.immediateSafetyInstruction,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFECDD3),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // 4. PRIMARY ACTION CARD (1-TAP DIRECT DISPATCH)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFEF4444))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF10B981)
                        ) {
                            Text(
                                text = "⭐ TOP RECOMMENDED CHANNEL",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = "Target: < ${triage.riskLevel.maxResponseMinutes} mins",
                            color = SecondaryCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = triage.primaryChannel.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = triage.primaryChannel.agencyName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = triage.primaryChannel.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate200,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // GIANT 1-TAP DISPATCH BUTTON
                    Button(
                        onClick = { makePhoneCall(context, triage.primaryChannel.number) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("primary_dispatch_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDC2626),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "1-TAP CALL ${triage.primaryChannel.number}",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        // 5. SECONDARY AGENCY CARD (IF APPLICABLE)
        triage.secondaryChannel?.let { sec ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Slate900),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Slate800
                            ) {
                                Text(
                                    text = sec.badge,
                                    color = SecondaryCyan,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = sec.title,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.sp
                            )
                            Text(
                                text = sec.agencyName,
                                fontSize = 11.sp,
                                color = Slate400
                            )
                        }

                        Button(
                            onClick = { makePhoneCall(context, sec.number) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Slate800,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(16.dp), tint = Color(0xFF10B981))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(sec.number, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // 6. LIVE GPS STATUS & TRUSTED CIRCLE SMS BROADCAST
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Live GPS & Broadcast Link",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "Accuracy: ${gpsState.accuracyMeters}m",
                            fontSize = 11.sp,
                            color = Color(0xFF10B981),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "📍 ${gpsState.latitude}, ${gpsState.longitude} • ${gpsState.cityApprox}",
                        fontSize = 11.5.sp,
                        color = Slate300
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val smsText = "EMERGENCY SOS: I need help at my coordinates: https://maps.google.com/?q=${gpsState.latitude},${gpsState.longitude} (SAYANAK Protective Beacon)"
                                val intent = android.content.Intent(android.content.Intent.ACTION_SENDTO).apply {
                                    data = android.net.Uri.parse("smsto:")
                                    putExtra("sms_body", smsText)
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Slate800, contentColor = Color.White),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SecondaryCyan),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("📡 Broadcast GPS SMS", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { viewModel.navigateTo(AppScreen.TRUSTED_CONTACTS) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Slate800, contentColor = Color.White),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Slate700),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("👥 Trusted Circle", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 7. STATUTORY LEGAL PROTECTIONS NOTICE
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate800)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Statutory Legal Protections (India)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = triage.legalProtectionNotice,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate300,
                        fontSize = 11.5.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // 8. ALL HELPLINES ACCORDION (OPTIONAL EXPANSION)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { isBrowsingAllHelplines = !isBrowsingAllHelplines },
                    color = Slate900,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate800)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isBrowsingAllHelplines) "▲ Hide General Directory" else "▼ Browse All Other National Helplines",
                            color = Slate400,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                if (isBrowsingAllHelplines) {
                    com.example.data.repository.SampleData.helplines.forEach { helpline ->
                        HelplineCard(helpline = helpline)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
