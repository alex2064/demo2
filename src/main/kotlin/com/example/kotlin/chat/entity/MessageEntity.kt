package com.example.kotlin.chat.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("MESSAGES")
data class Message(
    val content: String,
    val contentType: ContentType,
    val sent: String,
    val username: String,
    val userAvatarImageLink: String,
    @Id var id: String? = null
)

enum class ContentType {
    PLAIN,
    MARKDOWN
}