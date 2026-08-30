package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.example.data.model.AnonymousChatMessage
import com.example.data.model.AnonymousChatSession
import com.example.data.model.CaseStatus
import com.example.data.model.ConsentType
import com.example.data.model.ReportEntity
import com.example.data.model.SeverityTier
import com.example.ui.components.CaseStatusBadge
import com.example.ui.components.SeverityTierBadge
import com.example.ui.components.makePhoneCall
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BluePrimaryLight
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CardBorderHighlight
import com.example.ui.theme.InputBackground
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.SecondaryCyanLight
import com.example.ui.theme.SeverityUrgentSos
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
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
fun CounsellorPortalScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val reports by viewModel.allReports.collectAsState()
    val chatSessions by viewModel.activeChatSessions.collectAsState()
    var selectedPortalTab by remember { mutableStateOf(0) } // 0 = Reports Queue, 1 = Live Anonymous Chats
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var activeActionReport by remember { mutableStateOf<ReportEntity?>(null) }
    var updateNoteText by remember { mutableStateOf("") }
    var unlockedReportIds by remember { mutableStateOf(setOf<Long>()) }

    var selectedChatSessionId by remember { mutableStateOf<String?>(null) }
    val counsellorReplyInput by viewModel.counsellorReplyInput.collectAsState()
    val activeChatMessages by viewModel.activeChatMessages.collectAsState()

    val filteredReports = reports.filter {
        selectedCategoryFilter == "ALL" || it.category == selectedCategoryFilter
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Column {
                        Text(
                            text = "Empanelled Centre Portal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Confidential Triage & Referral Queue",
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondaryCyan
                        )
                    }
                }
            }
        }

        // Dashboard Info Banner (Consent-Gated Rule)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Security, contentDescription = "Security", tint = TealPrimary, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Privacy Mandate: Reporter contact details remain masked until explicitly consented. Status updates flow back directly via tracking token.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate200,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Portal Section Switcher Tabs
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Slate900,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Button(
                        onClick = { selectedPortalTab = 0 },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedPortalTab == 0) BluePrimary else Color.Transparent,
                            contentColor = if (selectedPortalTab == 0) Color.White else Slate400
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AssignmentTurnedIn,
                            contentDescription = "Triage Queue",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Intake Queue (${filteredReports.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { selectedPortalTab = 1 },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedPortalTab == 1) SecondaryCyan else Color.Transparent,
                            contentColor = if (selectedPortalTab == 1) Slate950 else Slate400
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Live Anonymous Chats",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Live Chats (${chatSessions.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        if (selectedPortalTab == 0) {
            // Category Filter Chips
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val filters = listOf("ALL", "ADDICTION", "ABUSE", "MENTAL_HEALTH", "ACADEMIC_STRESS")
                    filters.forEach { f ->
                        val isSelected = selectedCategoryFilter == f
                        Button(
                            onClick = { selectedCategoryFilter = f },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) TealPrimary else CardBackground,
                                contentColor = if (isSelected) Slate950 else Slate200
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).height(36.dp),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Text(
                                text = when (f) {
                                    "ALL" -> "All"
                                    "ADDICTION" -> "Addict"
                                    "ABUSE" -> "Abuse"
                                    "MENTAL_HEALTH" -> "Mental"
                                    else -> "Study"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

        // Triage Queue Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Active Triage Queue (${filteredReports.size} cases)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Reports Items
        if (filteredReports.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No cases found matching filter.", color = Slate400)
                    }
                }
            }
        } else {
            items(filteredReports, key = { it.id }) { report ->
                val tier = try { SeverityTier.valueOf(report.severityTier) } catch (_: Exception) { SeverityTier.COUNSELLING }
                val isUnlocked = unlockedReportIds.contains(report.id)
                val consent = try { ConsentType.valueOf(report.consentType) } catch (_: Exception) { ConsentType.RESOURCE_PACK_ONLY }
                val dateFormatted = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(report.createdAt))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("portal_case_${report.trackingToken}"),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (report.isThreatenedForReporting || tier == SeverityTier.URGENT_SOS) SeverityUrgentSos else CardBorder
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = report.trackingToken,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "$dateFormatted • ${report.relationshipToPerson}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate400,
                                    fontSize = 11.sp
                                )
                            }
                            CaseStatusBadge(statusStr = report.status)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = report.category,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryCyan
                            )
                            SeverityTierBadge(tier = tier)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = report.answersSummary,
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate200,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = Slate800,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Score Breakdown: ${report.scoreBreakdown}",
                                color = Slate400,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(8.dp),
                                lineHeight = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Consent-Gated Reveal Section (Section 3.3)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Slate900),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (isUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                                            contentDescription = "Consent Lock",
                                            tint = if (consent == ConsentType.RESOURCE_PACK_ONLY) Color(0xFFF87171) else TealPrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Consent Level: ${consent.label}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }

                                    if (consent != ConsentType.RESOURCE_PACK_ONLY) {
                                        TextButton(
                                            onClick = {
                                                unlockedReportIds = if (isUnlocked) unlockedReportIds - report.id else unlockedReportIds + report.id
                                            }
                                        ) {
                                            Text(
                                                text = if (isUnlocked) "Hide Details" else "Unlock Contact",
                                                fontSize = 11.sp,
                                                color = TealPrimary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                if (consent == ConsentType.RESOURCE_PACK_ONLY) {
                                    Text(
                                        text = "🔒 Anonymity Preserved: Reporter chose Resource Pack Only. Zero outbound contact permitted.",
                                        color = Slate400,
                                        fontSize = 11.sp
                                    )
                                } else if (isUnlocked) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        if (report.contactName.isNotBlank()) {
                                            Text(text = "Contact Name: ${report.contactName}", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                        }
                                        if (report.contactPhoneNumber.isNotBlank()) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = "Phone: ${report.contactPhoneNumber}", color = SecondaryCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                                val context = LocalContext.current
                                                IconButton(
                                                    onClick = { makePhoneCall(context, report.contactPhoneNumber) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = TealPrimary, modifier = Modifier.size(14.dp))
                                                }
                                            }
                                        }
                                        if (report.safeContactWindow.isNotBlank()) {
                                            Text(text = "Safe Window: ${report.safeContactWindow}", color = Color(0xFFFDE68A), fontSize = 11.sp)
                                        }
                                        if (report.codedCoverStory.isNotBlank()) {
                                            Text(text = "Coded Cover Story: \"${report.codedCoverStory}\"", color = Slate200, fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Case Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    activeActionReport = report
                                    updateNoteText = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Slate800, contentColor = Color.White),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(38.dp)
                            ) {
                                Icon(imageVector = Icons.Default.AssignmentTurnedIn, contentDescription = "Update", modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Update Status", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    } else {
        // Live Anonymous Chat Sessions List (Firestore Realtime)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active Firestore Sessions (${chatSessions.size})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Surface(
                        color = SecondaryCyan.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SecondaryCyan.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "REAL-TIME SYNC",
                            color = SecondaryCyanLight,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            if (chatSessions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No active anonymous chat sessions yet.", color = Slate400)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("New seeker messages will automatically sync here.", color = Slate400, fontSize = 11.sp)
                        }
                    }
                }
            } else {
                items(chatSessions, key = { it.sessionId }) { session ->
                    val dateFormatted = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(session.lastMessageTimestamp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedChatSessionId = session.sessionId
                                viewModel.startAnonymousChat(
                                    category = session.category,
                                    customSessionId = session.sessionId
                                )
                            },
                        colors = CardDefaults.cardColors(containerColor = Slate900),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderHighlight)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(BluePrimary.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ChatBubbleOutline,
                                            contentDescription = null,
                                            tint = BluePrimaryLight,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = session.sessionId,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "${session.seekerAlias} • Area: ${session.category}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = SecondaryCyanLight,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                                Text(
                                    text = dateFormatted,
                                    color = Slate400,
                                    fontSize = 10.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = session.lastMessageText.ifBlank { "Active confidential session" },
                                color = Slate200,
                                fontSize = 12.sp,
                                maxLines = 2
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    selectedChatSessionId = session.sessionId
                                    viewModel.startAnonymousChat(
                                        category = session.category,
                                        customSessionId = session.sessionId
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SecondaryCyan,
                                    contentColor = Slate950
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = "Join Live Chat",
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Join Confidential Live Chat", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // Update Status Dialog
    if (activeActionReport != null) {
        val rep = activeActionReport!!
        var chosenStatus by remember { mutableStateOf(CaseStatus.valueOf(rep.status)) }

        AlertDialog(
            onDismissRequest = { activeActionReport = null },
            title = {
                Text(
                    text = "Update Case Status: ${rep.trackingToken}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "Select new triage / referral stage:", color = Slate200, fontSize = 12.sp)

                    CaseStatus.values().forEach { status ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { chosenStatus = status }
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material3.RadioButton(
                                selected = chosenStatus == status,
                                onClick = { chosenStatus = status },
                                colors = androidx.compose.material3.RadioButtonDefaults.colors(selectedColor = TealPrimary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(status.label, color = Color.White, fontSize = 12.sp)
                        }
                    }

                    OutlinedTextField(
                        value = updateNoteText,
                        onValueChange = { updateNoteText = it },
                        label = { Text("Status Note (Visible to Reporter on Token Check)") },
                        placeholder = { Text("e.g. Assigned to Dr. Sharma. Tele-consultation packet sent.") },
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
                        val finalNote = if (updateNoteText.isBlank()) "Status updated to ${chosenStatus.label}" else updateNoteText
                        viewModel.updateCaseStatusFromPortal(rep.id, chosenStatus, finalNote)
                        activeActionReport = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary, contentColor = Slate950)
                ) {
                    Text("Apply & Sync to Reporter Token", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { activeActionReport = null }) {
                    Text("Cancel", color = Slate400)
                }
            },
            containerColor = Slate900
        )
    }
}
