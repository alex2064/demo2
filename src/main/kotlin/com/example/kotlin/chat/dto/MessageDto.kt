package com.example.kotlin.chat.dto

import java.net.URL

data class MessageDto(
    val content: String,
    val user: UserDto,
    val sent: String,
    val id: String? = null
)

data class UserDto(
    val name: String,
    val avatarImageLink: URL
)