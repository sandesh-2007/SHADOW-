package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatDuration
import com.example.model.NearbyShadow
import com.example.ui.components.CoarseLocationBadge
import com.example.ui.components.PulseRadar
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
fun RandomMatchingScreen(
    statusText: String,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ShadowBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Cancel X button at top right
        IconButton(
            onClick = onCancel,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp)
                .size(44.dp)
                .clip(CircleShape)
                .background(ShadowCard)
                .border(1.dp, ShadowCardBorder, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cancel Matching",
                tint = ShadowTextSecondary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text(
                    text = "Finding someone...",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = ShadowTextPrimary
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Searching the shadows",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = ShadowAccentCyan,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = statusText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ShadowTextMuted
                    ),
                    textAlign = TextAlign.Center
                )
            }

            // Radar animation in center
            PulseRadar()

            // Bottom cancel CTA
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🔒 Matching within your college network only",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ShadowTextMuted
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                ShadowOutlinedButton(
                    text = "CANCEL",
                    onClick = onCancel,
                    testTag = "cancel_matching_button"
                )
            }
        }
    }
}

@Composable
fun MatchFoundScreen(
    peer: NearbyShadow,
    selectedDuration: ChatDuration,
    onDurationSelect: (ChatDuration) -> Unit,
    onStartChat: () -> Unit,
    onFindAnother: () -> Unit,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF261D40))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "⚡ CONNECTION ESTABLISHED",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowAccentViolet,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You found someone.",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = ShadowTextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "A fellow anonymous student from your campus mesh",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ShadowTextSecondary
                    ),
                    textAlign = TextAlign.Center
                )
            }

            // Matched Peer Showcase Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(26.dp))
                    .background(ShadowCard)
                    .border(1.5.dp, ShadowPrimaryGradient, RoundedCornerShape(26.dp))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ShadowAvatar(
                        emoji = peer.emoji,
                        gradientIndex = peer.gradientIndex,
                        size = 80.dp,
                        showOnlineDot = true
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = peer.displayName,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = ShadowTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    CoarseLocationBadge(distanceText = peer.approximateDistance)

                    Spacer(modifier = Modifier.height(18.dp))

                    // Shared Interests
                    Text(
                        text = "COMPATIBLE VIBES",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowTextMuted,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        peer.interests.forEach { interest ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF222638))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${interest.emoji} ${interest.name}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = ShadowTextPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Duration Selector
                    Text(
                        text = "TEMPORARY CHAT TIMER",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowTextMuted,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ChatDuration.values().forEach { duration ->
                            val isSelected = selectedDuration == duration
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) ShadowAccentViolet else Color(0xFF1E2232))
                                    .clickable { onDurationSelect(duration) }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when (duration) {
                                        ChatDuration.TEN_MINUTES -> "10m"
                                        ChatDuration.THIRTY_MINUTES -> "30m"
                                        ChatDuration.ONE_HOUR -> "1h"
                                        ChatDuration.UNTIL_CHAT_ENDS -> "Until End"
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) Color.White else ShadowTextSecondary,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Bottom CTA
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ShadowGradientButton(
                    text = "START CHAT",
                    onClick = onStartChat,
                    testTag = "start_chat_button"
                )

                ShadowOutlinedButton(
                    text = "FIND ANOTHER",
                    onClick = onFindAnother,
                    testTag = "find_another_button"
                )
            }
        }
    }
}
