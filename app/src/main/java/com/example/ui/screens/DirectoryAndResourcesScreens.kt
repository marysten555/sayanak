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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CenterEntity
import com.example.data.repository.SampleData
import com.example.ui.components.makePhoneCall
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.TealPrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SahayakViewModel

@Composable
fun DirectoryScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val centers by viewModel.allCenters.collectAsState()
    var selectedCategory by remember { mutableStateOf("ALL") }
    val context = LocalContext.current

    val filteredCenters = centers.filter {
        selectedCategory == "ALL" || it.categorySupported == selectedCategory || it.categorySupported == "ALL"
    }

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
                Column {
                    Text(
                        text = "Empanelled Support Directory",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Verified De-addiction, Mental Health & Shelter Facilities",
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryCyan
                    )
                }
            }
        }

        // Category Filter
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val filterList = listOf("ALL", "ADDICTION", "ABUSE", "MENTAL_HEALTH", "ACADEMIC_STRESS")
                filterList.forEach { f ->
                    val isSelected = selectedCategory == f
                    Button(
                        onClick = { selectedCategory = f },
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

        // Center Items
        items(filteredCenters, key = { it.id }) { center ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Domain,
                                contentDescription = "Center",
                                tint = TealPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = center.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        if (center.isGovernmentEmpanelled) {
                            Surface(
                                color = SecondaryCyan.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Govt",
                                        tint = SecondaryCyan,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "Govt / Apex",
                                        color = SecondaryCyan,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location", tint = Slate400, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${center.city}, ${center.state} • ${center.categorySupported}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate400,
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = center.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate200,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Helpline: ${center.contactPhone}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryCyan
                        )

                        Button(
                            onClick = { makePhoneCall(context, center.contactPhone) },
                            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary, contentColor = Slate950),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Call", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// --- SELF-HELP RESOURCE PACKS & SAFETY GUIDES (Section 3.2 #8) ---
@Composable
fun ResourcesScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
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
                Column {
                    Text(
                        text = "Confidential Self-Help Guides",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Coping Strategies, Grounding & Legal Protections",
                        style = MaterialTheme.typography.bodySmall,
                        color = TealPrimary
                    )
                }
            }
        }

        // Guide 1: 5-4-3-2-1 Somatic Grounding
        item {
            ResourceGuideCard(
                title = "5-4-3-2-1 Sensory Grounding for Acute Anxiety",
                category = "Mental Health & Panic",
                content = """
                    When your nervous system is overwhelmed, engage your senses to return to the present:
                    • 5 Things you can SEE: Look for small details around you (a pattern on the floor, light reflecting).
                    • 4 Things you can TOUCH: Feel the texture of your shirt, the ground under your feet, the coolness of a desk.
                    • 3 Things you can HEAR: Listen for ambient sounds (fan hum, distant traffic, your own steady breath).
                    • 2 Things you can SMELL: Notice any scents in the room, soap, or fresh air.
                    • 1 Thing you can TASTE: Sip water or notice the taste inside your mouth.
                    Breathe in for 4 seconds, hold for 4 seconds, and exhale slowly for 6 seconds.
                """.trimIndent()
            )
        }

        // Guide 2: Approaching a Loved One with Addiction
        item {
            ResourceGuideCard(
                title = "How to Approach a Loved One Showing Addiction Signs",
                category = "Addiction Support",
                content = """
                    • Pick a Calm Moment: Never initiate the conversation while they are actively intoxicated, agitated, or in a hurry.
                    • Use 'I' Statements: Say 'I feel worried when I see you not eating or struggling to sleep' instead of 'You are ruining your life'.
                    • Avoid Demanding Confessions: Do not interrogate them for exact drug names. Focus purely on their emotional wellbeing and physical health.
                    • Offer Sahayak's Anonymous Triage: Show them that support does not mean public shame or forced lockups—it starts with an anonymous check.
                """.trimIndent()
            )
        }

        // Guide 3: Domestic Abuse Safety Planning
        item {
            ResourceGuideCard(
                title = "Safety Planning & Retaliation Shielding",
                category = "Domestic Abuse & Safety",
                content = """
                    • Safe Contact Window: Always agree with counsellors on exact times when the abuser is away (e.g., commute hours or daytime).
                    • Code Words: Establish a simple codeword (e.g. 'Did you order the groceries?') with a trusted friend to indicate you need them to call 112.
                    • Important Documents: Keep digital photos of Aadhaar, bank passbook, and child documents in a secure hidden folder or with a trusted relative.
                    • PWDVA Legal Shield: Under Section 12 of the Domestic Violence Act, you are entitled to free protection officers, safe shelter, and residence orders.
                """.trimIndent()
            )
        }

        // Guide 4: Overcoming Exam Burnout & Paralyzing Fear
        item {
            ResourceGuideCard(
                title = "Recovering from Academic Paralysis & Exam Fear",
                category = "Academic Stress",
                content = """
                    • The 5-Minute Micro Start: When paralysis sets in, commit to opening only 1 page for 5 minutes. The fear is always higher than the actual effort.
                    • Sleep Non-Negotiable: Depriving yourself of sleep severely impairs working memory. 6 hours of sleep yields 30% higher exam retention than all-nighters.
                    • Reframe Stakes: An exam is a snapshot of one syllabus at one point in time—it has zero determination over your lifelong human worth and potential.
                """.trimIndent()
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ResourceGuideCard(
    title: String,
    category: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = TealPrimary.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = category,
                    color = TealPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = Slate200,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}
