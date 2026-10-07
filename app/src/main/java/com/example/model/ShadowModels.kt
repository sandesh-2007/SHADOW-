package com.example.model

import java.util.UUID

data class AnonymousIdentity(
    val id: String = UUID.randomUUID().toString(),
    val code: Int = (1000..9999).random(),
    val emoji: String = listOf("🌑", "🌌", "🌒", "🌘", "🪐", "✨").random(),
    val collegeCampus: String = "Campus Node Alpha",
    val gradientIndex: Int = (0..5).random()
) {
    val displayName: String get() = "Shadow-$code"
    val fullTag: String get() = "$emoji $displayName"
}

data class Interest(
    val id: String,
    val name: String,
    val emoji: String
)

val AvailableInterests = listOf(
    Interest("gaming", "Gaming", "🎮"),
    Interest("coding", "Coding", "💻"),
    Interest("music", "Music", "🎵"),
    Interest("movies", "Movies", "🎬"),
    Interest("study", "Study", "📚"),
    Interest("sports", "Sports", "⚽"),
    Interest("college", "College", "🎓"),
    Interest("travel", "Travel", "✈️"),
    Interest("memes", "Memes", "🐸"),
    Interest("technology", "Technology", "⚡"),
    Interest("just_talk", "Just Talk", "💬")
)

enum class ConnectionPreference(val label: String, val subtitle: String) {
    ANYONE("Anyone", "Connect with anyone on campus"),
    MALE("Male", "Filtered matching preference"),
    FEMALE("Female", "Filtered matching preference")
}

enum class ChatDuration(val label: String, val minutes: Int) {
    TEN_MINUTES("10 minutes", 10),
    THIRTY_MINUTES("30 minutes", 30),
    ONE_HOUR("1 hour", 60),
    UNTIL_CHAT_ENDS("Until chat ends", -1)
}

data class NearbyShadow(
    val id: String,
    val displayName: String,
    val emoji: String,
    val approximateDistance: String,
    val interests: List<Interest>,
    val gradientIndex: Int,
    val isOnline: Boolean = true
) {
    val fullTag: String get() = "$emoji $displayName"
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val conversationId: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val formattedTime: String = "Just now",
    val isMine: Boolean,
    val isVoice: Boolean = false,
    val voiceDurationSec: Int = 0,
    val attachmentEmoji: String? = null,
    val attachmentLabel: String? = null
)

data class ShadowConversation(
    val id: String,
    val peerName: String,
    val peerEmoji: String,
    val peerGradientIndex: Int,
    val lastMessage: String,
    val timestamp: String,
    val duration: ChatDuration = ChatDuration.THIRTY_MINUTES,
    val minutesLeft: Int = 28,
    val approximateDistance: String = "Nearby",
    val sharedInterests: List<Interest> = emptyList(),
    val unreadCount: Int = 0,
    val isOnline: Boolean = true
) {
    val fullTag: String get() = "$peerEmoji $peerName"
}

enum class ReportReason(val title: String, val description: String) {
    HARASSMENT("Harassment", "Bullying, personal attacks, or aggressive behavior"),
    SPAM("Spam", "Commercial links, automated bots, or repeated texts"),
    SCAM("Scam", "Deceptive behavior, financial fraud, or impersonation"),
    INAPPROPRIATE("Inappropriate behavior", "NSFW, offensive language, or unwanted content"),
    OTHER("Other", "Any other violation of college safety guidelines")
}
