package com.example.kotlin.chat.dto

import java.net.URL

data class MessageVM(
    val content: String,
    val user: UserVM,
    val sent: String,
    val id: String? = null
)

data class UserVM(
    val name: String,
    val avatarImageLink: URL
)