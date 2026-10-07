package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneDisabled
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.PublicOff
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VpnKeyOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ConnectionPreference
import com.example.ui.components.ShadowTopBar
import com.example.ui.theme.ShadowAccentCyan
import com.example.ui.theme.ShadowAccentViolet
import com.example.ui.theme.ShadowBackground
import com.example.ui.theme.ShadowCard
import com.example.ui.theme.ShadowCardBorder
import com.example.ui.theme.ShadowPrimaryGradient
import com.example.ui.theme.ShadowSuccess
import com.example.ui.theme.ShadowTextMuted
import com.example.ui.theme.ShadowTextPrimary
import com.example.ui.theme.ShadowTextSecondary

@Composable
fun SettingsScreen(
    preference: ConnectionPreference,
    coarseLocationEnabled: Boolean,
    onManagePreferencesClick: () -> Unit,
    onManageInterestsClick: () -> Unit,
    onPrivacyCenterClick: () -> Unit,
    onSafetyCenterClick: () -> Unit,
    onToggleCoarseLocation: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var notificationsEnabled by remember { mutableStateOf(true) }
    var hapticFeedbackEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ShadowBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                color = ShadowTextPrimary
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Zero account • Ephemeral preferences",
            style = MaterialTheme.typography.bodySmall.copy(
                color = ShadowAccentCyan
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // SECTION 1: MATCHING & PREFERENCES
        SettingsSectionHeader(title = "MATCHING PREFERENCES")
        SettingsCardGroup {
            SettingsNavigationRow(
                icon = Icons.Default.Person,
                title = "Connection Preference",
                subtitle = "${preference.label} • Private filter",
                onClick = onManagePreferencesClick,
                testTag = "settings_pref_row"
            )
            SettingsDivider()
            SettingsNavigationRow(
                icon = Icons.Default.Tune,
                title = "Interests & Vibes",
                subtitle = "Choose matching topics",
                onClick = onManageInterestsClick,
                testTag = "settings_interests_row"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // SECTION 2: PRIVACY & SAFETY
        SettingsSectionHeader(title = "PRIVACY & SAFETY")
        SettingsCardGroup {
            SettingsNavigationRow(
                icon = Icons.Default.Lock,
                title = "Privacy Center",
                subtitle = "View your 6-pillar zero-data shield",
                onClick = onPrivacyCenterClick,
                accentColor = ShadowAccentCyan,
                testTag = "settings_privacy_row"
            )
            SettingsDivider()
            SettingsNavigationRow(
                icon = Icons.Default.Shield,
                title = "Safety Center",
                subtitle = "Campus protections, reporting, and guidelines",
                onClick = onSafetyCenterClick,
                accentColor = ShadowAccentViolet,
                testTag = "settings_safety_row"
            )
            SettingsDivider()
            SettingsToggleRow(
                icon = Icons.Default.NearMe,
                title = "Approximate Campus Radar",
                subtitle = "Coarse radius matching only",
                checked = coarseLocationEnabled,
                onCheckedChange = onToggleCoarseLocation
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // SECTION 3: NOTIFICATIONS & SYSTEM
        SettingsSectionHeader(title = "NOTIFICATIONS")
        SettingsCardGroup {
            SettingsToggleRow(
                icon = Icons.Default.Notifications,
                title = "Match Alerts",
                subtitle = "Notify when someone enters the shadow",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            SettingsDivider()
            SettingsToggleRow(
                icon = Icons.Default.DarkMode,
                title = "Haptic Vibration",
                subtitle = "Tactile feedback during matching",
                checked = hapticFeedbackEnabled,
                onCheckedChange = { hapticFeedbackEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // SECTION 4: ABOUT SHADOW
        SettingsSectionHeader(title = "ABOUT SHADOW")
        SettingsCardGroup {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🌑", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "SHADOW • Campus Edition",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ShadowTextPrimary
                                )
                            )
                            Text(
                                text = "Version 1.0.0 • Mesh Protocol",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = ShadowTextMuted
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF221A33))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "OFFICIAL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ShadowAccentViolet,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
fun PrivacyCenterScreen(
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
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState)
        ) {
            ShadowTopBar(
                title = "Privacy Center",
                subtitle = "Our strict Zero-Data architecture",
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF131522))
                    .border(1.2.dp, ShadowPrimaryGradient, RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = ShadowAccentCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "PRIVACY BY DEFAULT",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = ShadowAccentCyan,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "SHADOW is engineered so that your real identity is never requested, collected, stored, or revealed.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ShadowTextPrimary,
                            lineHeight = 20.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // CLEAR VISUAL CARDS (As requested: 18. PRIVACY CENTER)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PrivacyPillarCard(
                    icon = Icons.Default.VpnKeyOff,
                    title = "No Login",
                    description = "Open the app and you are immediately inside. No credentials exist."
                )

                PrivacyPillarCard(
                    icon = Icons.Default.PhoneDisabled,
                    title = "No Phone Number",
                    description = "We never request SMS verification, contacts, or phone identifiers."
                )

                PrivacyPillarCard(
                    icon = Icons.Default.MailOutline,
                    title = "No Email",
                    description = "Zero registration emails or password reset tokens."
                )

                PrivacyPillarCard(
                    icon = Icons.Default.PublicOff,
                    title = "No Public Profile",
                    description = "No follower count, no public wall, no permanent links to stalk."
                )

                PrivacyPillarCard(
                    icon = Icons.Default.LocationOff,
                    title = "Approximate Location Only",
                    description = "Coarse campus radar (Within 2 km). GPS coordinates are never stored or shared."
                )

                PrivacyPillarCard(
                    icon = Icons.Default.HourglassEmpty,
                    title = "Temporary Identity",
                    description = "Dynamic pseudonyms (e.g. 🌑 Shadow-4821) that you can scramble at any time."
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun SafetyCenterScreen(
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
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState)
        ) {
            ShadowTopBar(
                title = "Safety Center",
                subtitle = "Student guidelines & active safeguards",
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Community Safety Disclaimer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF191322))
                    .border(1.dp, Color(0xFF38234D), RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = ShadowAccentViolet,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "COMMUNICATION, NOT DATING",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = ShadowAccentViolet,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "SHADOW is a college social and chat network built for friendly student conversations. Harassment, solicitation, and scams are strictly prohibited.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ShadowTextPrimary,
                            lineHeight = 20.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Safety Features
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SafetyFeatureItem(
                    title = "Instant Block",
                    description = "Block any shadow instantly with one tap. They will never match with you again."
                )

                SafetyFeatureItem(
                    title = "Harassment Reporting",
                    description = "Flag inappropriate behavior. Multiple reports automatically revoke network access."
                )

                SafetyFeatureItem(
                    title = "Ephemeral Sessions",
                    description = "When a conversation ends, its transcript is erased. No screenshots or logs."
                )

                SafetyFeatureItem(
                    title = "User-Controlled Connections",
                    description = "You can leave any conversation at any moment using 'Next Person' with zero penalty."
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Subcomponents
@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
            color = ShadowTextMuted,
            letterSpacing = 1.2.sp,
            fontWeight = FontWeight.Bold
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun SettingsCardGroup(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(ShadowCard)
            .border(1.dp, ShadowCardBorder, RoundedCornerShape(20.dp))
    ) {
        content()
    }
}

@Composable
private fun SettingsNavigationRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    accentColor: Color = ShadowTextSecondary,
    testTag: String = "settings_row"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF202436)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ShadowTextPrimary
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ShadowTextMuted
                    )
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = ShadowTextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SettingsToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF202436)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = ShadowTextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ShadowTextPrimary
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ShadowTextMuted
                    )
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = ShadowAccentViolet,
                uncheckedTrackColor = Color(0xFF1E2333)
            )
        )
    }
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(ShadowCardBorder)
    )
}

@Composable
private fun PrivacyPillarCard(
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
                .background(Color(0xFF1B2030)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ShadowAccentCyan,
                modifier = Modifier.size(20.dp)
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
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ShadowTextSecondary,
                    lineHeight = 17.sp
                )
            )
        }
    }
}

@Composable
private fun SafetyFeatureItem(
    title: String,
    description: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(ShadowCard)
            .border(1.dp, ShadowCardBorder, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ShadowTextPrimary
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ShadowTextSecondary,
                    lineHeight = 18.sp
                )
            )
        }
    }
}
