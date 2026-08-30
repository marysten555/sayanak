package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = AccentGreen,
    onPrimary = Color(0xFF042F24),
    primaryContainer = CardDarkGreenElevated,
    onPrimaryContainer = Color(0xFFECFDF5),
    secondary = Color(0xFF38BDF8),
    onSecondary = Color(0xFF082F49),
    secondaryContainer = Color(0xFF163E36),
    onSecondaryContainer = Color(0xFFE0F2FE),
    tertiary = Color(0xFFA78BFA),
    onTertiary = Color.White,
    background = Color(0xFF091713),
    onBackground = Color(0xFFF5F7F6),
    surface = CardDarkGreen, // #1F3A34
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = CardDarkGreenElevated, // #23463E
    onSurfaceVariant = Color(0xFFD0D8D4),
    outline = CardDarkGreenBorder, // #2A5249
    outlineVariant = Color(0xFF173B31),
    error = SeverityUrgentSos,
    onError = Color.White,
    errorContainer = Color(0xFF450A0A),
    onErrorContainer = Color(0xFFFECDD3)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = AccentGreen,
    onPrimary = Color(0xFF042F24),
    primaryContainer = CardDarkGreenElevated,
    onPrimaryContainer = Color(0xFFECFDF5),
    secondary = SayanakMint,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF163E36),
    onSecondaryContainer = Color(0xFFECFDF5),
    tertiary = Color(0xFF8B5CF6),
    onTertiary = Color.White,
    background = Color(0xFF0B1B16), // Deep Forest Canvas
    onBackground = Color(0xFFF5F7F6), // Near-white text
    surface = CardDarkGreen, // Dark Green Card #1F3A34 (No pure white #FFFFFF)
    onSurface = Color(0xFFFFFFFF), // High contrast white text
    surfaceVariant = CardDarkGreenElevated, // Elevated Dark Green #23463E
    onSurfaceVariant = Color(0xFFD0D8D4), // High contrast secondary text #D0D8D4
    outline = CardDarkGreenBorder, // Subtle dark-green border #2A5249
    outlineVariant = Color(0xFF1A3D35),
    error = Color(0xFFEF4444),
    onError = Color.White
  )

@Composable
fun SahayakTheme(
  darkTheme: Boolean = false, // Default to clean modern healthcare Light theme
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}


