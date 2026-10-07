package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ShadowRepository
import com.example.model.AnonymousIdentity
import com.example.model.ChatDuration
import com.example.model.ChatMessage
import com.example.model.ConnectionPreference
import com.example.model.Interest
import com.example.model.NearbyShadow
import com.example.model.ReportReason
import com.example.model.ShadowConversation
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class Screen {
    SPLASH,
    WELCOME,
    ANONYMOUS_IDENTITY,
    MAIN,
    INTEREST_SELECTION,
    CONNECTION_PREFERENCE,
    RANDOM_MATCHING,
    MATCH_FOUND,
    CHAT,
    SAFETY_CENTER,
    PRIVACY_CENTER
}

enum class BottomTab {
    HOME,
    CHATS,
    NEARBY,
    SETTINGS
}

class ShadowViewModel(
    private val repository: ShadowRepository = ShadowRepository()
) : ViewModel() {

    // Navigation Stack
    private val _currentScreen = MutableStateFlow(Screen.SPLASH)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _currentTab = MutableStateFlow(BottomTab.HOME)
    val currentTab: StateFlow<BottomTab> = _currentTab.asStateFlow()

    private val screenStack = mutableListOf<Screen>()

    // Repository state delegation
    val myIdentity: StateFlow<AnonymousIdentity> = repository.myIdentity
    val selectedInterests: StateFlow<List<Interest>> = repository.selectedInterests
    val connectionPreference: StateFlow<ConnectionPreference> = repository.connectionPreference
    val defaultDuration: StateFlow<ChatDuration> = repository.defaultDuration
    val coarseLocationEnabled: StateFlow<Boolean> = repository.coarseLocationEnabled
    val nearbyShadows: StateFlow<List<NearbyShadow>> = repository.nearbyShadows
    val simulateNoNearby: StateFlow<Boolean> = repository.simulateNoNearby
    val conversations: StateFlow<List<ShadowConversation>> = repository.conversations
    val messages: StateFlow<Map<String, List<ChatMessage>>> = repository.messages

    // Matching flow state
    private val _isMatching = MutableStateFlow(false)
    val isMatching: StateFlow<Boolean> = _isMatching.asStateFlow()

    private val _matchingStatusText = MutableStateFlow("Searching the shadows...")
    val matchingStatusText: StateFlow<String> = _matchingStatusText.asStateFlow()

    private val _matchedPeer = MutableStateFlow<NearbyShadow?>(null)
    val matchedPeer: StateFlow<NearbyShadow?> = _matchedPeer.asStateFlow()

    private var matchingJob: Job? = null

    // Active Chat State
    private val _activeConversationId = MutableStateFlow<String?>(null)
    val activeConversationId: StateFlow<String?> = _activeConversationId.asStateFlow()

    private val _activeChatPeer = MutableStateFlow<NearbyShadow?>(null)
    val activeChatPeer: StateFlow<NearbyShadow?> = _activeChatPeer.asStateFlow()

    // Temporary chat duration chosen for current match
    private val _selectedMatchDuration = MutableStateFlow(ChatDuration.THIRTY_MINUTES)
    val selectedMatchDuration: StateFlow<ChatDuration> = _selectedMatchDuration.asStateFlow()

    // Feedback Toast / Notification state
    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> = _snackBarMessage.asStateFlow()

    init {
        // Auto-transition from splash screen after 1.8 seconds
        viewModelScope.launch {
            delay(1800)
            if (_currentScreen.value == Screen.SPLASH) {
                navigateTo(Screen.WELCOME)
            }
        }
    }

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        return if (screenStack.isNotEmpty()) {
            val prev = screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = prev
            true
        } else {
            false
        }
    }

    fun selectTab(tab: BottomTab) {
        _currentTab.value = tab
        if (_currentScreen.value != Screen.MAIN) {
            _currentScreen.value = Screen.MAIN
        }
    }

    fun regenerateIdentity() {
        repository.regenerateIdentity()
        showToast("New temporary identity generated")
    }

    fun toggleInterest(interest: Interest) {
        repository.toggleInterest(interest)
    }

    fun setConnectionPreference(preference: ConnectionPreference) {
        repository.setConnectionPreference(preference)
        showToast("Preference set: ${preference.label}")
    }

    fun setDefaultDuration(duration: ChatDuration) {
        repository.setDefaultDuration(duration)
        showToast("Temporary chat timer: ${duration.label}")
    }

    fun setMatchDuration(duration: ChatDuration) {
        _selectedMatchDuration.value = duration
    }

    fun toggleCoarseLocation(enabled: Boolean) {
        repository.toggleCoarseLocation(enabled)
    }

    fun toggleEmptyNearby(empty: Boolean) {
        repository.toggleSimulateNoNearby(empty)
    }

    // RANDOM MATCHING ENGINE
    fun startRandomMatching() {
        matchingJob?.cancel()
        _isMatching.value = true
        _matchingStatusText.value = "Searching the shadows..."
        navigateTo(Screen.RANDOM_MATCHING)

        matchingJob = viewModelScope.launch {
            delay(800)
            _matchingStatusText.value = "Filtering campus node..."
            delay(900)
            val interestsSummary = selectedInterests.value.take(2).joinToString(", ") { it.name }
            _matchingStatusText.value = "Matching vibes: $interestsSummary..."
            delay(1200)

            // Pick a candidate from available or generate fresh anonymous student
            val candidate = nearbyShadows.value.randomOrNull() ?: NearbyShadow(
                id = "shadow-${(1000..9999).random()}",
                displayName = "Shadow-${(1000..9999).random()}",
                emoji = listOf("🌑", "🌌", "🌒", "🪐").random(),
                approximateDistance = "Within 2 km",
                interests = selectedInterests.value.take(2),
                gradientIndex = (1..5).random()
            )

            _matchedPeer.value = candidate
            _isMatching.value = false
            navigateTo(Screen.MATCH_FOUND)
        }
    }

    fun cancelMatching() {
        matchingJob?.cancel()
        _isMatching.value = false
        navigateBack()
    }

    fun startChatWithMatch() {
        val peer = _matchedPeer.value ?: return
        val convId = repository.startConversationWith(peer, _selectedMatchDuration.value)
        openChat(convId, peer)
    }

    fun connectFromNearby(peer: NearbyShadow) {
        _matchedPeer.value = peer
        val convId = repository.startConversationWith(peer, defaultDuration.value)
        openChat(convId, peer)
    }

    fun openChat(conversationId: String, peer: NearbyShadow? = null) {
        _activeConversationId.value = conversationId
        _activeChatPeer.value = peer
        navigateTo(Screen.CHAT)
    }

    fun sendMessage(
        text: String,
        attachmentEmoji: String? = null,
        attachmentLabel: String? = null,
        isVoice: Boolean = false,
        voiceDurationSec: Int = 0
    ) {
        val convId = _activeConversationId.value ?: return
        if (text.isBlank() && attachmentLabel == null && !isVoice) return

        repository.sendMessage(
            conversationId = convId,
            text = text.trim(),
            attachmentEmoji = attachmentEmoji,
            attachmentLabel = attachmentLabel,
            isVoice = isVoice,
            voiceDurationSec = voiceDurationSec
        )
    }

    fun nextPerson() {
        // Leave current chat and trigger fresh matching
        val convId = _activeConversationId.value
        if (convId != null) {
            repository.endConversation(convId)
        }
        _activeConversationId.value = null
        startRandomMatching()
    }

    fun endCurrentChat() {
        val convId = _activeConversationId.value
        if (convId != null) {
            repository.endConversation(convId)
            showToast("Conversation ended")
        }
        _activeConversationId.value = null
        selectTab(BottomTab.CHATS)
        navigateTo(Screen.MAIN)
    }

    fun blockCurrentPeer(peerId: String) {
        val convId = _activeConversationId.value
        repository.blockUser(peerId, convId)
        _activeConversationId.value = null
        showToast("User blocked. They will not match with you again.")
        selectTab(BottomTab.HOME)
        navigateTo(Screen.MAIN)
    }

    fun reportCurrentPeer(peerId: String, reason: ReportReason, details: String) {
        val convId = _activeConversationId.value
        repository.reportUser(peerId, reason, details)
        _activeConversationId.value = null
        showToast("Report submitted to Campus Safety. User blocked.")
        selectTab(BottomTab.HOME)
        navigateTo(Screen.MAIN)
    }

    fun showToast(msg: String) {
        _snackBarMessage.value = msg
    }

    fun clearToast() {
        _snackBarMessage.value = null
    }
}
