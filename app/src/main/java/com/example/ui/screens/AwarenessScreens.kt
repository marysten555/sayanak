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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.IntakeCategory
import com.example.data.model.ReporterRole
import com.example.data.repository.SampleData
import com.example.ui.components.AnonymityTrustBanner
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.SeverityUrgentSos
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.TealPrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SahayakViewModel

// --- SIGNS IT'S NO LONGER JUST A PHASE (Section 3.1 #2) ---
@Composable
fun SignsExplainerScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val selectedCategory by viewModel.selectedExplainerCategory.collectAsState()
    var currentCat by remember { mutableStateOf(selectedCategory) }

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
                    text = "Signs It's No Longer Just a Phase",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Breaking Long-Term Tolerance",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TealPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Families often normalize abuse, addiction, or emotional decline for years until it turns into a silent, dangerous crisis. This guide outlines key indicators that professional triage is required.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate200,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Category Switcher Tabs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                IntakeCategory.values().forEach { cat ->
                    val isSelected = currentCat == cat
                    Button(
                        onClick = { currentCat = cat },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) TealPrimary else CardBackground,
                            contentColor = if (isSelected) Slate950 else Slate200
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f).height(40.dp),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Text(
                            text = when (cat) {
                                IntakeCategory.ADDICTION -> "Addiction"
                                IntakeCategory.ABUSE -> "Abuse"
                                IntakeCategory.MENTAL_HEALTH -> "Mental"
                                IntakeCategory.ACADEMIC_STRESS -> "Academic"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Detailed Category Signs
        item {
            val signs = SampleData.signsNotJustAPhase[currentCat] ?: emptyList()
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${currentCat.displayName}: Critical Shift Indicators",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    signs.forEachIndexed { index, sign ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(TealPrimary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    color = TealPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = sign,
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate200,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Take Action Button
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    viewModel.startIntake(category = currentCat, role = ReporterRole.THIRD_PARTY)
                },
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary, contentColor = Slate950),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("start_intake_from_signs_button")
                    .height(48.dp)
            ) {
                Text("Start Anonymous Intake for ${currentCat.displayName}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Start", modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// --- TEEN PEER SELF-CHECK (Section 3.1 #3 & Section 4.4) ---
@Composable
fun TeenCheckScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val teenState by viewModel.teenCheckState.collectAsState()

    var sleep by remember { mutableStateOf(teenState.sleepIssues) }
    var appetite by remember { mutableStateOf(teenState.appetiteIssues) }
    var withdrawal by remember { mutableStateOf(teenState.socialWithdrawal) }
    var grades by remember { mutableStateOf(teenState.gradesDrop) }
    var hopelessness by remember { mutableStateOf(teenState.hopelessnessLanguage) }
    var suicidal by remember { mutableStateOf(teenState.suicidalThoughts) }

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
                    text = "Teen & Student Peer Self-Check",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "100% Confidential & Peer-Friendly",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryCyan
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Take 60 seconds to check in on yourself or a friend. No email or personal data recorded.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate200,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // 5 Core Indicators
        item {
            Text(
                text = "Check all that apply over the past 2 weeks:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            TeenCheckOptionItem(
                label = "Sleep disruption: Unable to sleep, erratic sleep, or sleeping all day",
                checked = sleep,
                onCheckedChange = { sleep = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TeenCheckOptionItem(
                label = "Appetite & energy: Skipping meals regularly or constant exhaustion",
                checked = appetite,
                onCheckedChange = { appetite = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TeenCheckOptionItem(
                label = "Social withdrawal: Ghosting group chats, avoiding friends and family",
                checked = withdrawal,
                onCheckedChange = { withdrawal = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TeenCheckOptionItem(
                label = "Grades & motivation: Sudden inability to study or attend classes",
                checked = grades,
                onCheckedChange = { grades = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TeenCheckOptionItem(
                label = "Hopelessness language: Feeling like a burden, constant self-blame",
                checked = hopelessness,
                onCheckedChange = { hopelessness = it }
            )
        }

        // Critical Suicidal Ideation Check (Direct SOS trigger as per spec)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = if (suicidal) Color(0xFF450A0A) else CardBackground),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (suicidal) SeverityUrgentSos else CardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Thoughts of self-harm or ending your life?",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (suicidal) Color(0xFFFCA5A5) else Color.White
                        )
                        Text(
                            text = "Any answer indicating self-harm skips scoring and connects to crisis support immediately.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate400,
                            fontSize = 11.sp
                        )
                    }
                    Switch(
                        checked = suicidal,
                        onCheckedChange = { suicidal = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = SeverityUrgentSos,
                            checkedTrackColor = Color(0xFF7F1D1D)
                        )
                    )
                }
            }
        }

        // Result Card if evaluated
        if (teenState.isCompleted && !teenState.isCrisisTriggered) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Slate900),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TealPrimary)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Check-In Assessment",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = TealPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = teenState.resultSummary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        item {
            Button(
                onClick = {
                    viewModel.updateTeenCheck(
                        sleep = sleep,
                        appetite = appetite,
                        withdrawal = withdrawal,
                        grades = grades,
                        hopelessness = hopelessness,
                        suicidal = suicidal
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary, contentColor = Slate950),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("submit_teen_check_button")
                    .height(48.dp)
            ) {
                Text("Evaluate Peer Check", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun TeenCheckOptionItem(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) },
        colors = CardDefaults.cardColors(containerColor = if (checked) Slate800 else CardBackground),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (checked) TealPrimary else CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(checkedColor = TealPrimary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, color = Color.White, fontSize = 12.sp, lineHeight = 16.sp)
        }
    }
}
