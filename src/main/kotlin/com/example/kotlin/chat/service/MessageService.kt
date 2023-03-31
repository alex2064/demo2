package com.example.kotlin.chat.service

import com.example.kotlin.chat.dto.MessageDto
import kotlinx.coroutines.flow.Flow

interface MessageService {

    fun latest(): Flow<MessageDto>

    fun after(messageId: String): Flow<MessageDto>

    fun stream(): Flow<MessageDto>

    suspend fun post(messages: Flow<MessageDto>)
}