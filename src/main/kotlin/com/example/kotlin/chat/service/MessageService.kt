package com.example.kotlin.chat.service

import com.example.kotlin.chat.dto.MessageVM
import kotlinx.coroutines.flow.Flow

interface MessageService {

    fun latest(): Flow<MessageVM>

    fun after(messageId: String): Flow<MessageVM>

    fun stream(): Flow<MessageVM>

    suspend fun post(messages: Flow<MessageVM>)
}