package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Interest
import com.example.ui.theme.ShadowAccentCyan
import com.example.ui.theme.ShadowAccentViolet
import com.example.ui.theme.ShadowAvatarGradients
import com.example.ui.theme.ShadowBackground
import com.example.ui.theme.ShadowCard
import com.example.ui.theme.ShadowCardBorder
import com.example.ui.theme.ShadowError
import com.example.ui.theme.ShadowPrimaryGradient
import com.example.ui.theme.ShadowSuccess
import com.example.ui.theme.ShadowTextMuted
import com.example.ui.theme.ShadowTextPrimary
import com.example.ui.theme.ShadowTextSecondary

@Composable
fun ShadowAvatar(
    emoji: String,
    gradientIndex: Int = 0,
    size: Dp = 48.dp,
    showOnlineDot: Boolean = true,
    modifier: Modifier = Modifier
) {
    val gradientColors = ShadowAvatarGradients.getOrElse(gradientIndex % ShadowAvatarGradients.size) {
        listOf(ShadowAccentViolet, ShadowAccentCyan)
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow rim
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Brush.linearGradient(gradientColors))
                .padding(2.dp)
        ) {
            // Inner dark capsule
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(ShadowBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    fontSize = (size.value * 0.44f).sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (showOnlineDot) {
            Box(
                modifier = Modifier
                    .size(size * 0.28f)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(ShadowSuccess)
                    .border(2.dp, ShadowBackground, CircleShape)
            )
        }
    }
}

@Composable
fun ShadowGradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    testTag: String = "primary_button",
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(18.dp)
    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        color = Color.Transparent,
        modifier = modifier
            .testTag(testTag)
            .fillMaxWidth()
            .height(54.dp)
            .minimumInteractiveComponentSize()
    ) {
        Box(
            modifier = Modifier
                .background(if (enabled) ShadowPrimaryGradient else Brush.linearGradient(listOf(ShadowCard, ShadowCardBorder)))
                .clip(shape)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }
}

@Composable
fun ShadowOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    testTag: String = "secondary_button"
) {
    val shape = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier
            .testTag(testTag)
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .background(ShadowCard)
            .border(1.dp, ShadowCardBorder, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = ShadowTextSecondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = ShadowTextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun ShadowDangerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "danger_button"
) {
    val shape = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier
            .testTag(testTag)
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape)
            .background(ShadowError.copy(alpha = 0.12f))
            .border(1.dp, ShadowError.copy(alpha = 0.4f), shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                color = ShadowError,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun InterestChip(
    interest: Interest,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(14.dp)
    val background = if (isSelected) Color(0xFF201B36) else ShadowCard
    val borderBrush = if (isSelected) ShadowPrimaryGradient else Brush.linearGradient(listOf(ShadowCardBorder, ShadowCardBorder))

    Box(
        modifier = modifier
            .testTag("interest_chip_${interest.id}")
            .clip(shape)
            .background(background)
            .border(1.2.dp, borderBrush, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .defaultMinSize(minHeight = 44.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = interest.emoji, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = interest.name,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = if (isSelected) ShadowTextPrimary else ShadowTextSecondary,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = ShadowAccentCyan,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun PrivacyBadge(
    modifier: Modifier = Modifier,
    text: String = "Both users are anonymous"
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF131622))
            .border(1.dp, Color(0xFF262B3D), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = ShadowAccentViolet,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = ShadowTextSecondary,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun CoarseLocationBadge(
    distanceText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF131A26))
            .border(1.dp, Color(0xFF1F2E45), RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.NearMe,
            contentDescription = null,
            tint = ShadowAccentCyan,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = distanceText,
            style = MaterialTheme.typography.labelSmall.copy(
                color = ShadowAccentCyan,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
fun PulseRadar(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar_pulse")
    val scale1 by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_scale1"
    )
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_alpha1"
    )

    val scale2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, delayMillis = 700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_scale2"
    )
    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, delayMillis = 700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_alpha2"
    )

    Box(
        modifier = modifier.size(240.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer pulsing ring 1
        Box(
            modifier = Modifier
                .size(220.dp)
                .scale(scale1)
                .clip(CircleShape)
                .border(2.dp, ShadowAccentViolet.copy(alpha = alpha1), CircleShape)
        )

        // Pulsing ring 2
        Box(
            modifier = Modifier
                .size(170.dp)
                .scale(scale2)
                .clip(CircleShape)
                .border(2.dp, ShadowAccentCyan.copy(alpha = alpha2), CircleShape)
        )

        // Fixed inner grid circles
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .border(1.dp, Color(0xFF222638), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFF171A26))
                .border(1.dp, Color(0xFF2B3147), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🌑",
                fontSize = 32.sp
            )
        }
    }
}

@Composable
fun ShadowTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    subtitle: String? = null,
    actions: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(ShadowCard)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = ShadowTextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ShadowTextPrimary
                    )
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShadowTextMuted
                        )
                    )
                }
            }
        }
        actions()
    }
}
