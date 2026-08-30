package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ==========================================
// Dark Green Mental Wellness Theme Palette
// ==========================================

val SayanakForestDark = Color(0xFF0D4739) // Deep Forest Teal Green #0D4739
val SayanakForestMedium = Color(0xFF134E4A) // Dark Teal #134E4A
val SayanakTeal = Color(0xFF005C4B) // Brand Teal
val SayanakMint = Color(0xFF10B981) // Mint Green #10B981
val SayanakSageLight = Color(0xFFEAF5EE) // Soft Sage Mint
val SayanakSageBorder = Color(0xFFD2E8DA) // Subtle Mint Border
val SayanakHeroGradientStart = Color(0xFF13362E)
val SayanakHeroGradientEnd = Color(0xFF0D2821)

// Dark Green Mental Wellness Card Theme
val CardDarkGreen = Color(0xFF1F3A34) // Standard unselected card background #1F3A34
val CardDarkGreenElevated = Color(0xFF23463E) // Elevated card background #23463E
val CardDarkGreenBorder = Color(0xFF2A5249) // Crisp card border #2A5249
val CardDarkGreenBorderSelected = Color(0xFF3BB273) // Active border #3BB273
val AccentGreen = Color(0xFF3BB273) // Vibrant Accent Green #3BB273
val SelectedCardGradientStart = Color(0xFF0F6A4A) // Selected card gradient start #0F6A4A
val SelectedCardGradientEnd = Color(0xFF1B8F63) // Selected card gradient end #1B8F63

val SelectedCardBrush = Brush.horizontalGradient(
    colors = listOf(SelectedCardGradientStart, SelectedCardGradientEnd)
)

val TextPrimaryDarkCard = Color(0xFFFFFFFF) // Primary Text #FFFFFF on dark cards
val TextPrimaryNearWhite = Color(0xFFF5F7F6) // #F5F7F6
val TextSecondaryDarkCard = Color(0xFFD0D8D4) // Secondary Text #D0D8D4

// Primary compatibility aliases
val BluePrimary = SayanakForestDark
val BluePrimaryLight = SayanakMint
val BlueAccent = Color(0xFF6EE7B7)
val BlueDark = Color(0xFF064E3B)
val BlueContainerDark = Color(0xFF134E4A)
val BlueOnContainer = Color(0xFFECFDF5)

val TealPrimary = AccentGreen
val TealPrimaryLight = SayanakMint
val TealAccent = Color(0xFF34D399)
val TealDark = Color(0xFF064E3B)
val TealContainerDark = Color(0xFF134E4A)
val TealOnContainer = Color(0xFFECFDF5)

// Secondary & Accent Colors for Cards & Badges
val SecondaryCyan = Color(0xFF0284C7)
val SecondaryCyanLight = Color(0xFF38BDF8)
val SecondaryContainer = Color(0xFF163E36)

val AccentRose = Color(0xFFEF4444) // Mother / Heart Red
val AccentBlue = Color(0xFF3B82F6) // Father / Blue
val AccentYellow = Color(0xFFF59E0B) // Sibling / Gold
val AccentPurple = Color(0xFF8B5CF6) // Friend / Purple
val AccentTeal = Color(0xFF0D9488) // Teacher / Teal

val TertiaryIndigo = Color(0xFF4F46E5)
val TertiaryViolet = Color(0xFF7C3AED)

// Clean Healthcare & Sayanak Dark Neutral Backgrounds
val SayanakBackground = Color(0xFF0B1B16) // Deep Forest Canvas
val SayanakCardBg = Color(0xFF1F3A34) // Dark Green Card #1F3A34
val SayanakCardBorder = Color(0xFF2A5249) // Card Border #2A5249
val SayanakTextDark = Color(0xFFF5F7F6) // High contrast near-white text
val SayanakTextMuted = Color(0xFFD0D8D4) // High contrast secondary text
val SayanakTrustBadgeBg = Color(0xFF15332B) // Dark Green Trust Strip

// Slate Scale for UI
val Slate950 = Color(0xFF091713) // Dark mode base
val Slate900 = Color(0xFF0F241E)
val Slate850 = Color(0xFF16322A)
val Slate800 = Color(0xFF1F3A34)
val Slate700 = Color(0xFF2A5249)
val Slate600 = Color(0xFF3D6B60)
val Slate500 = Color(0xFF5A8E82)
val Slate400 = Color(0xFF9ABCB3)
val Slate300 = Color(0xFFD0D8D4)
val Slate200 = Color(0xFFE5EDE8)
val Slate100 = Color(0xFFF1F7F4)
val Slate50 = Color(0xFFF8FAF9)

// Severity & Alert Colors
val SeveritySelfHelp = Color(0xFF10B981)
val SeverityCounselling = Color(0xFF0284C7)
val SeverityRehab = Color(0xFFF59E0B)
val SeverityUrgentSos = Color(0xFFEF4444)
val UrgentSosBackground = Color(0xFF3B1212)
val UrgentSosGlow = Color(0x66EF4444)

// Card & Component Surfaces (No pure white #FFFFFF cards)
val CardBackground = Color(0xFF1F3A34)
val CardBackgroundSecondary = Color(0xFF23463E)
val CardBorder = Color(0xFF2A5249)
val CardBorderHighlight = Color(0xFF3BB273)
val InputBackground = Color(0xFF142E28)

// Gradient Brushes
val SayanakHeroBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFEAF5EE),
        Color(0xFFE0F2E8),
        Color(0xFFD8EDE1)
    )
)

val SayanakDarkHeroBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFF0F2B23),
        Color(0xFF15382E),
        Color(0xFF0C1F19)
    )
)

val EmeraldCyanGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF0D4739), Color(0xFF10B981))
)

val BlueCobaltGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF0D4739), Color(0xFF0284C7))
)

val CyanIndigoGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF0284C7), Color(0xFF4F46E5))
)

val IndigoPurpleGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF4F46E5), Color(0xFF7C3AED))
)

val AmberOrangeGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFF59E0B), Color(0xFFEA580C))
)

val UrgentSosGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFEF4444), Color(0xFFDC2626))
)

val RecoveryEmeraldLight = Color(0xFF10B981)
val RecoveryEmeraldDark = Color(0xFF065F46)




