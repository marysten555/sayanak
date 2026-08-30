package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.MoodBad
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WorkspacePremium
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyAffirmation
import com.example.data.model.DailyMoodRecord
import com.example.data.model.RecoveryGoal
import com.example.data.model.RecoveryMilestone
import com.example.data.model.SupportCircleMember
import com.example.ui.theme.BlueAccent
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BluePrimaryLight
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CardBorderHighlight
import com.example.ui.theme.SecondaryCyanLight
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.TertiaryIndigo

// Emerald green accents restricted strictly to recovery progress, milestones, and wellness indicators
val RecoveryEmerald = Color(0xFF10B981)
val RecoveryEmeraldLight = Color(0xFF34D399)
val RecoveryEmeraldDark = Color(0xFF065F46)
val RecoveryEmeraldBg = Color(0xFF064E3B)

/**
 * Illustrated Support Companion Avatar (3D styled friendly girl with brown hair, gold hoops, and waving hand)
 */
@Composable
fun SupportCompanionAvatar(
    size: Dp = 76.dp,
    modifier: Modifier = Modifier,
    isPulsing: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "companion_wave")
    val waveAngle by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hand_wave"
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(0xFFE8F5EE)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height
            val center = Offset(w / 2f, h / 2f)

            // Background soft aura
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFD1FAE5),
                        Color(0xFFE8F5EE),
                        Color.Transparent
                    ),
                    center = center,
                    radius = w * 0.5f
                )
            )

            // Torso / Sage Green Hoodie
            val torsoPath = Path().apply {
                moveTo(w * 0.20f, h * 0.98f)
                quadraticTo(w * 0.25f, h * 0.68f, w * 0.50f, h * 0.68f)
                quadraticTo(w * 0.75f, h * 0.68f, w * 0.80f, h * 0.98f)
                close()
            }
            drawPath(
                path = torsoPath,
                color = Color(0xFF0D4739)
            )

            // Hoodie Collar Detail
            drawCircle(
                color = Color(0xFF1E5B4B),
                center = Offset(w * 0.5f, h * 0.72f),
                radius = w * 0.12f
            )

            // Neck
            drawRect(
                color = Color(0xFFE8B693),
                topLeft = Offset(w * 0.44f, h * 0.52f),
                size = androidx.compose.ui.geometry.Size(w * 0.12f, h * 0.16f)
            )

            // Head / Face Base
            drawCircle(
                color = Color(0xFFF6C9A8),
                center = Offset(w * 0.50f, h * 0.44f),
                radius = w * 0.25f
            )

            // Brown Hair (Back & Top Bun)
            drawCircle(
                color = Color(0xFF3E2723),
                center = Offset(w * 0.50f, h * 0.22f),
                radius = w * 0.24f
            )
            // Left hair strand
            val leftHair = Path().apply {
                moveTo(w * 0.24f, h * 0.35f)
                quadraticTo(w * 0.22f, h * 0.58f, w * 0.28f, h * 0.62f)
                quadraticTo(w * 0.32f, h * 0.48f, w * 0.30f, h * 0.35f)
                close()
            }
            drawPath(leftHair, color = Color(0xFF3E2723))

            // Right hair strand
            val rightHair = Path().apply {
                moveTo(w * 0.76f, h * 0.35f)
                quadraticTo(w * 0.78f, h * 0.58f, w * 0.72f, h * 0.62f)
                quadraticTo(w * 0.68f, h * 0.48f, w * 0.70f, h * 0.35f)
                close()
            }
            drawPath(rightHair, color = Color(0xFF3E2723))

            // Front Bangs
            val bangsPath = Path().apply {
                moveTo(w * 0.28f, h * 0.28f)
                quadraticTo(w * 0.50f, h * 0.35f, w * 0.72f, h * 0.28f)
                quadraticTo(w * 0.50f, h * 0.22f, w * 0.28f, h * 0.28f)
                close()
            }
            drawPath(bangsPath, color = Color(0xFF4E342E))

            // Gold Hoop Earrings
            drawCircle(
                color = Color(0xFFF59E0B),
                center = Offset(w * 0.25f, h * 0.48f),
                radius = w * 0.04f,
                style = Stroke(width = 2.5f)
            )
            drawCircle(
                color = Color(0xFFF59E0B),
                center = Offset(w * 0.75f, h * 0.48f),
                radius = w * 0.04f,
                style = Stroke(width = 2.5f)
            )

            // Sparkling Dark Eyes
            val eyeOffsetY = h * 0.43f
            val eyeSpacing = w * 0.10f
            val eyeRadius = w * 0.038f

            // Left Eye
            drawCircle(color = Color(0xFF1E293B), center = Offset(center.x - eyeSpacing, eyeOffsetY), radius = eyeRadius)
            drawCircle(color = Color.White, center = Offset(center.x - eyeSpacing + 1.2f, eyeOffsetY - 1.2f), radius = eyeRadius * 0.4f)

            // Right Eye
            drawCircle(color = Color(0xFF1E293B), center = Offset(center.x + eyeSpacing, eyeOffsetY), radius = eyeRadius)
            drawCircle(color = Color.White, center = Offset(center.x + eyeSpacing + 1.2f, eyeOffsetY - 1.2f), radius = eyeRadius * 0.4f)

            // Rosy Blushing Cheeks
            drawCircle(color = Color(0x55EF4444), center = Offset(center.x - eyeSpacing * 1.3f, h * 0.49f), radius = w * 0.045f)
            drawCircle(color = Color(0x55EF4444), center = Offset(center.x + eyeSpacing * 1.3f, h * 0.49f), radius = w * 0.045f)

            // Warm Smile
            val smilePath = Path().apply {
                moveTo(center.x - w * 0.07f, h * 0.52f)
                quadraticTo(center.x, h * 0.58f, center.x + w * 0.07f, h * 0.52f)
            }
            drawPath(path = smilePath, color = Color(0xFFB91C1C), style = Stroke(width = 2.4f, cap = StrokeCap.Round))

            // Waving Hand on right side
            val handCenter = Offset(w * 0.85f, h * 0.42f)
            drawCircle(
                color = Color(0xFFF6C9A8),
                center = handCenter,
                radius = w * 0.09f
            )
            // Waving fingers
            drawCircle(
                color = Color(0xFFF6C9A8),
                center = Offset(handCenter.x - 3f, handCenter.y - 6f),
                radius = w * 0.04f
            )
            drawCircle(
                color = Color(0xFFF6C9A8),
                center = Offset(handCenter.x + 3f, handCenter.y - 6f),
                radius = w * 0.04f
            )
        }
    }
}

/**
 * 1. Primary Welcome Companion Hero Card (Matching SAYANAK Screenshot)
 * "Welcome to SAYANAK" • "A safe space for healing, support, and hope." • Talk to Sayanak CTA
 */
@Composable
fun CompanionWelcomeHeroCard(
    onStartAnonymousChat: () -> Unit,
    onEmergencySos: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("companion_welcome_hero_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF5EE)),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFFD4E8DC))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFEAF5EE),
                            Color(0xFFDFF0E6)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Column: Text + Talk to Sayanak Button
                Column(
                    modifier = Modifier
                        .weight(1.2f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Welcome to",
                        color = Color(0xFF2D6A4F),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "SAYANAK",
                        color = Color(0xFF0D4739),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "A safe space for healing,\nsupport, and hope.",
                        color = Color(0xFF1B4332),
                        fontSize = 13.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onStartAnonymousChat,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0D4739),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .height(42.dp)
                            .testTag("welcome_chat_button"),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Talk to Sayanak",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Right Column: Companion Girl + Speech Bubble
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Speech Bubble: "I'm here to listen."
                    Surface(
                        color = Color(0xFF0D4739),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp, bottomStart = 2.dp),
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Text(
                            text = "I'm here to listen.",
                            color = Color.White,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // Waving Girl Character
                    SupportCompanionAvatar(
                        size = 80.dp,
                        isPulsing = true
                    )
                }
            }
        }
    }
}

/**
 * 2. Trust Indicator Badges
 * 100% Anonymous • End-to-End Secure • No Judgment Zone • Verified Resources • Crisis Support Available
 */
@Composable
fun TrustIndicatorBadgesRow(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TrustBadgeItem(icon = Icons.Default.CheckCircle, label = "100% Anonymous", accent = Color(0xFF10B981))
        TrustBadgeItem(icon = Icons.Default.Lock, label = "End-to-End Secure", accent = Color(0xFF0D4739))
        TrustBadgeItem(icon = Icons.Default.Favorite, label = "No Judgment Zone", accent = Color(0xFFEF4444))
        TrustBadgeItem(icon = Icons.Default.Verified, label = "Verified Resources", accent = Color(0xFF10B981))
        TrustBadgeItem(icon = Icons.Default.HeadsetMic, label = "24/7 Crisis Support", accent = Color(0xFFF59E0B))
    }
}

@Composable
private fun TrustBadgeItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    accent: Color
) {
    Surface(
        color = Color(0xFFF4F9F5),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDDECE2))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = accent, modifier = Modifier.size(13.dp))
            Text(text = label, color = Color(0xFF0F241D), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

/**
 * Illustrated Support Circle Member Avatar (Custom illustrated vector character per role)
 */
@Composable
fun SupportCircleMemberAvatar(
    role: String,
    size: Dp = 50.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                when (role) {
                    "Self" -> Color(0xFFE8F5EE)
                    "Mother" -> Color(0xFFFEF2F2)
                    "Father" -> Color(0xFFEFF6FF)
                    "Sibling" -> Color(0xFFFFFBEB)
                    "Friend" -> Color(0xFFFAF5FF)
                    "Teacher" -> Color(0xFFF0FDF4)
                    else -> Color(0xFFE8F5EE)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height
            val center = Offset(w / 2f, h / 2f)

            when (role) {
                "Self" -> {
                    // Young smiling boy with dark wavy hair and green hoodie
                    // Torso
                    val torso = Path().apply {
                        moveTo(w * 0.20f, h * 0.98f)
                        quadraticTo(w * 0.25f, h * 0.70f, w * 0.50f, h * 0.70f)
                        quadraticTo(w * 0.75f, h * 0.70f, w * 0.80f, h * 0.98f)
                        close()
                    }
                    drawPath(torso, color = Color(0xFF0D4739))
                    // Face
                    drawCircle(color = Color(0xFFF6C9A8), center = Offset(center.x, h * 0.44f), radius = w * 0.26f)
                    // Hair
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x, h * 0.26f), radius = w * 0.24f)
                    // Eyes
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x - w * 0.09f, h * 0.43f), radius = w * 0.035f)
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x + w * 0.09f, h * 0.43f), radius = w * 0.035f)
                    // Smile
                    val smile = Path().apply {
                        moveTo(center.x - w * 0.07f, h * 0.53f)
                        quadraticTo(center.x, h * 0.60f, center.x + w * 0.07f, h * 0.53f)
                    }
                    drawPath(smile, color = Color(0xFF0D4739), style = Stroke(width = 2.2f, cap = StrokeCap.Round))
                }
                "Mother" -> {
                    // Loving Indian mother with dark hair, teal saree, gold necklace, and red bindi
                    val torso = Path().apply {
                        moveTo(w * 0.18f, h * 0.98f)
                        quadraticTo(w * 0.25f, h * 0.68f, w * 0.50f, h * 0.68f)
                        quadraticTo(w * 0.75f, h * 0.68f, w * 0.82f, h * 0.98f)
                        close()
                    }
                    drawPath(torso, color = Color(0xFF005C4B))
                    // Saree Pallu
                    drawLine(color = Color(0xFFF59E0B), start = Offset(w * 0.25f, h * 0.98f), end = Offset(w * 0.60f, h * 0.70f), strokeWidth = 3f)
                    // Face
                    drawCircle(color = Color(0xFFF6C9A8), center = Offset(center.x, h * 0.44f), radius = w * 0.26f)
                    // Hair & Bun
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x, h * 0.24f), radius = w * 0.24f)
                    // Red Bindi
                    drawCircle(color = Color(0xFFEF4444), center = Offset(center.x, h * 0.36f), radius = w * 0.026f)
                    // Eyes
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x - w * 0.09f, h * 0.44f), radius = w * 0.035f)
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x + w * 0.09f, h * 0.44f), radius = w * 0.035f)
                    // Warm smile
                    val smile = Path().apply {
                        moveTo(center.x - w * 0.07f, h * 0.54f)
                        quadraticTo(center.x, h * 0.61f, center.x + w * 0.07f, h * 0.54f)
                    }
                    drawPath(smile, color = Color(0xFFEF4444), style = Stroke(width = 2.2f, cap = StrokeCap.Round))
                }
                "Father" -> {
                    // Smiling father with glasses, mustache, in light green/olive polo
                    val torso = Path().apply {
                        moveTo(w * 0.18f, h * 0.98f)
                        quadraticTo(w * 0.25f, h * 0.68f, w * 0.50f, h * 0.68f)
                        quadraticTo(w * 0.75f, h * 0.68f, w * 0.82f, h * 0.98f)
                        close()
                    }
                    drawPath(torso, color = Color(0xFF2D6A4F))
                    // Face
                    drawCircle(color = Color(0xFFF6C9A8), center = Offset(center.x, h * 0.44f), radius = w * 0.26f)
                    // Short dark hair
                    drawCircle(color = Color(0xFF334155), center = Offset(center.x, h * 0.24f), radius = w * 0.23f)
                    // Glasses (Left & Right circles + bridge)
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x - w * 0.09f, h * 0.42f), radius = w * 0.055f, style = Stroke(width = 2f))
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x + w * 0.09f, h * 0.42f), radius = w * 0.055f, style = Stroke(width = 2f))
                    drawLine(color = Color(0xFF1E293B), start = Offset(center.x - w * 0.04f, h * 0.42f), end = Offset(center.x + w * 0.04f, h * 0.42f), strokeWidth = 2f)
                    // Eyes
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x - w * 0.09f, h * 0.42f), radius = w * 0.025f)
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x + w * 0.09f, h * 0.42f), radius = w * 0.025f)
                    // Mustache
                    val mustache = Path().apply {
                        moveTo(center.x - w * 0.08f, h * 0.52f)
                        quadraticTo(center.x, h * 0.50f, center.x + w * 0.08f, h * 0.52f)
                    }
                    drawPath(mustache, color = Color(0xFF334155), style = Stroke(width = 2.4f, cap = StrokeCap.Round))
                }
                "Sibling" -> {
                    // Cheerful girl with side ponytail in bright yellow top
                    val torso = Path().apply {
                        moveTo(w * 0.20f, h * 0.98f)
                        quadraticTo(w * 0.25f, h * 0.70f, w * 0.50f, h * 0.70f)
                        quadraticTo(w * 0.75f, h * 0.70f, w * 0.80f, h * 0.98f)
                        close()
                    }
                    drawPath(torso, color = Color(0xFFF59E0B))
                    // Face
                    drawCircle(color = Color(0xFFF6C9A8), center = Offset(center.x, h * 0.44f), radius = w * 0.26f)
                    // Hair with high side ponytail
                    drawCircle(color = Color(0xFF451A03), center = Offset(center.x, h * 0.24f), radius = w * 0.23f)
                    drawCircle(color = Color(0xFF451A03), center = Offset(w * 0.80f, h * 0.22f), radius = w * 0.12f)
                    // Eyes & Smile
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x - w * 0.09f, h * 0.43f), radius = w * 0.035f)
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x + w * 0.09f, h * 0.43f), radius = w * 0.035f)
                    val smile = Path().apply {
                        moveTo(center.x - w * 0.07f, h * 0.53f)
                        quadraticTo(center.x, h * 0.60f, center.x + w * 0.07f, h * 0.53f)
                    }
                    drawPath(smile, color = Color(0xFFEA580C), style = Stroke(width = 2.2f, cap = StrokeCap.Round))
                }
                "Friend" -> {
                    // Teen girl with long parted hair in soft purple hoodie
                    val torso = Path().apply {
                        moveTo(w * 0.20f, h * 0.98f)
                        quadraticTo(w * 0.25f, h * 0.70f, w * 0.50f, h * 0.70f)
                        quadraticTo(w * 0.75f, h * 0.70f, w * 0.80f, h * 0.98f)
                        close()
                    }
                    drawPath(torso, color = Color(0xFF8B5CF6))
                    // Face
                    drawCircle(color = Color(0xFFF6C9A8), center = Offset(center.x, h * 0.44f), radius = w * 0.26f)
                    // Long brown hair
                    drawCircle(color = Color(0xFF3B1D11), center = Offset(center.x, h * 0.23f), radius = w * 0.24f)
                    drawRect(color = Color(0xFF3B1D11), topLeft = Offset(w * 0.20f, h * 0.35f), size = androidx.compose.ui.geometry.Size(w * 0.12f, h * 0.30f))
                    drawRect(color = Color(0xFF3B1D11), topLeft = Offset(w * 0.68f, h * 0.35f), size = androidx.compose.ui.geometry.Size(w * 0.12f, h * 0.30f))
                    // Eyes & Smile
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x - w * 0.09f, h * 0.43f), radius = w * 0.035f)
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x + w * 0.09f, h * 0.43f), radius = w * 0.035f)
                    val smile = Path().apply {
                        moveTo(center.x - w * 0.07f, h * 0.53f)
                        quadraticTo(center.x, h * 0.60f, center.x + w * 0.07f, h * 0.53f)
                    }
                    drawPath(smile, color = Color(0xFF8B5CF6), style = Stroke(width = 2.2f, cap = StrokeCap.Round))
                }
                else -> {
                    // Teacher / Mentor with glasses & green saree
                    val torso = Path().apply {
                        moveTo(w * 0.18f, h * 0.98f)
                        quadraticTo(w * 0.25f, h * 0.68f, w * 0.50f, h * 0.68f)
                        quadraticTo(w * 0.75f, h * 0.68f, w * 0.82f, h * 0.98f)
                        close()
                    }
                    drawPath(torso, color = Color(0xFF047857))
                    // Face
                    drawCircle(color = Color(0xFFF6C9A8), center = Offset(center.x, h * 0.44f), radius = w * 0.26f)
                    // Neat hair bun
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x, h * 0.23f), radius = w * 0.23f)
                    // Glasses
                    drawCircle(color = Color(0xFF047857), center = Offset(center.x - w * 0.09f, h * 0.42f), radius = w * 0.052f, style = Stroke(width = 1.8f))
                    drawCircle(color = Color(0xFF047857), center = Offset(center.x + w * 0.09f, h * 0.42f), radius = w * 0.052f, style = Stroke(width = 1.8f))
                    drawLine(color = Color(0xFF047857), start = Offset(center.x - w * 0.04f, h * 0.42f), end = Offset(center.x + w * 0.04f, h * 0.42f), strokeWidth = 1.8f)
                    // Eyes
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x - w * 0.09f, h * 0.42f), radius = w * 0.025f)
                    drawCircle(color = Color(0xFF1E293B), center = Offset(center.x + w * 0.09f, h * 0.42f), radius = w * 0.025f)
                    // Smile
                    val smile = Path().apply {
                        moveTo(center.x - w * 0.07f, h * 0.53f)
                        quadraticTo(center.x, h * 0.60f, center.x + w * 0.07f, h * 0.53f)
                    }
                    drawPath(smile, color = Color(0xFF047857), style = Stroke(width = 2.2f, cap = StrokeCap.Round))
                }
            }
        }
    }
}

/**
 * 3. Support Circle Cards Section (Matching exact Screenshot)
 * Self, Mother, Father, Sibling, Friend, Teacher
 */
@Composable
fun SupportCircleSection(
    members: List<SupportCircleMember>,
    onEditMember: (SupportCircleMember) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMemberForEdit by remember { mutableStateOf<SupportCircleMember?>(null) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8F5EE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = null,
                        tint = Color(0xFF0D4739),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Your Support Circle",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F241D),
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "💚", fontSize = 14.sp)
                    }
                    Text(
                        text = "The people who care. The people who support.",
                        color = Color(0xFF52796F),
                        fontSize = 11.sp
                    )
                }
            }

            Surface(
                color = Color(0xFFEAF5EE),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCCE4D5)),
                modifier = Modifier.clickable {
                    if (members.isNotEmpty()) {
                        selectedMemberForEdit = members.first()
                    }
                }
            ) {
                Text(
                    text = "Manage >",
                    color = Color(0xFF0D4739),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            members.forEach { member ->
                SupportCircleCard(
                    member = member,
                    onClick = { selectedMemberForEdit = member }
                )
            }
        }
    }

    if (selectedMemberForEdit != null) {
        SupportCircleEditDialog(
            member = selectedMemberForEdit!!,
            onDismiss = { selectedMemberForEdit = null },
            onSave = { updated ->
                onEditMember(updated)
                selectedMemberForEdit = null
            }
        )
    }
}

@Composable
fun SupportCircleCard(
    member: SupportCircleMember,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(142.dp)
            .clickable { onClick() }
            .testTag("support_card_${member.role.lowercase()}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFFE2EDE6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Illustrated Custom Character Avatar
            SupportCircleMemberAvatar(
                role = member.role,
                size = 52.dp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Role Title (Self, Mother, Father, Sibling, Friend, Teacher)
            Text(
                text = member.role,
                color = Color(0xFF0F241D),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle quote
            Text(
                text = if (member.subtitle.isNotBlank()) member.subtitle else member.relation,
                color = Color(0xFF52796F),
                fontSize = 10.5.sp,
                lineHeight = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                maxLines = 2,
                minLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Outline Heart Icon with role's accent color
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color(member.heartColorHex).copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Heart",
                    tint = Color(member.heartColorHex),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun SupportCircleEditDialog(
    member: SupportCircleMember,
    onDismiss: () -> Unit,
    onSave: (SupportCircleMember) -> Unit
) {
    var name by remember { mutableStateOf(member.name) }
    var phone by remember { mutableStateOf(member.phone) }
    var isEmergency by remember { mutableStateOf(member.isEmergencyContact) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Slate900,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = BluePrimaryLight)
                Text(text = "Edit Support Contact (${member.role})", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "This contact stays 100% on your local device for rapid safety check-in.", color = Slate400, fontSize = 11.5.sp)

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Contact Name / Alias") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimaryLight,
                        unfocusedBorderColor = Slate700,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number / Helpline") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimaryLight,
                        unfocusedBorderColor = Slate700,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isEmergency = !isEmergency }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    androidx.compose.material3.Checkbox(
                        checked = isEmergency,
                        onCheckedChange = { isEmergency = it }
                    )
                    Text(text = "Mark as Rapid Emergency SOS Contact", color = Slate200, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(member.copy(name = name, phone = phone, isEmergencyContact = isEmergency))
                },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Text("Save Contact")
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text("Cancel", color = Slate400)
            }
        }
    )
}

/**
 * 4. Mood Tracking Widget
 * Daily mood selection • Weekly trend graph • Emotional wellbeing score • Journal note option
 */
@Composable
fun MoodTrackingWidget(
    currentMood: String,
    wellbeingScore: Int,
    moodHistory: List<DailyMoodRecord>,
    journalNote: String,
    onRecordMood: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var noteText by remember { mutableStateOf(journalNote) }
    var showJournalInput by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("mood_tracking_widget"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.Mood, contentDescription = null, tint = SecondaryCyanLight, modifier = Modifier.size(18.dp))
                    Text(
                        text = "Daily Mood & Wellbeing",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Emotional wellbeing score badge
                Surface(
                    color = BluePrimary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BluePrimaryLight.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Score:", color = Slate400, fontSize = 10.sp)
                        Text(text = "$wellbeingScore/100", color = SecondaryCyanLight, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mood Selector Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MoodItemButton(label = "Joyful", icon = Icons.Default.SentimentVerySatisfied, isSelected = currentMood == "JOYFUL", accent = Color(0xFFFBBF24)) {
                    onRecordMood("JOYFUL", noteText)
                }
                MoodItemButton(label = "Calm", icon = Icons.Default.SentimentSatisfied, isSelected = currentMood == "CALM", accent = BluePrimaryLight) {
                    onRecordMood("CALM", noteText)
                }
                MoodItemButton(label = "Neutral", icon = Icons.Default.SentimentDissatisfied, isSelected = currentMood == "NEUTRAL", accent = Slate300) {
                    onRecordMood("NEUTRAL", noteText)
                }
                MoodItemButton(label = "Anxious", icon = Icons.Default.MoodBad, isSelected = currentMood == "ANXIOUS", accent = Color(0xFFF59E0B)) {
                    onRecordMood("ANXIOUS", noteText)
                }
                MoodItemButton(label = "Distressed", icon = Icons.Default.SentimentVeryDissatisfied, isSelected = currentMood == "DISTRESSED", accent = Color(0xFFEF4444)) {
                    onRecordMood("DISTRESSED", noteText)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Weekly Trend Bar Graph
            Text(text = "7-Day Emotional Trend", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))

            WeeklyMoodCanvasGraph(records = moodHistory, modifier = Modifier.fillMaxWidth().height(60.dp))

            Spacer(modifier = Modifier.height(10.dp))

            // Journal Note Expandable Option
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showJournalInput = !showJournalInput }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showJournalInput) "Hide Private Journal Note" else "＋ Add Today's Private Journal Note",
                    color = BluePrimaryLight,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = BluePrimaryLight,
                    modifier = Modifier.size(13.dp)
                )
            }

            AnimatedVisibility(visible = showJournalInput) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        placeholder = { Text("What made you feel this way today? (Saved privately on device)", fontSize = 11.5.sp, color = Slate500) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BluePrimaryLight,
                            unfocusedBorderColor = Slate700,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = { onRecordMood(currentMood, noteText) },
                        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.align(Alignment.End).height(34.dp)
                    ) {
                        Text("Save Note", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun MoodItemButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    accent: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (isSelected) accent.copy(alpha = 0.25f) else Slate850)
                .border(
                    width = if (isSelected) 1.8.dp else 0.8.dp,
                    color = if (isSelected) accent else Slate700,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) accent else Slate400,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (isSelected) Color.White else Slate400,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun WeeklyMoodCanvasGraph(
    records: List<DailyMoodRecord>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Slate850, RoundedCornerShape(10.dp))
            .border(1.dp, CardBorder, RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            if (records.isEmpty()) return@Canvas

            val stepX = w / records.size
            val maxScore = 10f

            // Draw baseline guideline
            drawLine(
                color = Slate700,
                start = Offset(0f, h * 0.5f),
                end = Offset(w, h * 0.5f),
                strokeWidth = 1f
            )

            records.forEachIndexed { i, record ->
                val barWidth = stepX * 0.45f
                val barHeight = (record.score / maxScore) * (h * 0.75f)
                val left = (i * stepX) + (stepX - barWidth) / 2f
                val top = h - barHeight - 4f

                val barColor = when (record.moodType) {
                    "JOYFUL" -> Color(0xFFFBBF24)
                    "CALM" -> BluePrimaryLight
                    "ANXIOUS" -> Color(0xFFF59E0B)
                    "DISTRESSED" -> Color(0xFFEF4444)
                    else -> SecondaryCyanLight
                }

                drawRoundRect(
                    color = barColor,
                    topLeft = Offset(left, top),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(4f, 4f)
                )
            }
        }
    }
}

/**
 * 5. Recovery Progress Tracker
 * Current streak • Weekly goals • Wellness score • Milestone achievements
 * Green accents used strictly for positive recovery progress & milestones!
 */
@Composable
fun RecoveryProgressTrackerCard(
    streakDays: Int,
    wellnessScore: Int,
    goals: List<RecoveryGoal>,
    milestones: List<RecoveryMilestone>,
    onIncrementStreak: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("recovery_progress_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with Green Accent Milestone Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(
                        imageVector = Icons.Default.TrackChanges,
                        contentDescription = null,
                        tint = RecoveryEmeraldLight,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Recovery & Wellness Progress",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Green Accent Streak Pill
                Surface(
                    color = RecoveryEmeraldBg,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RecoveryEmerald.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = RecoveryEmeraldLight, modifier = Modifier.size(11.dp))
                        Text(text = "$streakDays Day Streak", color = RecoveryEmeraldLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3-Column Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Streak Box
                Surface(
                    modifier = Modifier.weight(1f),
                    color = Slate850,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$streakDays", color = RecoveryEmeraldLight, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Days Sober / Calm", color = Slate400, fontSize = 10.sp, textAlign = TextAlign.Center)
                    }
                }

                // Weekly Goal Progress Box
                Surface(
                    modifier = Modifier.weight(1f),
                    color = Slate850,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "4 / 4", color = BluePrimaryLight, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Weekly Goals Met", color = Slate400, fontSize = 10.sp, textAlign = TextAlign.Center)
                    }
                }

                // Wellness Score Box
                Surface(
                    modifier = Modifier.weight(1f),
                    color = Slate850,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$wellnessScore%", color = SecondaryCyanLight, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Resilience Index", color = Slate400, fontSize = 10.sp, textAlign = TextAlign.Center)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Milestone Achievements Scroller
            Text(text = "Milestones & Achievements", color = Slate300, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                milestones.forEach { milestone ->
                    Surface(
                        color = if (milestone.isUnlocked) RecoveryEmeraldBg else Slate850,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (milestone.isUnlocked) RecoveryEmerald.copy(alpha = 0.6f) else CardBorder
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (milestone.isUnlocked) Icons.Default.WorkspacePremium else Icons.Default.Lock,
                                contentDescription = null,
                                tint = if (milestone.isUnlocked) RecoveryEmeraldLight else Slate500,
                                modifier = Modifier.size(15.dp)
                            )
                            Column {
                                Text(
                                    text = milestone.title,
                                    color = if (milestone.isUnlocked) Color.White else Slate400,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (milestone.isUnlocked) (milestone.unlockedDate ?: "Achieved") else "Locked",
                                    color = if (milestone.isUnlocked) RecoveryEmeraldLight else Slate500,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 6. Daily Wellness Check-in Card
 * "How are you feeling today?" • One-tap emotional check-in • Personalized suggestions
 */
@Composable
fun DailyWellnessCheckinCard(
    selectedFeeling: String?,
    suggestion: String?,
    onSelectFeeling: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("daily_wellness_checkin_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(imageVector = Icons.Default.EmojiEmotions, contentDescription = null, tint = SecondaryCyanLight, modifier = Modifier.size(18.dp))
                Text(
                    text = "Daily Wellness Check-in",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "How are you feeling today?", color = Slate300, fontSize = 13.sp)

            Spacer(modifier = Modifier.height(10.dp))

            // Feelings Pills Row
            val feelings = listOf("Great", "Good", "Okay", "Anxious", "Overwhelmed")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                feelings.forEach { f ->
                    val isSel = selectedFeeling == f
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onSelectFeeling(f) },
                        color = if (isSel) BluePrimary else Slate850,
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSel) BluePrimaryLight else CardBorder)
                    ) {
                        Text(
                            text = f,
                            color = if (isSel) Color.White else Slate300,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            if (suggestion != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = Slate850,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BluePrimary.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = BluePrimaryLight, modifier = Modifier.size(15.dp))
                        Column {
                            Text(text = "Tailored Wellness Suggestion", color = BluePrimaryLight, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = suggestion, color = Slate200, fontSize = 11.5.sp, lineHeight = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 7. Positive Daily Affirmation Card
 */
@Composable
fun PositiveAffirmationCard(
    affirmation: DailyAffirmation,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("positive_affirmation_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(16.dp))
                    Text(text = "DAILY AFFIRMATION", color = Color(0xFFFDE68A), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.6.sp)
                }

                IconButton(onClick = onRefresh, modifier = Modifier.size(24.dp)) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Next affirmation", tint = Slate400, modifier = Modifier.size(15.dp))
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "“${affirmation.quote}”",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 13.5.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "— Theme: ${affirmation.theme}",
                color = Slate400,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * 3-Step Visual Infographic for regular people to immediately understand how Sahayak works
 */
@Composable
fun SimpleHowItWorksVisual(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderHighlight)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "How Sahayak Protects You",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "3 Simple Steps",
                    color = BluePrimaryLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StepVisualItem(
                    stepNumber = "1",
                    title = "Answer Safely",
                    desc = "No name, phone, or photos required.",
                    icon = Icons.Default.Lock,
                    iconTint = BluePrimaryLight,
                    modifier = Modifier.weight(1f)
                )

                StepVisualItem(
                    stepNumber = "2",
                    title = "Instant Triage",
                    desc = "Smart evaluation of care tier level.",
                    icon = Icons.Default.Psychology,
                    iconTint = SecondaryCyanLight,
                    modifier = Modifier.weight(1f)
                )

                StepVisualItem(
                    stepNumber = "3",
                    title = "Get Support",
                    desc = "Direct link to verified centres.",
                    icon = Icons.Default.HeadsetMic,
                    iconTint = TertiaryIndigo,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StepVisualItem(
    stepNumber: String,
    title: String,
    desc: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Slate850,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.15f))
                    .border(1.dp, iconTint.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$stepNumber. $title",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = desc,
                color = Slate400,
                fontSize = 9.5.sp,
                lineHeight = 12.5.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

/**
 * Interactive AI Companion Assistant Dialog
 */
@Composable
fun AiAssistantCompanionDialog(
    advice: String,
    onAsk: (String) -> Unit,
    onDismiss: () -> Unit,
    onStartIntake: () -> Unit,
    onStartChat: () -> Unit,
    onEmergencySos: () -> Unit,
    modifier: Modifier = Modifier
) {
    var userQuery by remember { mutableStateOf("") }
    val quickQuestions = listOf(
        "I'm feeling very overwhelmed",
        "How is my anonymity protected?",
        "Addiction urge support steps",
        "Creating a safe crisis plan",
        "How do I track my case status?"
    )

    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .testTag("ai_assistant_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SupportCompanionAvatar(size = 38.dp, isPulsing = true)
                        Column {
                            Text(
                                text = "Sahayak AI Companion",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Confidential Guidance & Triage",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp).testTag("close_ai_assistant_dialog")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // AI Response Box
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 110.dp, max = 220.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = advice,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.5.sp,
                            lineHeight = 19.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Quick Prompt Chips
                Text(
                    text = "Quick Support Topics",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickQuestions.forEach { question ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier.clickable {
                                onAsk(question)
                            }
                        ) {
                            Text(
                                text = question,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Custom Query Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    androidx.compose.material3.OutlinedTextField(
                        value = userQuery,
                        onValueChange = { userQuery = it },
                        placeholder = {
                            Text("Ask guidance or describe feelings...", fontSize = 12.sp)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("ai_assistant_query_input"),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )

                    Button(
                        onClick = {
                            if (userQuery.isNotBlank()) {
                                onAsk(userQuery)
                                userQuery = ""
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.height(52.dp).testTag("ai_assistant_send_button"),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp)
                    ) {
                        Text("Ask", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Fast Action Shortcuts
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onDismiss()
                            onStartIntake()
                        },
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Intake", fontSize = 11.sp)
                    }
                    Button(
                        onClick = {
                            onDismiss()
                            onStartChat()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Live Chat", fontSize = 11.sp)
                    }
                    Button(
                        onClick = {
                            onDismiss()
                            onEmergencySos()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF4444)
                        ),
                        modifier = Modifier.weight(0.9f).height(38.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("SOS", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

