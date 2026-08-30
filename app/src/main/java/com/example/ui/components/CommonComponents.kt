package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CaseStatus
import com.example.data.model.HelplineResource
import com.example.data.model.IntakeCategory
import com.example.data.model.SeverityTier
import com.example.ui.theme.BlueAccent
import com.example.ui.theme.BlueCobaltGradient
import com.example.ui.theme.BlueDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BluePrimaryLight
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CardBorderHighlight
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.SecondaryCyanLight
import com.example.ui.theme.SeverityCounselling
import com.example.ui.theme.SeverityRehab
import com.example.ui.theme.SeveritySelfHelp
import com.example.ui.theme.SeverityUrgentSos
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.UrgentSosGradient

fun makePhoneCall(context: Context, phoneNumber: String) {
    try {
        val cleanNumber = phoneNumber.replace(Regex("[^0-9+]"), "")
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$cleanNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (_: Exception) {
    }
}

@Composable
fun SayanakEmblemLogo(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 36.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(0xFFE8F5EE))
            .border(1.dp, Color(0xFFCCE2D5), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize(0.72f)) {
            val w = this.size.width
            val h = this.size.height

            // Cupped Hands (Two caring green arcs / leaves)
            val leftHand = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.15f, h * 0.65f)
                quadraticTo(w * 0.20f, h * 0.90f, w * 0.50f, h * 0.95f)
                quadraticTo(w * 0.35f, h * 0.75f, w * 0.30f, h * 0.55f)
                close()
            }
            drawPath(leftHand, color = Color(0xFF0D4739))

            val rightHand = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.85f, h * 0.65f)
                quadraticTo(w * 0.80f, h * 0.90f, w * 0.50f, h * 0.95f)
                quadraticTo(w * 0.65f, h * 0.75f, w * 0.70f, h * 0.55f)
                close()
            }
            drawPath(rightHand, color = Color(0xFF0D4739))

            // Central Warm Healing Heart in Green/Mint
            val heartPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.5f, h * 0.32f)
                cubicTo(w * 0.35f, h * 0.15f, w * 0.15f, h * 0.35f, w * 0.5f, h * 0.70f)
                cubicTo(w * 0.85f, h * 0.35f, w * 0.65f, h * 0.15f, w * 0.5f, h * 0.32f)
                close()
            }
            drawPath(heartPath, color = Color(0xFF005C4B))

            // Upper Leaf / Sparkle
            drawCircle(
                color = Color(0xFF10B981),
                center = androidx.compose.ui.geometry.Offset(w * 0.32f, h * 0.22f),
                radius = w * 0.10f
            )
            drawCircle(
                color = Color(0xFF10B981),
                center = androidx.compose.ui.geometry.Offset(w * 0.68f, h * 0.22f),
                radius = w * 0.10f
            )
        }
    }
}

@Composable
fun SahayakTopBar(
    title: String = "SAYANAK",
    subtitle: String? = "You are not alone 🍃",
    onQuickExit: () -> Unit,
    onEmergencySos: () -> Unit,
    isDarkMode: Boolean = false,
    onToggleTheme: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
        tonalElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Hamburger Menu
            IconButton(
                onClick = onQuickExit,
                modifier = Modifier
                    .size(36.dp)
                    .testTag("top_bar_menu")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Navigation",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Center / Brand: SAYANAK Logo + Title + "You are not alone 🍃"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                SayanakEmblemLogo(size = 34.dp)
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "SAYANAK",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface,
                            letterSpacing = 0.8.sp,
                            fontSize = 17.sp
                        )
                    }
                    Text(
                        text = "You are not alone 🍃",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF3BB273),
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Right Action Icons (Notification Bell, Language Globe, Profile Avatar)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Notifications Bell
                IconButton(
                    onClick = { /* Notifications */ },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("top_bar_notifications")
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Language Globe
                IconButton(
                    onClick = onToggleTheme,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("top_bar_language")
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "Language & Settings",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Profile Avatar Circle
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0D4739))
                        .clickable { /* Profile */ }
                        .testTag("top_bar_profile"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AnonymityTrustBanner(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Slate900.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderHighlight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(BluePrimary.copy(alpha = 0.15f))
                    .border(1.dp, BluePrimary.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Anonymity Guaranteed",
                    tint = BluePrimaryLight,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "100% Anonymous & Retaliation-Protected",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "No account required • Zero media stored • Safe contact reveal only with explicit consent",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate400,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun SeverityTierBadge(
    tier: SeverityTier,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (tier) {
        SeverityTier.SELF_HELP -> SeveritySelfHelp to Color(0xFFD1FAE5)
        SeverityTier.COUNSELLING -> SeverityCounselling to Color(0xFFE0F2FE)
        SeverityTier.REHAB_SPECIALIST -> SeverityRehab to Color(0xFFFEF3C7)
        SeverityTier.URGENT_SOS -> SeverityUrgentSos to Color.White
    }

    Surface(
        color = bgColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, bgColor.copy(alpha = 0.7f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(bgColor)
            )
            Text(
                text = tier.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun CaseStatusBadge(
    statusStr: String,
    modifier: Modifier = Modifier
) {
    val status = try {
        CaseStatus.valueOf(statusStr)
    } catch (_: Exception) {
        CaseStatus.TRIAGED
    }
    val badgeColor = Color(status.badgeColorHex)

    Surface(
        color = badgeColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, badgeColor.copy(alpha = 0.6f)),
        modifier = modifier
    ) {
        Text(
            text = status.label,
            color = badgeColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun HelplineCard(
    helpline: HelplineResource,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = helpline.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    if (helpline.is24x7) {
                        Surface(
                            color = BluePrimary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(0.5.dp, BluePrimary.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "24/7 FREE",
                                color = BluePrimaryLight,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = helpline.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate400,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Dial: ${helpline.number}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryCyanLight
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = { makePhoneCall(context, helpline.number) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BluePrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .testTag("call_${helpline.number}")
                    .height(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call ${helpline.title}",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Call",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

