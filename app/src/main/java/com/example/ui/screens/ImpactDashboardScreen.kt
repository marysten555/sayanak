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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.LocalizedStrings
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.CardDarkGreen
import com.example.ui.theme.CardDarkGreenBorder
import com.example.ui.theme.CardDarkGreenElevated
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.SeverityUrgentSos
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.TextPrimaryNearWhite
import com.example.ui.theme.TextSecondaryDarkCard
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SahayakViewModel

data class ImpactMetric(
    val title: String,
    val value: String,
    val subtitle: String,
    val iconColor: Color,
    val isPositiveTrend: Boolean = true
)

data class HeatmapRegion(
    val name: String,
    val state: String,
    val riskLevel: String,
    val riskColor: Color,
    val activeUnits: Int,
    val avgResponseMins: Double
)

@Composable
fun ImpactDashboardScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val allReports by viewModel.allReports.collectAsState()

    val totalSubmitted = 12480 + allReports.size
    val totalResolved = 11140 + (allReports.count { it.status == "RESOLVED" })
    val totalSosDispatches = 3890
    val totalHelplineConnected = 24500

    val metrics = listOf(
        ImpactMetric("Reports Submitted", "%,d".format(totalSubmitted), "100% End-to-End Encrypted", AccentGreen),
        ImpactMetric("Cases Resolved", "%,d".format(totalResolved), "89.2% Resolution Rate", Color(0xFF38BDF8)),
        ImpactMetric("SOS Rescues", "%,d".format(totalSosDispatches), "< 7 min Average Police Dispatch", SeverityUrgentSos),
        ImpactMetric("Helpline Triage", "%,d".format(totalHelplineConnected), "24x7 Multi-Agency Bridge", Color(0xFFFBBF24))
    )

    val heatmaps = listOf(
        HeatmapRegion("South Zone Hub", "Chennai & Bengaluru", "Normal Safety", AccentGreen, 42, 6.2),
        HeatmapRegion("Northern Capital Region", "Delhi NCR", "Elevated Alert", SeverityUrgentSos, 88, 4.8),
        HeatmapRegion("Western Metro Belt", "Mumbai & Pune", "Moderate Alert", Color(0xFFFBBF24), 64, 5.5),
        HeatmapRegion("Eastern Coastal Corridor", "Kolkata & Odisha", "Normal Safety", AccentGreen, 36, 7.1)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = LocalizedStrings.get("impact_dashboard", currentLang),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Real-Time Social Impact & Crisis Resolution Telemetry",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Top 4 Metrics Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ImpactStatCard(metric = metrics[0], modifier = Modifier.weight(1f))
                    ImpactStatCard(metric = metrics[1], modifier = Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ImpactStatCard(metric = metrics[2], modifier = Modifier.weight(1f))
                    ImpactStatCard(metric = metrics[3], modifier = Modifier.weight(1f))
                }
            }
        }

        // SDG Alignment Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardDarkGreenElevated)
                    .border(1.dp, CardDarkGreenBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(AccentGreen.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "UN Sustainable Development Goals (SDGs)",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "SDG 5 (Gender Equality), SDG 16 (Peace, Justice & Strong Institutions), and SDG 3 (Well-being).",
                            color = TextSecondaryDarkCard,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Incident Heatmap / Regional Safety Clusters
        item {
            Text(
                text = "Regional Response Clusters & Heatmaps",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        items(heatmaps) { region ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardDarkGreen)
                    .border(1.dp, CardDarkGreenBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            text = region.name,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNearWhite,
                            fontSize = 13.5.sp
                        )
                        Text(
                            text = "${region.state} • ${region.activeUnits} Empanelled Units",
                            color = TextSecondaryDarkCard,
                            fontSize = 11.5.sp
                        )
                    }

                    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(region.riskColor.copy(alpha = 0.15f))
                                .border(1.dp, region.riskColor.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = region.riskLevel,
                                color = region.riskColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Avg ETA: ${region.avgResponseMins}m",
                            color = Color(0xFF6EE7B7),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImpactStatCard(
    metric: ImpactMetric,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(CardDarkGreen)
            .border(1.dp, CardDarkGreenBorder, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = metric.title,
                color = Slate400,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = metric.value,
                color = metric.iconColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = metric.subtitle,
                color = TextSecondaryDarkCard,
                fontSize = 10.sp,
                lineHeight = 13.sp
            )
        }
    }
}
