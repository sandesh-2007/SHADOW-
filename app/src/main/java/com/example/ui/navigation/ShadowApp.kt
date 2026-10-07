package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AnonymousIdentityScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.ChatsListScreen
import com.example.ui.screens.ConnectionPreferenceScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InterestSelectionScreen
import com.example.ui.screens.MatchFoundScreen
import com.example.ui.screens.NearPeopleScreen
import com.example.ui.screens.PrivacyCenterScreen
import com.example.ui.screens.RandomMatchingScreen
import com.example.ui.screens.SafetyCenterScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.ShadowAccentCyan
import com.example.ui.theme.ShadowAccentViolet
import com.example.ui.theme.ShadowBackground
import com.example.ui.theme.ShadowCard
import com.example.ui.theme.ShadowCardBorder
import com.example.ui.theme.ShadowPrimaryGradient
import com.example.ui.theme.ShadowTextMuted
import com.example.ui.theme.ShadowTextPrimary
import com.example.ui.theme.ShadowTextSecondary
import com.example.viewmodel.BottomTab
import com.example.viewmodel.Screen
import com.example.viewmodel.ShadowViewModel

@Composable
fun ShadowApp(
    viewModel: ShadowViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val myIdentity by viewModel.myIdentity.collectAsStateWithLifecycle()
    val selectedInterests by viewModel.selectedInterests.collectAsStateWithLifecycle()
    val connectionPreference by viewModel.connectionPreference.collectAsStateWithLifecycle()
    val coarseLocationEnabled by viewModel.coarseLocationEnabled.collectAsStateWithLifecycle()
    val nearbyShadows by viewModel.nearbyShadows.collectAsStateWithLifecycle()
    val simulateNoNearby by viewModel.simulateNoNearby.collectAsStateWithLifecycle()
    val conversations by viewModel.conversations.collectAsStateWithLifecycle()
    val messagesMap by viewModel.messages.collectAsStateWithLifecycle()
    val matchingStatusText by viewModel.matchingStatusText.collectAsStateWithLifecycle()
    val matchedPeer by viewModel.matchedPeer.collectAsStateWithLifecycle()
    val selectedMatchDuration by viewModel.selectedMatchDuration.collectAsStateWithLifecycle()
    val activeConvId by viewModel.activeConversationId.collectAsStateWithLifecycle()
    val activeChatPeer by viewModel.activeChatPeer.collectAsStateWithLifecycle()
    val snackbarMsg by viewModel.snackBarMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMsg) {
        snackbarMsg?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearToast()
        }
    }

    // Handle back button behavior for sub-screens
    BackHandler(enabled = currentScreen != Screen.MAIN && currentScreen != Screen.SPLASH) {
        val handled = viewModel.navigateBack()
        if (!handled && currentScreen != Screen.MAIN) {
            viewModel.navigateTo(Screen.MAIN)
        }
    }

    Scaffold(
        containerColor = ShadowBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = ShadowCard,
                    contentColor = ShadowTextPrimary,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = data.visuals.message, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { screen ->
                when (screen) {
                    Screen.SPLASH -> {
                        SplashScreen(
                            onContinue = { viewModel.navigateTo(Screen.WELCOME) }
                        )
                    }

                    Screen.WELCOME -> {
                        WelcomeScreen(
                            onEnterShadow = { viewModel.navigateTo(Screen.ANONYMOUS_IDENTITY) }
                        )
                    }

                    Screen.ANONYMOUS_IDENTITY -> {
                        AnonymousIdentityScreen(
                            identity = myIdentity,
                            onRegenerate = { viewModel.regenerateIdentity() },
                            onContinue = { viewModel.navigateTo(Screen.MAIN) }
                        )
                    }

                    Screen.MAIN -> {
                        MainScreenContainer(
                            currentTab = currentTab,
                            onTabSelected = { viewModel.selectTab(it) },
                            // Home Props
                            identity = myIdentity,
                            selectedInterests = selectedInterests,
                            preference = connectionPreference,
                            onRandomConnectClick = { viewModel.startRandomMatching() },
                            onNearPeopleClick = { viewModel.selectTab(BottomTab.NEARBY) },
                            onManageInterestsClick = { viewModel.navigateTo(Screen.INTEREST_SELECTION) },
                            onManagePreferenceClick = { viewModel.navigateTo(Screen.CONNECTION_PREFERENCE) },
                            onRegenerateIdentityClick = { viewModel.regenerateIdentity() },
                            onPrivacyCenterClick = { viewModel.navigateTo(Screen.PRIVACY_CENTER) },
                            // Chats Props
                            conversations = conversations,
                            onConversationClick = { conv ->
                                viewModel.openChat(conv.id)
                            },
                            // Nearby Props
                            nearbyShadows = nearbyShadows,
                            isEmptySimulated = simulateNoNearby,
                            onToggleEmptySimulation = { viewModel.toggleEmptyNearby(it) },
                            onConnectFromNearby = { peer ->
                                viewModel.connectFromNearby(peer)
                            },
                            // Settings Props
                            coarseLocationEnabled = coarseLocationEnabled,
                            onSafetyCenterClick = { viewModel.navigateTo(Screen.SAFETY_CENTER) },
                            onToggleCoarseLocation = { viewModel.toggleCoarseLocation(it) }
                        )
                    }

                    Screen.INTEREST_SELECTION -> {
                        InterestSelectionScreen(
                            selectedInterests = selectedInterests,
                            onToggleInterest = { viewModel.toggleInterest(it) },
                            onFindSomeoneClick = { viewModel.startRandomMatching() },
                            onBackClick = { viewModel.navigateBack() }
                        )
                    }

                    Screen.CONNECTION_PREFERENCE -> {
                        ConnectionPreferenceScreen(
                            currentPreference = connectionPreference,
                            onSelectPreference = { viewModel.setConnectionPreference(it) },
                            onBackClick = { viewModel.navigateBack() }
                        )
                    }

                    Screen.RANDOM_MATCHING -> {
                        RandomMatchingScreen(
                            statusText = matchingStatusText,
                            onCancel = { viewModel.cancelMatching() }
                        )
                    }

                    Screen.MATCH_FOUND -> {
                        matchedPeer?.let { peer ->
                            MatchFoundScreen(
                                peer = peer,
                                selectedDuration = selectedMatchDuration,
                                onDurationSelect = { viewModel.setMatchDuration(it) },
                                onStartChat = { viewModel.startChatWithMatch() },
                                onFindAnother = { viewModel.startRandomMatching() }
                            )
                        } ?: run {
                            viewModel.navigateTo(Screen.MAIN)
                        }
                    }

                    Screen.CHAT -> {
                        activeConvId?.let { convId ->
                            val activeConv = conversations.firstOrNull { it.id == convId }
                            val activeMessages = messagesMap[convId] ?: emptyList()
                            ChatScreen(
                                conversationId = convId,
                                conversation = activeConv,
                                peer = activeChatPeer,
                                messages = activeMessages,
                                onSendMessage = { text, emoji, label, isVoice, voiceSec ->
                                    viewModel.sendMessage(text, emoji, label, isVoice, voiceSec)
                                },
                                onNextPerson = { viewModel.nextPerson() },
                                onEndChat = { viewModel.endCurrentChat() },
                                onBlockUser = { peerId -> viewModel.blockCurrentPeer(peerId) },
                                onReportUser = { peerId, reason, details ->
                                    viewModel.reportCurrentPeer(peerId, reason, details)
                                },
                                onBackClick = {
                                    viewModel.selectTab(BottomTab.CHATS)
                                    viewModel.navigateTo(Screen.MAIN)
                                }
                            )
                        } ?: run {
                            viewModel.navigateTo(Screen.MAIN)
                        }
                    }

                    Screen.PRIVACY_CENTER -> {
                        PrivacyCenterScreen(
                            onBackClick = { viewModel.navigateBack() }
                        )
                    }

                    Screen.SAFETY_CENTER -> {
                        SafetyCenterScreen(
                            onBackClick = { viewModel.navigateBack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MainScreenContainer(
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    // Home Props
    identity: com.example.model.AnonymousIdentity,
    selectedInterests: List<com.example.model.Interest>,
    preference: com.example.model.ConnectionPreference,
    onRandomConnectClick: () -> Unit,
    onNearPeopleClick: () -> Unit,
    onManageInterestsClick: () -> Unit,
    onManagePreferenceClick: () -> Unit,
    onRegenerateIdentityClick: () -> Unit,
    onPrivacyCenterClick: () -> Unit,
    // Chats Props
    conversations: List<com.example.model.ShadowConversation>,
    onConversationClick: (com.example.model.ShadowConversation) -> Unit,
    // Nearby Props
    nearbyShadows: List<com.example.model.NearbyShadow>,
    isEmptySimulated: Boolean,
    onToggleEmptySimulation: (Boolean) -> Unit,
    onConnectFromNearby: (com.example.model.NearbyShadow) -> Unit,
    // Settings Props
    coarseLocationEnabled: Boolean,
    onSafetyCenterClick: () -> Unit,
    onToggleCoarseLocation: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Main Tab Content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (currentTab) {
                BottomTab.HOME -> {
                    HomeScreen(
                        identity = identity,
                        selectedInterests = selectedInterests,
                        preference = preference,
                        onRandomConnectClick = onRandomConnectClick,
                        onNearPeopleClick = onNearPeopleClick,
                        onManageInterestsClick = onManageInterestsClick,
                        onManagePreferenceClick = onManagePreferenceClick,
                        onRegenerateIdentityClick = onRegenerateIdentityClick,
                        onPrivacyCenterClick = onPrivacyCenterClick
                    )
                }

                BottomTab.CHATS -> {
                    ChatsListScreen(
                        conversations = conversations,
                        onConversationClick = onConversationClick,
                        onRandomConnectClick = onRandomConnectClick
                    )
                }

                BottomTab.NEARBY -> {
                    NearPeopleScreen(
                        nearbyShadows = nearbyShadows,
                        isEmptySimulated = isEmptySimulated,
                        onToggleEmptySimulation = onToggleEmptySimulation,
                        onConnectClick = onConnectFromNearby,
                        onRandomConnectClick = onRandomConnectClick
                    )
                }

                BottomTab.SETTINGS -> {
                    SettingsScreen(
                        preference = preference,
                        coarseLocationEnabled = coarseLocationEnabled,
                        onManagePreferencesClick = onManagePreferenceClick,
                        onManageInterestsClick = onManageInterestsClick,
                        onPrivacyCenterClick = onPrivacyCenterClick,
                        onSafetyCenterClick = onSafetyCenterClick,
                        onToggleCoarseLocation = onToggleCoarseLocation
                    )
                }
            }
        }

        // Native Mobile Bottom Navigation Bar (Optimized for one-handed thumb reach)
        ShadowBottomNavigationBar(
            currentTab = currentTab,
            onTabSelected = onTabSelected,
            modifier = Modifier.navigationBarsPadding()
        )
    }
}

@Composable
private fun ShadowBottomNavigationBar(
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp),
        color = ShadowCard,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 1.dp, color = ShadowCardBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BottomNavItem(
                    label = "Home",
                    iconFilled = Icons.Default.Home,
                    iconOutlined = Icons.Outlined.Home,
                    isSelected = currentTab == BottomTab.HOME,
                    onClick = { onTabSelected(BottomTab.HOME) },
                    testTag = "nav_home"
                )

                BottomNavItem(
                    label = "Chats",
                    iconFilled = Icons.Default.Forum,
                    iconOutlined = Icons.Outlined.ChatBubbleOutline,
                    isSelected = currentTab == BottomTab.CHATS,
                    onClick = { onTabSelected(BottomTab.CHATS) },
                    testTag = "nav_chats"
                )

                BottomNavItem(
                    label = "Nearby",
                    iconFilled = Icons.Default.NearMe,
                    iconOutlined = Icons.Outlined.NearMe,
                    isSelected = currentTab == BottomTab.NEARBY,
                    onClick = { onTabSelected(BottomTab.NEARBY) },
                    testTag = "nav_nearby"
                )

                BottomNavItem(
                    label = "Settings",
                    iconFilled = Icons.Default.Settings,
                    iconOutlined = Icons.Outlined.Settings,
                    isSelected = currentTab == BottomTab.SETTINGS,
                    onClick = { onTabSelected(BottomTab.SETTINGS) },
                    testTag = "nav_settings"
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    iconFilled: ImageVector,
    iconOutlined: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .testTag(testTag)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(if (isSelected) Color(0xFF261D40) else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) iconFilled else iconOutlined,
                contentDescription = label,
                tint = if (isSelected) ShadowAccentCyan else ShadowTextMuted,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) ShadowTextPrimary else ShadowTextMuted
            )
        )
    }
}
