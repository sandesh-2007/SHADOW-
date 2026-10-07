package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// SHADOW Color Palette
val ShadowBackground = Color(0xFF08090D)
val ShadowSurface = Color(0xFF11131A)
val ShadowCard = Color(0xFF171A23)
val ShadowCardBorder = Color(0xFF232736)
val ShadowCardHover = Color(0xFF1D212E)

// Accents
val ShadowAccentViolet = Color(0xFF8B5CF6)
val ShadowAccentCyan = Color(0xFF22D3EE)
val ShadowAccentPink = Color(0xFFEC4899)
val ShadowAccentGlow = Color(0x338B5CF6)
val ShadowCyanGlow = Color(0x3322D3EE)

// Typography & Content
val ShadowTextPrimary = Color(0xFFF8FAFC)
val ShadowTextSecondary = Color(0xFF94A3B8)
val ShadowTextMuted = Color(0xFF64748B)

// Status
val ShadowSuccess = Color(0xFF34D399)
val ShadowError = Color(0xFFFB7185)
val ShadowWarning = Color(0xFFFBBF24)

// Gradients
val ShadowPrimaryGradient = Brush.horizontalGradient(
    colors = listOf(ShadowAccentViolet, ShadowAccentCyan)
)

val ShadowSurfaceGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF151824), ShadowBackground)
)

val ShadowGlowGradient = Brush.radialGradient(
    colors = listOf(Color(0x4D8B5CF6), Color.Transparent)
)

val ShadowAvatarGradients = listOf(
    listOf(Color(0xFF8B5CF6), Color(0xFF22D3EE)),
    listOf(Color(0xFF6366F1), Color(0xFFA855F7)),
    listOf(Color(0xFF06B6D4), Color(0xFF3B82F6)),
    listOf(Color(0xFFEC4899), Color(0xFF8B5CF6)),
    listOf(Color(0xFF10B981), Color(0xFF06B6D4)),
    listOf(Color(0xFFF59E0B), Color(0xFFEF4444))
)
