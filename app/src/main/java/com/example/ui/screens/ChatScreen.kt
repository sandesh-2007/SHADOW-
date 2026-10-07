package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatMessage
import com.example.model.NearbyShadow
import com.example.model.ReportReason
import com.example.model.ShadowConversation
import com.example.ui.components.PrivacyBadge
import com.example.ui.components.ShadowAvatar
import com.example.ui.components.ShadowDangerButton
import com.example.ui.components.ShadowGradientButton
import com.example.ui.components.ShadowOutlinedButton
import com.example.ui.theme.ShadowAccentCyan
import com.example.ui.theme.ShadowAccentViolet
import com.example.ui.theme.ShadowBackground
import com.example.ui.theme.ShadowCard
import com.example.ui.theme.ShadowCardBorder
import com.example.ui.theme.ShadowError
import com.example.ui.theme.ShadowPrimaryGradient
import com.example.ui.theme.ShadowSuccess
import com.example.ui.theme.ShadowTextMuted
import com.example.ui.theme.ShadowTextPrimary
import com.example.ui.theme.ShadowTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    conversationId: String,
    conversation: ShadowConversation?,
    peer: NearbyShadow?,
    messages: List<ChatMessage>,
    onSendMessage: (text: String, emoji: String?, label: String?, isVoice: Boolean, voiceSec: Int) -> Unit,
    onNextPerson: () -> Unit,
    onEndChat: () -> Unit,
    onBlockUser: (String) -> Unit,
    onReportUser: (String, ReportReason, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var showEmojiPicker by remember { mutableStateOf(false) }
    var showAttachmentPicker by remember { mutableStateOf(false) }
    var showOptionsMenu by remember { mutableStateOf(false) }

    // Dialog & Sheets State
    var showNextPersonSheet by remember { mutableStateOf(false) }
    var showEndChatDialog by remember { mutableStateOf(false) }
    var showReportBlockSheet by remember { mutableStateOf(false) }
    var selectedReportReason by remember { mutableStateOf(ReportReason.INAPPROPRIATE) }

    val listState = rememberLazyListState()

    // Auto-scroll when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val peerName = conversation?.peerName ?: peer?.displayName ?: "Shadow-Peer"
    val peerEmoji = conversation?.peerEmoji ?: peer?.emoji ?: "🌑"
    val peerGradient = conversation?.peerGradientIndex ?: peer?.gradientIndex ?: 0
    val minutesLeft = conversation?.minutesLeft ?: 30
    val peerId = peer?.id ?: conversationId.removePrefix("conv-")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ShadowBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Chat Top Bar
            ChatHeader(
                peerName = peerName,
                peerEmoji = peerEmoji,
                peerGradientIndex = peerGradient,
                minutesLeft = minutesLeft,
                onBackClick = onBackClick,
                onNextPersonClick = { showNextPersonSheet = true },
                onOptionsClick = { showOptionsMenu = true }
            )

            // Privacy status banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F121C))
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                PrivacyBadge(text = "Both users are anonymous • Ephemeral session")
            }

            // Messages Stream
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp)
            ) {
                items(messages, key = { it.id }) { message ->
                    ChatMessageBubble(message = message)
                }
            }

            // Quick Emojis Drawer
            AnimatedVisibility(
                visible = showEmojiPicker,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                QuickEmojiDrawer(
                    onEmojiSelected = { emoji ->
                        inputText += emoji
                    }
                )
            }

            // Quick Attachments Drawer (Campus Vibe Shots)
            AnimatedVisibility(
                visible = showAttachmentPicker,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                CampusAttachmentDrawer(
                    onAttachmentSelected = { emoji, label ->
                        onSendMessage("", emoji, label, false, 0)
                        showAttachmentPicker = false
                    }
                )
            }

            // Bottom Input Bar
            ChatInputBar(
                inputText = inputText,
                onInputTextChange = { inputText = it },
                onSend = {
                    if (inputText.isNotBlank()) {
                        onSendMessage(inputText, null, null, false, 0)
                        inputText = ""
                    }
                },
                onToggleEmoji = {
                    showEmojiPicker = !showEmojiPicker
                    showAttachmentPicker = false
                },
                onToggleAttachment = {
                    showAttachmentPicker = !showAttachmentPicker
                    showEmojiPicker = false
                },
                onSendVoice = {
                    onSendMessage("", null, null, true, 4)
                }
            )
        }

        // Dropdown Menu for Top Actions
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 56.dp, end = 16.dp)
        ) {
            DropdownMenu(
                expanded = showOptionsMenu,
                onDismissRequest = { showOptionsMenu = false },
                modifier = Modifier
                    .background(ShadowCard)
                    .border(1.dp, ShadowCardBorder, RoundedCornerShape(12.dp))
            ) {
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FastForward,
                                contentDescription = null,
                                tint = ShadowAccentCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Next Person", color = ShadowTextPrimary)
                        }
                    },
                    onClick = {
                        showOptionsMenu = false
                        showNextPersonSheet = true
                    }
                )
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PowerSettingsNew,
                                contentDescription = null,
                                tint = ShadowTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("End Chat", color = ShadowTextPrimary)
                        }
                    },
                    onClick = {
                        showOptionsMenu = false
                        showEndChatDialog = true
                    }
                )
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Report,
                                contentDescription = null,
                                tint = ShadowError,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Report / Block", color = ShadowError)
                        }
                    },
                    onClick = {
                        showOptionsMenu = false
                        showReportBlockSheet = true
                    }
                )
            }
        }

        // NEXT PERSON BOTTOM SHEET (11. NEXT PERSON)
        if (showNextPersonSheet) {
            ModalBottomSheet(
                onDismissRequest = { showNextPersonSheet = false },
                containerColor = ShadowCard,
                sheetState = rememberModalBottomSheetState()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Leave this conversation?",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShadowTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "You will be disconnected from $peerName and instantly matched with another available shadow.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ShadowTextSecondary
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    ShadowGradientButton(
                        text = "NEXT PERSON",
                        onClick = {
                            showNextPersonSheet = false
                            onNextPerson()
                        },
                        leadingIcon = Icons.Default.FastForward,
                        testTag = "confirm_next_person_button"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ShadowOutlinedButton(
                        text = "STAY",
                        onClick = { showNextPersonSheet = false },
                        testTag = "stay_button"
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // END CHAT CONFIRMATION DIALOG
        if (showEndChatDialog) {
            AlertDialog(
                onDismissRequest = { showEndChatDialog = false },
                containerColor = ShadowCard,
                title = {
                    Text(
                        text = "End Conversation",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShadowTextPrimary
                        )
                    )
                },
                text = {
                    Text(
                        text = "This will permanently delete messages and close the connection with $peerName.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ShadowTextSecondary
                        )
                    )
                },
                confirmButton = {
                    ShadowDangerButton(
                        text = "END CHAT",
                        onClick = {
                            showEndChatDialog = false
                            onEndChat()
                        },
                        modifier = Modifier.width(120.dp),
                        testTag = "confirm_end_chat_button"
                    )
                },
                dismissButton = {
                    ShadowOutlinedButton(
                        text = "CANCEL",
                        onClick = { showEndChatDialog = false },
                        modifier = Modifier.width(100.dp)
                    )
                }
            )
        }

        // REPORT / BLOCK SHEET (15. REPORT / BLOCK)
        if (showReportBlockSheet) {
            ModalBottomSheet(
                onDismissRequest = { showReportBlockSheet = false },
                containerColor = ShadowCard,
                sheetState = rememberModalBottomSheetState()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = ShadowError,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Safety & Protection",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShadowTextPrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Select a reason to report $peerName to Campus Trust & Safety:",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ShadowTextSecondary
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Report reasons
                    ReportReason.values().forEach { reason ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedReportReason = reason }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedReportReason == reason,
                                onClick = { selectedReportReason = reason },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = ShadowError,
                                    unselectedColor = ShadowCardBorder
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = reason.title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = ShadowTextPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = reason.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = ShadowTextMuted
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    ShadowDangerButton(
                        text = "REPORT USER",
                        onClick = {
                            showReportBlockSheet = false
                            onReportUser(peerId, selectedReportReason, "Submitted via chat safety")
                        },
                        testTag = "report_user_button"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ShadowOutlinedButton(
                        text = "BLOCK USER",
                        onClick = {
                            showReportBlockSheet = false
                            onBlockUser(peerId)
                        },
                        leadingIcon = Icons.Default.Block,
                        testTag = "block_user_button"
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ChatHeader(
    peerName: String,
    peerEmoji: String,
    peerGradientIndex: Int,
    minutesLeft: Int,
    onBackClick: () -> Unit,
    onNextPersonClick: () -> Unit,
    onOptionsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(ShadowCard)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = ShadowTextPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            ShadowAvatar(
                emoji = peerEmoji,
                gradientIndex = peerGradientIndex,
                size = 42.dp
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = peerName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ShadowTextPrimary
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(ShadowSuccess)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Active now",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowSuccess,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Subtle timer in chat header (12. TEMPORARY CHAT)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF221A33))
                    .border(1.dp, Color(0xFF382959), RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = ShadowAccentViolet,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "⏳ ${minutesLeft}m",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShadowAccentViolet,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            IconButton(
                onClick = onNextPersonClick,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1E2436))
                    .testTag("chat_next_person_icon")
            ) {
                Icon(
                    imageVector = Icons.Default.FastForward,
                    contentDescription = "Next Person",
                    tint = ShadowAccentCyan,
                    modifier = Modifier.size(18.dp)
                )
            }

            IconButton(
                onClick = onOptionsClick,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    tint = ShadowTextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ChatMessageBubble(message: ChatMessage) {
    val isMine = message.isMine
    val bubbleShape = if (isMine) {
        RoundedCornerShape(topStart = 18.dp, topEnd = 4.dp, bottomStart = 18.dp, bottomEnd = 18.dp)
    } else {
        RoundedCornerShape(topStart = 4.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 18.dp)
    }

    val bubbleBackground = if (isMine) {
        ShadowPrimaryGradient
    } else {
        Brush.linearGradient(listOf(ShadowCard, ShadowCard))
    }

    val textColor = if (isMine) Color.White else ShadowTextPrimary

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(bubbleShape)
                .background(bubbleBackground)
                .border(
                    width = if (isMine) 0.dp else 1.dp,
                    color = if (isMine) Color.Transparent else ShadowCardBorder,
                    shape = bubbleShape
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column {
                // If attachment
                if (message.attachmentLabel != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0x33000000))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(text = message.attachmentEmoji ?: "📷", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = message.attachmentLabel,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                    if (message.text.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                // If voice message
                if (message.isVoice) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Voice Note (${message.voiceDurationSec}s)",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                    }
                } else if (message.text.isNotBlank()) {
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = textColor,
                            lineHeight = 22.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message.formattedTime,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (isMine) Color(0xCCFFFFFF) else ShadowTextMuted,
                        fontSize = 10.sp
                    ),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
private fun ChatInputBar(
    inputText: String,
    onInputTextChange: (String) -> Unit,
    onSend: () -> Unit,
    onToggleEmoji: () -> Unit,
    onToggleAttachment: () -> Unit,
    onSendVoice: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ShadowCard)
            .border(1.dp, ShadowCardBorder)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Photo / Attachment icon
            IconButton(
                onClick = onToggleAttachment,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddPhotoAlternate,
                    contentDescription = "Attach Campus Vibe",
                    tint = ShadowAccentCyan,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Emoji icon
            IconButton(
                onClick = onToggleEmoji,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SentimentSatisfiedAlt,
                    contentDescription = "Emojis",
                    tint = ShadowTextSecondary,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Text Input field
            TextField(
                value = inputText,
                onValueChange = onInputTextChange,
                placeholder = {
                    Text(
                        text = "Say something in the shadow...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ShadowTextMuted
                        )
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF111420),
                    unfocusedContainerColor = Color(0xFF111420),
                    focusedTextColor = ShadowTextPrimary,
                    unfocusedTextColor = ShadowTextPrimary,
                    cursorColor = ShadowAccentCyan,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("chat_input_field")
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Voice or Send Button
            if (inputText.isBlank()) {
                IconButton(
                    onClick = onSendVoice,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF222638))
                        .testTag("voice_message_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Message",
                        tint = ShadowAccentViolet,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                IconButton(
                    onClick = onSend,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(ShadowPrimaryGradient)
                        .testTag("send_message_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickEmojiDrawer(onEmojiSelected: (String) -> Unit) {
    val emojis = listOf("☕", "🎮", "💻", "🍕", "📚", "🎧", "⚡", "🌑", "🔥", "✨", "😂", "👀")
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF141724))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(emojis) { emoji ->
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFF1F2436))
                    .clickable { onEmojiSelected(emoji) }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 20.sp)
            }
        }
    }
}

@Composable
private fun CampusAttachmentDrawer(onAttachmentSelected: (String, String) -> Unit) {
    val moments = listOf(
        Pair("🏛️", "Campus Quad"),
        Pair("☕", "Midnight Coffee"),
        Pair("📚", "Library Floor 3"),
        Pair("💻", "Lab Coding"),
        Pair("🍕", "Dorm Snack")
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF141724))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(moments) { moment ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E2336))
                    .border(1.dp, Color(0xFF2C334D), RoundedCornerShape(12.dp))
                    .clickable { onAttachmentSelected(moment.first, moment.second) }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = moment.first, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = moment.second,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShadowTextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}
