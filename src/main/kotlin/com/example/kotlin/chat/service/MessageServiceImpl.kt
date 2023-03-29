package com.example.kotlin.chat.service

import com.example.kotlin.chat.dto.MessageVM
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

    val sender: MutableSharedFlow<MessageVM> = MutableSharedFlow()

    override fun latest(): Flow<MessageVM> =
        messageRepository.findLatest().mapToViewModel()

    override fun after(messageId: String): Flow<MessageVM> =
        messageRepository.findLatest(messageId).mapToViewModel()

    override fun stream(): Flow<MessageVM> = sender

    override suspend fun post(messages: Flow<MessageVM>) =
        messages.onEach { m -> sender.emit(m.asRendered()) }
            .map { m -> m.asDomainObject() }
            .let { m -> messageRepository.saveAll(m) }
            .collect()
}