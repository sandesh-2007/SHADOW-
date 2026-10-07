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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AnonymousIdentity
import com.example.model.ConnectionPreference
import com.example.model.Interest
import com.example.ui.components.PrivacyBadge
import com.example.ui.components.ShadowAvatar
import com.example.ui.theme.ShadowAccentCyan
import com.example.ui.theme.ShadowAccentViolet
import com.example.ui.theme.ShadowBackground
import com.example.ui.theme.ShadowCard
import com.example.ui.theme.ShadowCardBorder
import com.example.ui.theme.ShadowCardHover
import com.example.ui.theme.ShadowPrimaryGradient
import com.example.ui.theme.ShadowSuccess
import com.example.ui.theme.ShadowTextMuted
import com.example.ui.theme.ShadowTextPrimary
import com.example.ui.theme.ShadowTextSecondary

@Composable
fun HomeScreen(
    identity: AnonymousIdentity,
    selectedInterests: List<Interest>,
    preference: ConnectionPreference,
    onRandomConnectClick: () -> Unit,
    onNearPeopleClick: () -> Unit,
    onManageInterestsClick: () -> Unit,
    onManagePreferenceClick: () -> Unit,
    onRegenerateIdentityClick: () -> Unit,
    onPrivacyCenterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ShadowBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Top App Bar with Temporary Identity Pill
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "SHADOW",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            color = ShadowTextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF231E3D))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "CAMPUS MESH",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ShadowAccentViolet,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(ShadowSuccess)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "418 students online in shadows",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShadowTextMuted
                        )
                    )
                }
            }

            // Quick Privacy Center Icon
            IconButton(
                onClick = onPrivacyCenterClick,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(ShadowCard)
                    .border(1.dp, ShadowCardBorder, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Privacy Center",
                    tint = ShadowAccentCyan,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Temporary Identity Interactive Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(ShadowCard)
                .border(1.dp, ShadowCardBorder, RoundedCornerShape(22.dp))
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ShadowAvatar(
                        emoji = identity.emoji,
                        gradientIndex = identity.gradientIndex,
                        size = 54.dp
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = identity.displayName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShadowTextPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = null,
                                tint = ShadowAccentViolet,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = identity.collegeCampus,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = ShadowTextSecondary
                                )
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onRegenerateIdentityClick,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF222638))
                        .testTag("refresh_identity_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Autorenew,
                        contentDescription = "Regenerate Identity",
                        tint = ShadowAccentCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section Title: Connect Modes
        Text(
            text = "CONNECT MODES",
            style = MaterialTheme.typography.labelSmall.copy(
                color = ShadowTextMuted,
                letterSpacing = 1.2.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // TWO DOMINANT ACTIONS (As specified in brief)
        // 1. RANDOM CONNECT
        DominantActionCard(
            title = "⚡ RANDOM CONNECT",
            subtitle = "Meet an available anonymous student from your college",
            gradient = ShadowPrimaryGradient,
            badgeText = "Fastest",
            onClick = onRandomConnectClick,
            testTag = "random_connect_card"
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 2. NEAR PEOPLE
        DominantActionCard(
            title = "📍 NEAR PEOPLE",
            subtitle = "Discover anonymous students approximately nearby",
            gradient = Brush.horizontalGradient(
                colors = listOf(Color(0xFF0F766E), Color(0xFF06B6D4))
            ),
            badgeText = "Within 5 km",
            onClick = onNearPeopleClick,
            testTag = "near_people_card"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Active Interests Strip with Edit Action
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "YOUR ACTIVE VIBES",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = ShadowTextMuted,
                    letterSpacing = 1.2.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onManageInterestsClick)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Edit Vibes",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ShadowAccentViolet,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = null,
                    tint = ShadowAccentViolet,
                    modifier = Modifier.size(13.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(selectedInterests) { interest ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1E2232))
                        .border(1.dp, Color(0xFF2C3247), RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = interest.emoji, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = interest.name,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ShadowTextPrimary,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Connection Preference Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(ShadowCard)
                .border(1.dp, ShadowCardBorder, RoundedCornerShape(18.dp))
                .clickable(onClick = onManagePreferenceClick)
                .padding(16.dp)
                .testTag("connection_preference_banner")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF212538)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = ShadowAccentCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Matching Preference",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ShadowTextMuted
                            )
                        )
                        Text(
                            text = "${preference.label} • Private filter",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShadowTextPrimary
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

        Spacer(modifier = Modifier.height(18.dp))

        // Privacy Guarantee Footer Pill
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF0F121C))
                .border(1.dp, Color(0xFF1E2336), RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = ShadowAccentViolet,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Coarse location only • No logs • Zero public profiles",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ShadowTextSecondary,
                        fontSize = 11.5.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun DominantActionCard(
    title: String,
    subtitle: String,
    gradient: Brush,
    badgeText: String,
    onClick: () -> Unit,
    testTag: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
            .clip(RoundedCornerShape(24.dp))
            .background(ShadowCard)
            .border(1.2.dp, gradient, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = ShadowTextPrimary
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF262C40))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowAccentCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ShadowTextSecondary,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TAP TO CONNECT",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = ShadowAccentCyan,
                        letterSpacing = 1.sp
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = ShadowAccentCyan,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
