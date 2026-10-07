package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ShadowDarkColorScheme = darkColorScheme(
    primary = ShadowAccentViolet,
    onPrimary = ShadowTextPrimary,
    primaryContainer = ShadowCard,
    onPrimaryContainer = ShadowTextPrimary,
    secondary = ShadowAccentCyan,
    onSecondary = ShadowBackground,
    secondaryContainer = ShadowCardHover,
    onSecondaryContainer = ShadowAccentCyan,
    tertiary = ShadowAccentPink,
    onTertiary = ShadowTextPrimary,
    background = ShadowBackground,
    onBackground = ShadowTextPrimary,
    surface = ShadowSurface,
    onSurface = ShadowTextPrimary,
    surfaceVariant = ShadowCard,
    onSurfaceVariant = ShadowTextSecondary,
    outline = ShadowCardBorder,
    outlineVariant = ShadowCardBorder,
    error = ShadowError,
    onError = ShadowBackground
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    // SHADOW is strictly a dark-first, mysterious & premium mobile experience
    MaterialTheme(
        colorScheme = ShadowDarkColorScheme,
        typography = Typography,
        content = content
    )
}
