package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AvailableInterests
import com.example.model.ConnectionPreference
import com.example.model.Interest
import com.example.ui.components.InterestChip
import com.example.ui.components.ShadowGradientButton
import com.example.ui.components.ShadowTopBar
import com.example.ui.theme.ShadowAccentCyan
import com.example.ui.theme.ShadowAccentViolet
import com.example.ui.theme.ShadowBackground
import com.example.ui.theme.ShadowCard
import com.example.ui.theme.ShadowCardBorder
import com.example.ui.theme.ShadowPrimaryGradient
import com.example.ui.theme.ShadowTextMuted
import com.example.ui.theme.ShadowTextPrimary
import com.example.ui.theme.ShadowTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestSelectionScreen(
    selectedInterests: List<Interest>,
    onToggleInterest: (Interest) -> Unit,
    onFindSomeoneClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

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
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                ShadowTopBar(
                    title = "Choose Your Vibes",
                    subtitle = "Select what you want to talk about today",
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Stats pill
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(ShadowCard)
                        .border(1.dp, ShadowCardBorder, RoundedCornerShape(14.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active topics selected",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ShadowTextSecondary
                        )
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF261D40))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${selectedInterests.size} selected",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ShadowAccentViolet,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Flow of Interest Chips
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AvailableInterests.forEach { interest ->
                        val isSelected = selectedInterests.any { it.id == interest.id }
                        InterestChip(
                            interest = interest,
                            isSelected = isSelected,
                            onClick = { onToggleInterest(interest) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Privacy Note
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF10131E))
                        .border(1.dp, Color(0xFF1D2235), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = ShadowAccentCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Interests are used strictly to calculate conversation compatibility in the shadows.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ShadowTextSecondary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Bottom CTA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                ShadowGradientButton(
                    text = "FIND SOMEONE",
                    onClick = onFindSomeoneClick,
                    testTag = "find_someone_button"
                )
            }
        }
    }
}

@Composable
fun ConnectionPreferenceScreen(
    currentPreference: ConnectionPreference,
    onSelectPreference: (ConnectionPreference) -> Unit,
    onBackClick: () -> Unit,
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
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                ShadowTopBar(
                    title = "Connection Preferences",
                    subtitle = "Who do you want to connect with?",
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Privacy banner at top
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF131522))
                        .border(1.dp, Color(0xFF262A3D), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = ShadowAccentViolet,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Your preference is used only for matching and is NEVER publicly displayed to other users.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ShadowTextPrimary,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 20.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Options List
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    PreferenceOptionCard(
                        preference = ConnectionPreference.ANYONE,
                        isSelected = currentPreference == ConnectionPreference.ANYONE,
                        onSelect = { onSelectPreference(ConnectionPreference.ANYONE) },
                        icon = Icons.Default.Groups
                    )

                    PreferenceOptionCard(
                        preference = ConnectionPreference.MALE,
                        isSelected = currentPreference == ConnectionPreference.MALE,
                        onSelect = { onSelectPreference(ConnectionPreference.MALE) },
                        icon = Icons.Default.Male
                    )

                    PreferenceOptionCard(
                        preference = ConnectionPreference.FEMALE,
                        isSelected = currentPreference == ConnectionPreference.FEMALE,
                        onSelect = { onSelectPreference(ConnectionPreference.FEMALE) },
                        icon = Icons.Default.Female
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                ShadowGradientButton(
                    text = "SAVE PREFERENCE",
                    onClick = onBackClick,
                    testTag = "save_preference_button"
                )
            }
        }
    }
}

@Composable
private fun PreferenceOptionCard(
    preference: ConnectionPreference,
    isSelected: Boolean,
    onSelect: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    val shape = RoundedCornerShape(20.dp)
    val borderBrush = if (isSelected) ShadowPrimaryGradient else androidx.compose.ui.graphics.Brush.linearGradient(listOf(ShadowCardBorder, ShadowCardBorder))
    val bg = if (isSelected) Color(0xFF1B1A2C) else ShadowCard

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pref_option_${preference.name}")
            .clip(shape)
            .background(bg)
            .border(1.2.dp, borderBrush, shape)
            .clickable(onClick = onSelect)
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFF282344) else Color(0xFF1E2233)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSelected) ShadowAccentCyan else ShadowTextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = preference.label,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShadowTextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = preference.subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShadowTextMuted
                        )
                    )
                }
            }

            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(
                    selectedColor = ShadowAccentViolet,
                    unselectedColor = ShadowCardBorder
                )
            )
        }
    }
}
