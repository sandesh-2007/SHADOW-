package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AnonymousIdentity
import com.example.ui.components.PrivacyBadge
import com.example.ui.components.ShadowAvatar
import com.example.ui.components.ShadowGradientButton
import com.example.ui.components.ShadowOutlinedButton
import com.example.ui.theme.ShadowAccentCyan
import com.example.ui.theme.ShadowAccentViolet
import com.example.ui.theme.ShadowBackground
import com.example.ui.theme.ShadowCard
import com.example.ui.theme.ShadowCardBorder
import com.example.ui.theme.ShadowPrimaryGradient
import com.example.ui.theme.ShadowTextMuted
import com.example.ui.theme.ShadowTextPrimary
import com.example.ui.theme.ShadowTextSecondary

@Composable
fun SplashScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "splash_glow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ShadowBackground)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        // Ambient background glow
        Box(
            modifier = Modifier
                .size(320.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x338B5CF6), Color(0x1422D3EE), Color.Transparent)
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Logo Mark
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(ShadowCard)
                    .border(2.dp, ShadowPrimaryGradient, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🌑", fontSize = 52.sp)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "SHADOW",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp,
                    color = ShadowTextPrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Talk to someone. Stay unknown.",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = ShadowAccentCyan,
                    letterSpacing = 0.5.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            PrivacyBadge(text = "College Campus Node Alpha")
        }
    }
}

@Composable
fun WelcomeScreen(
    onEnterShadow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ShadowBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header & branding
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 28.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF131724))
                        .border(1.dp, Color(0xFF23293D), RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(text = "🌑", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SHADOW • COLLEGE EDITION",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowAccentViolet,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Meet someone\nnew.",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = ShadowTextPrimary,
                        lineHeight = 42.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "No login. No profile. No pressure.\nJust real campus conversations.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = ShadowTextSecondary,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }

            // Core Pillars Cards
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PillarFeatureCard(
                    icon = Icons.Default.Lock,
                    title = "Zero Identity Footprint",
                    description = "No phone, no email, no password. An anonymous alias is assigned automatically."
                )
                PillarFeatureCard(
                    icon = Icons.Default.School,
                    title = "College Campus Mesh",
                    description = "Connect exclusively with peers at your college community."
                )
                PillarFeatureCard(
                    icon = Icons.Default.FlashOn,
                    title = "Temporary & Ephemeral",
                    description = "Conversations vanish on timer. No follower counts, no history."
                )
            }

            // Bottom CTA
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ShadowGradientButton(
                    text = "ENTER SHADOW",
                    onClick = onEnterShadow,
                    testTag = "enter_shadow_button"
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = ShadowAccentCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Your identity stays private.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShadowTextMuted,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AnonymousIdentityScreen(
    identity: AnonymousIdentity,
    onRegenerate: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ShadowBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(
                    text = "YOUR TEMPORARY IDENTITY",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ShadowAccentCyan,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Welcome to the Shadows",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ShadowTextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Other students will only see your temporary avatar and pseudonym.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ShadowTextSecondary
                    ),
                    textAlign = TextAlign.Center
                )
            }

            // Identity Display Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(26.dp))
                    .background(ShadowCard)
                    .border(1.5.dp, ShadowPrimaryGradient, RoundedCornerShape(26.dp))
                    .padding(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedContent(
                        targetState = identity,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "identity_anim"
                    ) { currentIdentity ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ShadowAvatar(
                                emoji = currentIdentity.emoji,
                                gradientIndex = currentIdentity.gradientIndex,
                                size = 96.dp,
                                showOnlineDot = true
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            Text(
                                text = currentIdentity.displayName,
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp,
                                    color = ShadowTextPrimary
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = ShadowAccentViolet,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = currentIdentity.collegeCampus,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = ShadowTextSecondary
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ShadowOutlinedButton(
                        text = "GENERATE ANOTHER",
                        onClick = onRegenerate,
                        leadingIcon = Icons.Default.Autorenew,
                        testTag = "regenerate_identity_button"
                    )
                }
            }

            // Privacy Notice & Continue
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF111420))
                        .border(1.dp, Color(0xFF1D2233), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = ShadowAccentCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "No real names. No phone numbers. No permanent traces.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ShadowTextSecondary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                ShadowGradientButton(
                    text = "CONTINUE TO SHADOW",
                    onClick = onContinue,
                    testTag = "continue_to_shadow_button"
                )
            }
        }
    }
}

@Composable
private fun PillarFeatureCard(
    icon: ImageVector,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(ShadowCard)
            .border(1.dp, ShadowCardBorder, RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFF1F2436)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ShadowAccentViolet,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ShadowTextPrimary
                )
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ShadowTextSecondary,
                    lineHeight = 16.sp
                )
            )
        }
    }
}
