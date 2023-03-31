package com.example.kotlin.chat.extension

import com.example.kotlin.chat.entity.ContentType
import com.example.kotlin.chat.entity.Message
import com.example.kotlin.chat.dto.MessageDto
import com.example.kotlin.chat.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import java.net.URL

/**
 * 확장함수에서는 this로 해당 객체를 부르고 this.은 생략 가능
 *
 * MessageDto -> user -> name 접근은 코틀린에서는 this.user.name으로 접근
 */

fun MessageDto.asDomainObject(contentType: ContentType = ContentType.MARKDOWN): Message =
    Message(content, contentType, sent, user.name, user.avatarImageLink.toString(), id)

fun Message.asViewModel(): MessageDto =
    MessageDto(content, UserDto(username, URL(userAvatarImageLink)), sent, id)

fun List<Message>.mapToViewModel(): List<MessageDto> =
    this.map { m -> m.asViewModel() }

fun ContentType.render(content: String): String =
    when (this) {
        ContentType.PLAIN -> content
        ContentType.MARKDOWN -> {
            val flavour = CommonMarkFlavourDescriptor()
            HtmlGenerator(content, MarkdownParser(flavour).buildMarkdownTreeFromString(content), flavour).generateHtml()
        }
    }

fun Flow<Message>.mapToViewModel(): Flow<MessageDto> =
    this.map { m -> m.asViewModel() }

/**
 * this.copy
 *      - 객체가 수정이 불가능 할때 copy를 하면서 값을 변경하고 새로 만듦
 */

fun MessageDto.asRendered(contentType: ContentType = ContentType.MARKDOWN): MessageDto =
    this.copy(content = contentType.render(this.content))