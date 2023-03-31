package com.example.kotlin.chat.service

import com.example.kotlin.chat.dto.MessageDto
import com.example.kotlin.chat.extension.asDomainObject
import com.example.kotlin.chat.extension.asRendered
import com.example.kotlin.chat.extension.mapToViewModel
import com.example.kotlin.chat.repository.MessageRepository
import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(
    val messageRepository: MessageRepository
) : MessageService {

    val sender: MutableSharedFlow<MessageDto> = MutableSharedFlow()

    override fun latest(): Flow<MessageDto> =
        messageRepository.findLatest().mapToViewModel()

    override fun after(messageId: String): Flow<MessageDto> =
        messageRepository.findLatest(messageId).mapToViewModel()

    override fun stream(): Flow<MessageDto> = sender

    override suspend fun post(messages: Flow<MessageDto>) =
        messages.onEach { m -> sender.emit(m.asRendered()) }
            .map { m -> m.asDomainObject() }
            .let { m -> messageRepository.saveAll(m) }
            .collect()
}