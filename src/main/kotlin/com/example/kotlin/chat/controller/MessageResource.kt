package com.example.kotlin.chat.controller

import com.example.kotlin.chat.service.MessageService
import com.example.kotlin.chat.dto.MessageDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.onStart
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * @MessageMapping
 *      - client에서 send 요청을 할 수 있는 경로
 *
 * suspend
 *      - 코루틴
 *
 * Flow(0~N개의 데이터 전달), Mono(0~1개의 데이터 전달)
 *      - Spring webflux에서 사용하는 Reactor 객체
 *      - Reactor는 Reactive Streams의 구현체
 */
@Controller
@MessageMapping("api.v1.messages")
class MessageResource(
    val messageService: MessageService
) {

    @MessageMapping("stream")
    suspend fun receive(@Payload inboundMessages: Flow<MessageDto>) =
        messageService.post(inboundMessages)

    @MessageMapping("stream")
    fun send(): Flow<MessageDto> =
        messageService.stream()
            .onStart { emitAll(messageService.latest()) }
}