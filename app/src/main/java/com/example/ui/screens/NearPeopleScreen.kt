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
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.WifiTethering
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
import com.example.model.NearbyShadow
import com.example.ui.components.CoarseLocationBadge
import com.example.ui.components.ShadowAvatar
import com.example.ui.components.ShadowGradientButton
import com.example.ui.components.ShadowOutlinedButton
import com.example.ui.theme.ShadowAccentCyan
import com.example.ui.theme.ShadowAccentViolet
import com.example.ui.theme.ShadowBackground
import com.example.ui.theme.ShadowCard
import com.example.ui.theme.ShadowCardBorder
import com.example.ui.theme.ShadowTextMuted
import com.example.ui.theme.ShadowTextPrimary
import com.example.ui.theme.ShadowTextSecondary

@Composable
fun NearPeopleScreen(
    nearbyShadows: List<NearbyShadow>,
    isEmptySimulated: Boolean,
    onToggleEmptySimulation: (Boolean) -> Unit,
    onConnectClick: (NearbyShadow) -> Unit,
    onRandomConnectClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ShadowBackground)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Screen Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Near People",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = ShadowTextPrimary
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Coarse campus radar (Approximate only)",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ShadowAccentCyan
                    )
                )
            }

            // Quick toggle to simulate empty vs filled nearby state (great for UI review)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(ShadowCard)
                    .border(1.dp, ShadowCardBorder, RoundedCornerShape(10.dp))
                    .clickable { onToggleEmptySimulation(!isEmptySimulated) }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WifiTethering,
                        contentDescription = null,
                        tint = if (isEmptySimulated) ShadowTextMuted else ShadowAccentViolet,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isEmptySimulated) "Show Active" else "Test Empty",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowTextSecondary,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Privacy Guarantee Notice
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF10131F))
                .border(1.dp, Color(0xFF1E2436), RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = ShadowAccentViolet,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "No GPS coordinates or exact locations are ever shared.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ShadowTextSecondary,
                        fontSize = 11.5.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // If list is empty -> Show "14. NO NEARBY USERS" state
        if (nearbyShadows.isEmpty()) {
            NoNearbyUsersEmptyState(
                onRandomConnect = onRandomConnectClick,
                onTryAgain = { onToggleEmptySimulation(false) }
            )
        } else {
            // Display Nearby Users List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(nearbyShadows, key = { it.id }) { shadow ->
                    NearbyUserCard(
                        shadow = shadow,
                        onConnect = { onConnectClick(shadow) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NearbyUserCard(
    shadow: NearbyShadow,
    onConnect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("nearby_card_${shadow.id}")
            .clip(RoundedCornerShape(20.dp))
            .background(ShadowCard)
            .border(1.dp, ShadowCardBorder, RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ShadowAvatar(
                        emoji = shadow.emoji,
                        gradientIndex = shadow.gradientIndex,
                        size = 52.dp,
                        showOnlineDot = shadow.isOnline
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = shadow.displayName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShadowTextPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        CoarseLocationBadge(distanceText = shadow.approximateDistance)
                    }
                }

                // Primary Connect Action
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF261D40))
                        .border(1.dp, Color(0xFF3B2F63), RoundedCornerShape(14.dp))
                        .clickable(onClick = onConnect)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "CONNECT",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowAccentCyan,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Interests row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                shadow.interests.forEach { interest ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF1E2232))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${interest.emoji} ${interest.name}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ShadowTextSecondary,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NoNearbyUsersEmptyState(
    onRandomConnect: () -> Unit,
    onTryAgain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(Color(0xFF161925))
                .border(1.dp, ShadowCardBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Radar,
                contentDescription = null,
                tint = ShadowAccentCyan,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No shadows nearby.",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = ShadowTextPrimary
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Try Random Connect or check again later when more students are walking around the campus nodes.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = ShadowTextSecondary,
                lineHeight = 22.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        ShadowGradientButton(
            text = "RANDOM CONNECT",
            onClick = onRandomConnect,
            leadingIcon = Icons.Default.FlashOn,
            testTag = "empty_random_connect_button"
        )

        Spacer(modifier = Modifier.height(12.dp))

        ShadowOutlinedButton(
            text = "TRY AGAIN",
            onClick = onTryAgain,
            leadingIcon = Icons.Default.Refresh,
            testTag = "try_again_button"
        )
    }
}
