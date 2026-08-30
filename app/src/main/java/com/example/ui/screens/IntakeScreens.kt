package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ConsentType
import com.example.data.model.IntakeCategory
import com.example.data.model.ReporterRole
import com.example.data.model.SeverityTier
import com.example.engine.IntakeAnswers
import com.example.ui.components.AnonymityTrustBanner
import com.example.ui.components.SeverityTierBadge
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.CardDarkGreen
import com.example.ui.theme.CardDarkGreenBorder
import com.example.ui.theme.CardDarkGreenElevated
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.InputBackground
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.SelectedCardBrush
import com.example.ui.theme.SeverityUrgentSos
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.TealDark
import com.example.ui.theme.TealPrimary
import com.example.ui.theme.TextPrimaryNearWhite
import com.example.ui.theme.TextSecondaryDarkCard
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SahayakViewModel

// --- SCREEN 1: Role & Category Selection ---
@Composable
fun IntakeRoleCategoryScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.intakeState.collectAsState()
    var selectedRole by remember { mutableStateOf(state.reporterRole) }
    var relationshipText by remember { mutableStateOf(state.relationshipToPerson) }
    var selectedCategory by remember { mutableStateOf(state.category) }

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
                    text = "Step 1 of 3: Reporting Context",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        item {
            AnonymityTrustBanner()
        }

        item {
            Text(
                text = "Who is this report for?",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Role choice cards
            RoleSelectionCard(
                role = ReporterRole.SELF,
                isSelected = selectedRole == ReporterRole.SELF,
                icon = Icons.Outlined.Person,
                title = "I am reporting for myself",
                subtitle = "Directly assess my own symptoms, safety, or stress.",
                onSelect = {
                    selectedRole = ReporterRole.SELF
                    relationshipText = "Self"
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            RoleSelectionCard(
                role = ReporterRole.THIRD_PARTY,
                isSelected = selectedRole == ReporterRole.THIRD_PARTY,
                icon = Icons.Outlined.FamilyRestroom,
                title = "I am reporting on behalf of someone else",
                subtitle = "Family member, friend, classmate, or colleague.",
                onSelect = {
                    selectedRole = ReporterRole.THIRD_PARTY
                    if (relationshipText == "Self") relationshipText = "Family Member"
                }
            )

            if (selectedRole == ReporterRole.THIRD_PARTY) {
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = relationshipText,
                    onValueChange = { relationshipText = it },
                    label = { Text("Your relationship (e.g. Sibling, Parent, Friend)", fontSize = 12.sp) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = InputBackground,
                        unfocusedContainerColor = InputBackground,
                        focusedBorderColor = TealPrimary,
                        unfocusedBorderColor = Slate700,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Select primary concern category:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            IntakeCategory.values().forEach { category ->
                val isSelected = selectedCategory == category
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .then(
                            if (isSelected) Modifier.background(SelectedCardBrush)
                            else Modifier.background(CardDarkGreen)
                        )
                        .border(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) AccentGreen else CardDarkGreenBorder,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { selectedCategory = category }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { selectedCategory = category },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (isSelected) Color.White else AccentGreen,
                                unselectedColor = TextSecondaryDarkCard
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = category.displayName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else TextPrimaryNearWhite
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = category.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isSelected) Color(0xFFE2F0E9) else TextSecondaryDarkCard,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.setIntakeCategory(selectedCategory)
                    viewModel.setReporterRole(selectedRole, relationshipText)
                    viewModel.proceedToQuestions()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentGreen,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("continue_to_questions_button")
                    .height(50.dp)
            ) {
                Text(
                    text = "Continue to Behavior Checklist",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun RoleSelectionCard(
    role: ReporterRole,
    isSelected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .then(
                if (isSelected) Modifier.background(SelectedCardBrush)
                else Modifier.background(CardDarkGreen)
            )
            .border(
                if (isSelected) 2.dp else 1.dp,
                if (isSelected) AccentGreen else CardDarkGreenBorder,
                RoundedCornerShape(14.dp)
            )
            .clickable { onSelect() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color.White.copy(alpha = 0.22f) else CardDarkGreenElevated)
                    .border(1.dp, if (isSelected) Color.White.copy(alpha = 0.4f) else CardDarkGreenBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isSelected) Color.White else AccentGreen,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else TextPrimaryNearWhite
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) Color(0xFFE2F0E9) else TextSecondaryDarkCard,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

// --- SCREEN 2: Structured Behavior Questionnaire (No photo/video) ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IntakeQuestionsScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.intakeState.collectAsState()
    var currentAnswers by remember { mutableStateOf(state.answers) }

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
                    IconButton(onClick = { viewModel.navigateTo(AppScreen.INTAKE_ROLE_CATEGORY) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Column {
                        Text(
                            text = "Step 2 of 3: Behavior Checklist",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = state.category.displayName,
                            style = MaterialTheme.typography.bodySmall,
                            color = TealPrimary
                        )
                    }
                }
            }
        }

        // Responsible design reminder
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Shield, contentDescription = "Privacy", tint = TealPrimary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Zero photo or video evidence collected. Select only observable behaviors. 'Unsure' is always valid.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate200,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Dynamic Questions based on Category
        when (state.category) {
            IntakeCategory.ADDICTION -> {
                item {
                    Text(
                        text = "1. Observable Physical Signs",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val physicalList = listOf(
                        "Bloodshot or glassy eyes",
                        "Sudden drastic weight loss/gain",
                        "Unusual chemical/pungent smell",
                        "Visible tremors or shaking hands",
                        "Slurred or erratic speech",
                        "Skin marks / needle punctures / sores"
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        physicalList.forEach { sign ->
                            val isChecked = currentAnswers.physicalSigns.contains(sign)
                            FilterChip(
                                selected = isChecked,
                                onClick = {
                                    val newSet = currentAnswers.physicalSigns.toMutableSet()
                                    if (isChecked) newSet.remove(sign) else newSet.add(sign)
                                    currentAnswers = currentAnswers.copy(physicalSigns = newSet)
                                },
                                label = { Text(sign, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TealPrimary.copy(alpha = 0.25f),
                                    selectedLabelColor = Color.White,
                                    containerColor = CardBackground,
                                    labelColor = Slate200
                                )
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "2. Observable Behavioral Changes",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val behavList = listOf(
                        "Extremely secretive about whereabouts/phone",
                        "Unexplained missing money or valuables at home",
                        "Sudden shift to unfamiliar new peer group",
                        "Volatile mood swings or unprovoked aggression",
                        "Complete withdrawal from family meals/interactions",
                        "Severe sleep pattern disruptions / up all night",
                        "Sharp drop in work or college attendance"
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        behavList.forEach { sign ->
                            val isChecked = currentAnswers.behavioralSigns.contains(sign)
                            FilterChip(
                                selected = isChecked,
                                onClick = {
                                    val newSet = currentAnswers.behavioralSigns.toMutableSet()
                                    if (isChecked) newSet.remove(sign) else newSet.add(sign)
                                    currentAnswers = currentAnswers.copy(behavioralSigns = newSet)
                                },
                                label = { Text(sign, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SecondaryCyan.copy(alpha = 0.25f),
                                    selectedLabelColor = Color.White,
                                    containerColor = CardBackground,
                                    labelColor = Slate200
                                )
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "3. Duration & Escalation Pattern",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val durations = listOf("< 1 month", "1-6 months", "6-12 months", "> 1 year")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        durations.forEach { dur ->
                            val isSelected = currentAnswers.addictionDuration == dur
                            Button(
                                onClick = { currentAnswers = currentAnswers.copy(addictionDuration = dur) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) TealPrimary else CardBackground,
                                    contentColor = if (isSelected) Slate950 else Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(38.dp),
                                contentPadding = PaddingValues(2.dp)
                            ) {
                                Text(dur, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CardBackground)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Is the situation escalating rapidly?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Switch(
                            checked = currentAnswers.isAddictionEscalating,
                            onCheckedChange = { currentAnswers = currentAnswers.copy(isAddictionEscalating = it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = TealPrimary, checkedTrackColor = TealDark)
                        )
                    }
                }

                item {
                    Text(
                        text = "4. Optional Rough Substance Guess (Optional)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "You are never required to name a drug. 'Unsure' is completely valid.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val guesses = listOf("Alcohol", "Smoking / Inhalants", "Pills / Medications", "Injectable", "Unsure / Unknown")
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        guesses.forEach { guess ->
                            val isSelected = currentAnswers.roughSubstanceGuess == guess
                            FilterChip(
                                selected = isSelected,
                                onClick = { currentAnswers = currentAnswers.copy(roughSubstanceGuess = guess) },
                                label = { Text(guess, fontSize = 12.sp) }
                            )
                        }
                    }
                }
            }

            IntakeCategory.ABUSE -> {
                item {
                    Text(
                        text = "1. What types of incidents have occurred?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val abuseTypes = listOf(
                        "Verbal humiliation / shouting",
                        "Physical hitting / slapping / pushing",
                        "Property destruction / smashing items",
                        "Physical restraint / locking in room",
                        "Financial control / denying money for food/medicine",
                        "Stalking / constant digital surveillance"
                    )

                    abuseTypes.forEach { incident ->
                        val isChecked = currentAnswers.abuseIncidents.contains(incident)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isChecked) Slate800 else CardBackground)
                                .clickable {
                                    val newSet = currentAnswers.abuseIncidents.toMutableSet()
                                    if (isChecked) newSet.remove(incident) else newSet.add(incident)
                                    currentAnswers = currentAnswers.copy(abuseIncidents = newSet)
                                }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    val newSet = currentAnswers.abuseIncidents.toMutableSet()
                                    if (checked) newSet.add(incident) else newSet.remove(incident)
                                    currentAnswers = currentAnswers.copy(abuseIncidents = newSet)
                                },
                                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFF87171))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = incident, color = Color.White, fontSize = 13.sp)
                        }
                    }
                }

                item {
                    Text(
                        text = "2. Physical Harm Indicator (Categorized, not proof)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val harmLevels = listOf(
                        "None",
                        "Bruising",
                        "Visible injury requiring care",
                        "Hospital visit"
                    )

                    harmLevels.forEach { level ->
                        val isSelected = currentAnswers.physicalHarmLevel == level
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable { currentAnswers = currentAnswers.copy(physicalHarmLevel = level) },
                            colors = CardDefaults.cardColors(containerColor = if (isSelected) Slate800 else CardBackground),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) Color(0xFFF87171) else CardBorder)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { currentAnswers = currentAnswers.copy(physicalHarmLevel = level) },
                                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFF87171))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(level, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "3. Critical Risk & Escalation Factors (DASH-Aligned)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "These factors are the most predictive of severe immediate danger.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    DashQuestionSwitch(
                        label = "Has a weapon been involved or mentioned as a threat?",
                        checked = currentAnswers.hasWeaponInvolved,
                        isHighDanger = true,
                        onCheckedChange = { currentAnswers = currentAnswers.copy(hasWeaponInvolved = it) }
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    DashQuestionSwitch(
                        label = "Has the person threatened to kill you or loved ones?",
                        checked = currentAnswers.hasThreatenedToKill,
                        isHighDanger = true,
                        onCheckedChange = { currentAnswers = currentAnswers.copy(hasThreatenedToKill = it) }
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    DashQuestionSwitch(
                        label = "Has the abuse or threats escalated in frequency recently?",
                        checked = currentAnswers.hasEscalatedRecently,
                        onCheckedChange = { currentAnswers = currentAnswers.copy(hasEscalatedRecently = it) }
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    DashQuestionSwitch(
                        label = "Has the person harmed a pet or threatened dependents?",
                        checked = currentAnswers.hasHurtPet,
                        onCheckedChange = { currentAnswers = currentAnswers.copy(hasHurtPet = it) }
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    DashQuestionSwitch(
                        label = "Does the person strictly control your movement or phone access?",
                        checked = currentAnswers.hasControlOverMoneyOrMovement,
                        onCheckedChange = { currentAnswers = currentAnswers.copy(hasControlOverMoneyOrMovement = it) }
                    )
                }
            }

            IntakeCategory.MENTAL_HEALTH -> {
                item {
                    Text(
                        text = "PHQ-9 & GAD-7 Scaled Behavioral Check",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Over the last 2 weeks, how often have you or your loved one experienced the following?",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400,
                        fontSize = 11.sp
                    )
                }

                item {
                    MentalHealthScaleItem(
                        title = "1. Persistent sadness, emptiness, or feeling down",
                        value = currentAnswers.moodSadnessScore,
                        onValueChange = { currentAnswers = currentAnswers.copy(moodSadnessScore = it) }
                    )
                }

                item {
                    MentalHealthScaleItem(
                        title = "2. Trouble falling asleep, staying asleep, or sleeping excessively",
                        value = currentAnswers.sleepDisruptionScore,
                        onValueChange = { currentAnswers = currentAnswers.copy(sleepDisruptionScore = it) }
                    )
                }

                item {
                    MentalHealthScaleItem(
                        title = "3. Severe energy collapse, fatigue, or poor appetite",
                        value = currentAnswers.appetiteEnergyScore,
                        onValueChange = { currentAnswers = currentAnswers.copy(appetiteEnergyScore = it) }
                    )
                }

                item {
                    MentalHealthScaleItem(
                        title = "4. Feelings of hopelessness, worthlessness, or feeling like a burden",
                        value = currentAnswers.hopelessnessScore,
                        onValueChange = { currentAnswers = currentAnswers.copy(hopelessnessScore = it) }
                    )
                }

                item {
                    MentalHealthScaleItem(
                        title = "5. Complete social withdrawal and loss of interest in all activities",
                        value = currentAnswers.socialWithdrawalScore,
                        onValueChange = { currentAnswers = currentAnswers.copy(socialWithdrawalScore = it) }
                    )
                }

                // Mandatory Suicidal Ideation / Safety Check
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = if (currentAnswers.hasSuicidalIdeation) Color(0xFF450A0A) else CardBackground),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (currentAnswers.hasSuicidalIdeation) SeverityUrgentSos else CardBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Are there thoughts of self-harm or suicide?",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (currentAnswers.hasSuicidalIdeation) Color(0xFFFCA5A5) else Color.White
                                    )
                                    Text(
                                        text = "Flagging yes immediately activates crisis support and emergency contacts.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Slate400,
                                        fontSize = 11.sp
                                    )
                                }
                                Switch(
                                    checked = currentAnswers.hasSuicidalIdeation,
                                    onCheckedChange = { currentAnswers = currentAnswers.copy(hasSuicidalIdeation = it) },
                                    colors = SwitchDefaults.colors(checkedThumbColor = SeverityUrgentSos, checkedTrackColor = Color(0xFF7F1D1D))
                                )
                            }
                        }
                    }
                }
            }

            IntakeCategory.ACADEMIC_STRESS -> {
                item {
                    Text(
                        text = "1. Current Academic Workload & Pressure",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    val workloads = listOf("Mild", "Moderate", "Heavy", "Overwhelming")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        workloads.forEach { wl ->
                            val isSelected = currentAnswers.workloadLevel == wl
                            Button(
                                onClick = { currentAnswers = currentAnswers.copy(workloadLevel = wl) },
                                colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) TealPrimary else CardBackground, contentColor = if (isSelected) Slate950 else Color.White),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(38.dp),
                                contentPadding = PaddingValues(2.dp)
                            ) {
                                Text(wl, fontSize = 11.sp)
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "2. Exam Proximity",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    val proximities = listOf("Within a week", "Within a month", "Over a month away")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        proximities.forEach { prox ->
                            val isSelected = currentAnswers.examProximity == prox
                            Button(
                                onClick = { currentAnswers = currentAnswers.copy(examProximity = prox) },
                                colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) SecondaryCyan else CardBackground, contentColor = if (isSelected) Slate950 else Color.White),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(38.dp),
                                contentPadding = PaddingValues(2.dp)
                            ) {
                                Text(prox, fontSize = 11.sp)
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "3. Coping Difficulty & Panic",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    val copings = listOf("Manageable", "Challenging", "Severe", "Paralyzing")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        copings.forEach { cp ->
                            val isSelected = currentAnswers.academicCopingDifficulty == cp
                            Button(
                                onClick = { currentAnswers = currentAnswers.copy(academicCopingDifficulty = cp) },
                                colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) Color(0xFFFBBF24) else CardBackground, contentColor = if (isSelected) Slate950 else Color.White),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(38.dp),
                                contentPadding = PaddingValues(2.dp)
                            ) {
                                Text(cp, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    DashQuestionSwitch(
                        label = "Experiencing panic attacks, hyperventilation, or study paralysis?",
                        checked = currentAnswers.hasPanicEpisodes,
                        onCheckedChange = { currentAnswers = currentAnswers.copy(hasPanicEpisodes = it) }
                    )
                }
            }
        }

        // Additional Context Free-Text Note
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Additional Context (Optional)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = currentAnswers.additionalContextNote,
                onValueChange = { currentAnswers = currentAnswers.copy(additionalContextNote = it) },
                placeholder = { Text("Any specific patterns, concerns or context you wish to mention safely...", color = Slate400, fontSize = 12.sp) },
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputBackground,
                    unfocusedContainerColor = InputBackground,
                    focusedBorderColor = TealPrimary,
                    unfocusedBorderColor = Slate700,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Submit to Scoring Engine Button
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.updateIntakeAnswers(currentAnswers)
                    viewModel.calculateTriageScore()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TealPrimary,
                    contentColor = Slate950
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("run_triage_score_button")
                    .height(50.dp)
            ) {
                Text(
                    text = "Calculate Triage Score & Routing",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Submit",
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun DashQuestionSwitch(
    label: String,
    checked: Boolean,
    isHighDanger: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (checked && isHighDanger) Color(0xFF450A0A) else CardBackground)
            .border(1.dp, if (checked && isHighDanger) SeverityUrgentSos else CardBorder, RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (checked && isHighDanger) Color(0xFFFCA5A5) else Color.White,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = if (isHighDanger) SeverityUrgentSos else TealPrimary,
                checkedTrackColor = if (isHighDanger) Color(0xFF7F1D1D) else TealDark
            )
        )
    }
}

@Composable
private fun MentalHealthScaleItem(
    title: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))

            val options = listOf("Not at all (0)", "Several days (1)", "More than half (2)", "Nearly every day (3)")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                options.forEachIndexed { index, opt ->
                    val isSelected = value == index
                    Button(
                        onClick = { onValueChange(index) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) TealPrimary else Slate800,
                            contentColor = if (isSelected) Slate950 else Slate200
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f).height(36.dp),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Text(index.toString(), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// --- SCREEN 3: Triage Result, Transparent Rules & Consent Gating ---
@Composable
fun IntakeResultScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val currentLang by viewModel.currentLanguage.collectAsState()
    val state by viewModel.intakeState.collectAsState()
    val scoring = state.scoringResult

    var consentType by remember { mutableStateOf(state.consentType) }
    var safeWindow by remember { mutableStateOf(state.safeContactWindow) }
    var coverStory by remember { mutableStateOf(state.codedCoverStory) }
    var contactName by remember { mutableStateOf(state.contactName) }
    var contactPhone by remember { mutableStateOf(state.contactPhone) }
    var city by remember { mutableStateOf(state.roughLocationCity) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.INTAKE_QUESTIONS) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = "Step 3 of 3: Triage Score & Consent",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Triage Result Summary Card
        if (scoring != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Slate900),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, TealPrimary)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Automated Severity Result",
                                style = MaterialTheme.typography.labelLarge,
                                color = Slate400
                            )
                            SeverityTierBadge(tier = scoring.tier)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = scoring.tier.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = scoring.recommendedActionSummary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFE2E8F0),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            color = Slate800,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Transparent Decision Tree Audit Breakdown:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryCyan
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                scoring.explanationRules.forEach { rule ->
                                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                        Text(text = "• ", color = TealPrimary, fontWeight = FontWeight.Bold)
                                        Text(text = rule, color = Slate200, fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Recommended Helplines for this victim report
            item {
                val isUrgent = scoring.tier == SeverityTier.URGENT_SOS
                val suggested = com.example.data.repository.SampleData.getSuggestedHelplinesForCategory(state.category.name, isUrgent)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Slate900),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isUrgent) SeverityUrgentSos else TealPrimary)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Helplines",
                                tint = if (isUrgent) SeverityUrgentSos else TealPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = when (currentLang) {
                                    com.example.localization.AppLanguage.TAMIL -> "உங்கள் சூழ்நிலைக்கான பரிந்துரைக்கப்பட்ட உதவி எண்கள்"
                                    com.example.localization.AppLanguage.HINDI -> "आपकी स्थिति हेतु अनुशंसित आपातकालीन हेल्पलाइन"
                                    else -> "Recommended Helplines For Your Situation"
                                },
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        suggested.forEach { helpline ->
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
                                        Text(
                                            text = helpline.getLocalizedTitle(currentLang),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            fontSize = 13.sp
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = helpline.getLocalizedDescription(currentLang),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Slate400,
                                            fontSize = 11.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = {
                                            val dialIntent = android.content.Intent(android.content.Intent.ACTION_DIAL, android.net.Uri.parse("tel:${helpline.number}"))
                                            context.startActivity(dialIntent)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (helpline.number == "112" || isUrgent) SeverityUrgentSos else TealPrimary,
                                            contentColor = if (helpline.number == "112" || isUrgent) Color.White else Slate950
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(14.dp))
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

        // Consent Choice Selection (Section 3.2 #8)
        item {
            Text(
                text = "Consent Level: How would you like to proceed?",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Centre / Counsellor sees only an anonymized case summary until you explicitly consent to reveal contact details.",
                style = MaterialTheme.typography.bodySmall,
                color = Slate400,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            ConsentType.values().forEach { option ->
                val isSelected = consentType == option
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .then(
                            if (isSelected) Modifier.background(SelectedCardBrush)
                            else Modifier.background(CardDarkGreen)
                        )
                        .border(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) AccentGreen else CardDarkGreenBorder,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { consentType = option }
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { consentType = option },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (isSelected) Color.White else AccentGreen,
                                unselectedColor = TextSecondaryDarkCard
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = option.label,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else TextPrimaryNearWhite
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = option.detail,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isSelected) Color(0xFFE2F0E9) else TextSecondaryDarkCard,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // Optional Safe Contact Window & Coded Cover Story Configuration (Section 5)
        if (consentType != ConsentType.RESOURCE_PACK_ONLY) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Slate900),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Schedule, contentDescription = "Safe Window", tint = SecondaryCyan, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Safe Callback Window & Cover Story",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = safeWindow,
                            onValueChange = { safeWindow = it },
                            label = { Text("Safe Contact Time Window (e.g. Mon-Fri 2-4 PM)") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = InputBackground,
                                unfocusedContainerColor = InputBackground,
                                focusedBorderColor = TealPrimary,
                                unfocusedBorderColor = Slate700,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = coverStory,
                            onValueChange = { coverStory = it },
                            label = { Text("Coded Cover Story (e.g. 'Calling as college survey coordinator')") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = InputBackground,
                                unfocusedContainerColor = InputBackground,
                                focusedBorderColor = TealPrimary,
                                unfocusedBorderColor = Slate700,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = contactName,
                                onValueChange = { contactName = it },
                                label = { Text("Name / Alias") },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = InputBackground,
                                    unfocusedContainerColor = InputBackground,
                                    focusedBorderColor = TealPrimary,
                                    unfocusedBorderColor = Slate700,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = contactPhone,
                                onValueChange = { contactPhone = it },
                                label = { Text("Phone Number") },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = InputBackground,
                                    unfocusedContainerColor = InputBackground,
                                    focusedBorderColor = TealPrimary,
                                    unfocusedBorderColor = Slate700,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.weight(1.3f)
                            )
                        }
                    }
                }
            }
        }

        // Final Submission Button
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    viewModel.setConsentDetails(
                        consentType = consentType,
                        safeWindow = safeWindow,
                        coverStory = coverStory,
                        name = contactName,
                        phone = contactPhone,
                        city = city
                    )
                    viewModel.submitReportFinal()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TealPrimary,
                    contentColor = Slate950
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("submit_anonymous_report_button")
                    .height(52.dp)
            ) {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Submit", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Submit Anonymous Report & Generate Token",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// --- SCREEN 4: Confirmation & Tracking Token ---
@Composable
fun IntakeConfirmationScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.intakeState.collectAsState()
    val token = state.generatedToken
    val context = LocalContext.current
    var copied by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950),
        contentPadding = PaddingValues(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(TealPrimary.copy(alpha = 0.2f))
                    .border(2.dp, TealPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Success",
                    tint = TealPrimary,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Anonymous Report Submitted",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Your identity is encrypted. Save your tracking token below to check updates safely.",
                style = MaterialTheme.typography.bodyMedium,
                color = Slate400,
                fontSize = 13.sp
            )
        }

        // Generated Token Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, TealPrimary)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "YOUR ANONYMOUS TRACKING TOKEN",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryCyan,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = token,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Sahayak Token", token)
                            clipboard.setPrimaryClip(clip)
                            copied = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Slate800, contentColor = Color.White),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = if (copied) Icons.Default.Check else Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = if (copied) "Copied to Clipboard!" else "Copy Token", fontSize = 12.sp)
                    }
                }
            }
        }

        // Post-Report Check-In Guidance (Section 5)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Safety & Retaliation Shield",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• You can return anytime and paste this token to check status.\n• If the situation worsens or someone threatens you for reporting, use the Post-Report Check-In inside your token dashboard to trigger immediate protective escalation.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate200,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        viewModel.lookupToken(token)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary, contentColor = Slate950),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(46.dp)
                ) {
                    Text("View My Report", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { viewModel.navigateTo(AppScreen.HOME) },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(46.dp)
                ) {
                    Text("Return Home")
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
