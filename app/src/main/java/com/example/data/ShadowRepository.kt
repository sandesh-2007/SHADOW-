package com.example.data

import com.example.model.AnonymousIdentity
import com.example.model.AvailableInterests
import com.example.model.ChatDuration
import com.example.model.ChatMessage
import com.example.model.ConnectionPreference
import com.example.model.Interest
import com.example.model.NearbyShadow
import com.example.model.ReportReason
import com.example.model.ShadowConversation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class ShadowRepository(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    // 1. Current User Anonymous Identity
    private val _myIdentity = MutableStateFlow(
        AnonymousIdentity(
            code = 4821,
            emoji = "🌑",
            collegeCampus = "Campus Node Alpha",
            gradientIndex = 0
        )
    )
    val myIdentity: StateFlow<AnonymousIdentity> = _myIdentity.asStateFlow()

    // 2. Selected Interests
    private val _selectedInterests = MutableStateFlow(
        listOf(
            AvailableInterests.first { it.id == "gaming" },
            AvailableInterests.first { it.id == "coding" },
            AvailableInterests.first { it.id == "music" },
            AvailableInterests.first { it.id == "memes" }
        )
    )
    val selectedInterests: StateFlow<List<Interest>> = _selectedInterests.asStateFlow()

    // 3. Connection Preference
    private val _connectionPreference = MutableStateFlow(ConnectionPreference.ANYONE)
    val connectionPreference: StateFlow<ConnectionPreference> = _connectionPreference.asStateFlow()

    // 4. Default Duration Preference
    private val _defaultDuration = MutableStateFlow(ChatDuration.THIRTY_MINUTES)
    val defaultDuration: StateFlow<ChatDuration> = _defaultDuration.asStateFlow()

    // 5. Coarse Location Active
    private val _coarseLocationEnabled = MutableStateFlow(true)
    val coarseLocationEnabled: StateFlow<Boolean> = _coarseLocationEnabled.asStateFlow()

    // 6. Blocked IDs
    private val _blockedUserIds = MutableStateFlow<Set<String>>(emptySet())
    val blockedUserIds: StateFlow<Set<String>> = _blockedUserIds.asStateFlow()

    // 7. Nearby Shadows
    private val initialNearby = listOf(
        NearbyShadow(
            id = "shadow-2841",
            displayName = "Shadow-2841",
            emoji = "🌑",
            approximateDistance = "Nearby",
            interests = listOf(
                AvailableInterests.first { it.id == "gaming" },
                AvailableInterests.first { it.id == "music" }
            ),
            gradientIndex = 1
        ),
        NearbyShadow(
            id = "shadow-7319",
            displayName = "Shadow-7319",
            emoji = "🌌",
            approximateDistance = "Within 2 km",
            interests = listOf(
                AvailableInterests.first { it.id == "coding" },
                AvailableInterests.first { it.id == "study" }
            ),
            gradientIndex = 2
        ),
        NearbyShadow(
            id = "shadow-9104",
            displayName = "Shadow-9104",
            emoji = "🌒",
            approximateDistance = "Within 2 km",
            interests = listOf(
                AvailableInterests.first { it.id == "movies" },
                AvailableInterests.first { it.id == "just_talk" }
            ),
            gradientIndex = 3
        ),
        NearbyShadow(
            id = "shadow-5520",
            displayName = "Shadow-5520",
            emoji = "🌘",
            approximateDistance = "Within 5 km",
            interests = listOf(
                AvailableInterests.first { it.id == "sports" },
                AvailableInterests.first { it.id == "travel" }
            ),
            gradientIndex = 4
        ),
        NearbyShadow(
            id = "shadow-1940",
            displayName = "Shadow-1940",
            emoji = "🪐",
            approximateDistance = "Within 5 km",
            interests = listOf(
                AvailableInterests.first { it.id == "technology" },
                AvailableInterests.first { it.id == "memes" }
            ),
            gradientIndex = 5
        )
    )

    private val _nearbyShadows = MutableStateFlow(initialNearby)
    val nearbyShadows: StateFlow<List<NearbyShadow>> = _nearbyShadows.asStateFlow()

    // Flag to test empty state for nearby users
    private val _simulateNoNearby = MutableStateFlow(false)
    val simulateNoNearby: StateFlow<Boolean> = _simulateNoNearby.asStateFlow()

    // 8. Active Conversations
    private val _conversations = MutableStateFlow(
        listOf(
            ShadowConversation(
                id = "conv-shadow-2841",
                peerName = "Shadow-2841",
                peerEmoji = "🌑",
                peerGradientIndex = 1,
                lastMessage = "Are you into gaming?",
                timestamp = "12:42 AM",
                duration = ChatDuration.THIRTY_MINUTES,
                minutesLeft = 8,
                approximateDistance = "Nearby",
                sharedInterests = listOf(
                    AvailableInterests.first { it.id == "gaming" },
                    AvailableInterests.first { it.id == "music" }
                ),
                unreadCount = 1
            ),
            ShadowConversation(
                id = "conv-shadow-7392",
                peerName = "Shadow-7392",
                peerEmoji = "🌒",
                peerGradientIndex = 2,
                lastMessage = "That project sounds interesting.",
                timestamp = "11:18 PM",
                duration = ChatDuration.ONE_HOUR,
                minutesLeft = 24,
                approximateDistance = "Within 2 km",
                sharedInterests = listOf(
                    AvailableInterests.first { it.id == "coding" }
                ),
                unreadCount = 0
            )
        )
    )
    val conversations: StateFlow<List<ShadowConversation>> = _conversations.asStateFlow()

    // 9. Chat Messages per conversation ID
    private val _messages = MutableStateFlow<Map<String, List<ChatMessage>>>(
        mapOf(
            "conv-shadow-2841" to listOf(
                ChatMessage(
                    conversationId = "conv-shadow-2841",
                    text = "Hey! Saw we both share gaming & music on campus.",
                    formattedTime = "12:39 AM",
                    isMine = false
                ),
                ChatMessage(
                    conversationId = "conv-shadow-2841",
                    text = "Nice! Yeah, taking a break from assignments right now haha",
                    formattedTime = "12:40 AM",
                    isMine = true
                ),
                ChatMessage(
                    conversationId = "conv-shadow-2841",
                    text = "Are you into gaming?",
                    formattedTime = "12:42 AM",
                    isMine = false
                )
            ),
            "conv-shadow-7392" to listOf(
                ChatMessage(
                    conversationId = "conv-shadow-7392",
                    text = "Hey shadow 👋 Which department are you at?",
                    formattedTime = "11:10 PM",
                    isMine = false
                ),
                ChatMessage(
                    conversationId = "conv-shadow-7392",
                    text = "Building a decentralized system for our final semester lab!",
                    formattedTime = "11:14 PM",
                    isMine = true
                ),
                ChatMessage(
                    conversationId = "conv-shadow-7392",
                    text = "That project sounds interesting.",
                    formattedTime = "11:18 PM",
                    isMine = false
                )
            )
        )
    )
    val messages: StateFlow<Map<String, List<ChatMessage>>> = _messages.asStateFlow()

    // Methods
    fun regenerateIdentity() {
        val nextCode = (1000..9999).random()
        val emojis = listOf("🌑", "🌌", "🌒", "🌘", "🪐", "✨")
        val randomEmoji = emojis.random()
        val randomGrad = (0..5).random()
        _myIdentity.value = _myIdentity.value.copy(
            code = nextCode,
            emoji = randomEmoji,
            gradientIndex = randomGrad
        )
    }

    fun toggleInterest(interest: Interest) {
        val current = _selectedInterests.value.toMutableList()
        if (current.any { it.id == interest.id }) {
            if (current.size > 1) { // keep at least 1
                current.removeAll { it.id == interest.id }
            }
        } else {
            current.add(interest)
        }
        _selectedInterests.value = current
    }

    fun setConnectionPreference(pref: ConnectionPreference) {
        _connectionPreference.value = pref
    }

    fun setDefaultDuration(duration: ChatDuration) {
        _defaultDuration.value = duration
    }

    fun toggleCoarseLocation(enabled: Boolean) {
        _coarseLocationEnabled.value = enabled
    }

    fun toggleSimulateNoNearby(empty: Boolean) {
        _simulateNoNearby.value = empty
        if (empty) {
            _nearbyShadows.value = emptyList()
        } else {
            _nearbyShadows.value = initialNearby.filterNot { _blockedUserIds.value.contains(it.id) }
        }
    }

    fun blockUser(userId: String, convId: String? = null) {
        val updated = _blockedUserIds.value + userId
        _blockedUserIds.value = updated
        _nearbyShadows.value = _nearbyShadows.value.filterNot { it.id == userId }
        if (convId != null) {
            _conversations.value = _conversations.value.filterNot { it.id == convId }
        }
    }

    fun reportUser(userId: String, reason: ReportReason, details: String) {
        // Automatically block reported user
        blockUser(userId)
    }

    fun sendMessage(
        conversationId: String,
        text: String,
        attachmentEmoji: String? = null,
        attachmentLabel: String? = null,
        isVoice: Boolean = false,
        voiceDurationSec: Int = 0
    ) {
        val newMsg = ChatMessage(
            conversationId = conversationId,
            text = text,
            formattedTime = timeFormat.format(Date()),
            isMine = true,
            attachmentEmoji = attachmentEmoji,
            attachmentLabel = attachmentLabel,
            isVoice = isVoice,
            voiceDurationSec = voiceDurationSec
        )

        val currentList = _messages.value[conversationId] ?: emptyList()
        val updatedMap = _messages.value.toMutableMap()
        updatedMap[conversationId] = currentList + newMsg
        _messages.value = updatedMap

        // Update conversation preview
        val previewText = when {
            isVoice -> "🎙️ Voice message (${voiceDurationSec}s)"
            attachmentLabel != null -> "📷 $attachmentLabel"
            else -> text
        }
        _conversations.value = _conversations.value.map {
            if (it.id == conversationId) {
                it.copy(
                    lastMessage = previewText,
                    timestamp = timeFormat.format(Date()),
                    unreadCount = 0
                )
            } else it
        }

        // Trigger simulated peer response after realistic delay
        simulatePeerReply(conversationId, text)
    }

    private fun simulatePeerReply(conversationId: String, userText: String) {
        scope.launch {
            delay(1800)
            val replies = listOf(
                "Haha totally agree! College life is crazy right now ☕",
                "That's so true. Are you chilling at the student center?",
                "Glad we connected on SHADOW! Zero pressure vibes.",
                "Awesome! What year are you in by the way?",
                "Haha 100%! What kind of music or games are you into recently?",
                "Same here. Best part is no one knows who we are haha."
            )
            val replyText = replies.random()
            val replyMsg = ChatMessage(
                conversationId = conversationId,
                text = replyText,
                formattedTime = timeFormat.format(Date()),
                isMine = false
            )
            val currentList = _messages.value[conversationId] ?: emptyList()
            val updatedMap = _messages.value.toMutableMap()
            updatedMap[conversationId] = currentList + replyMsg
            _messages.value = updatedMap

            _conversations.value = _conversations.value.map {
                if (it.id == conversationId) {
                    it.copy(
                        lastMessage = replyText,
                        timestamp = timeFormat.format(Date()),
                        unreadCount = 0
                    )
                } else it
            }
        }
    }

    fun startConversationWith(
        peer: NearbyShadow,
        duration: ChatDuration = _defaultDuration.value
    ): String {
        val existing = _conversations.value.firstOrNull { it.id.contains(peer.id) }
        if (existing != null) return existing.id

        val convId = "conv-${peer.id}"
        val initialMinutes = if (duration.minutes > 0) duration.minutes else 60
        val newConv = ShadowConversation(
            id = convId,
            peerName = peer.displayName,
            peerEmoji = peer.emoji,
            peerGradientIndex = peer.gradientIndex,
            lastMessage = "Connected in the shadows",
            timestamp = "Just now",
            duration = duration,
            minutesLeft = initialMinutes,
            approximateDistance = peer.approximateDistance,
            sharedInterests = peer.interests,
            unreadCount = 0
        )
        _conversations.value = listOf(newConv) + _conversations.value
        _messages.value = _messages.value + (convId to listOf(
            ChatMessage(
                conversationId = convId,
                text = "Connected anonymously via ${peer.approximateDistance}. Say hi!",
                formattedTime = timeFormat.format(Date()),
                isMine = false
            )
        ))
        return convId
    }

    fun endConversation(conversationId: String) {
        _conversations.value = _conversations.value.filterNot { it.id == conversationId }
        val updated = _messages.value.toMutableMap()
        updated.remove(conversationId)
        _messages.value = updated
    }
}
