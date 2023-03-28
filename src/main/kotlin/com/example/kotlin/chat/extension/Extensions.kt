package com.example.kotlin.chat.extension

import com.example.kotlin.chat.entity.ContentType
import com.example.kotlin.chat.entity.Message
import com.example.kotlin.chat.dto.MessageVM
import com.example.kotlin.chat.dto.UserVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import java.net.URL

/**
 * 확장함수에서는 this로 해당 객체를 부르고 this.은 생략 가능
 *
 * MessageVM -> user -> name 접근은 코틀린에서는 this.user.name으로 접근
 */

fun MessageVM.asDomainObject(contentType: ContentType = ContentType.MARKDOWN): Message =
    Message(content, contentType, sent, user.name, user.avatarImageLink.toString(), id)

fun Message.asViewModel(): MessageVM =
    MessageVM(content, UserVM(username, URL(userAvatarImageLink)), sent, id)

fun List<Message>.mapToViewModel(): List<MessageVM> =
    this.map { m -> m.asViewModel() }

fun ContentType.render(content: String): String =
    when (this) {
        ContentType.PLAIN -> content
        ContentType.MARKDOWN -> {
            val flavour = CommonMarkFlavourDescriptor()
            HtmlGenerator(content, MarkdownParser(flavour).buildMarkdownTreeFromString(content), flavour).generateHtml()
        }
    }

fun Flow<Message>.mapToViewModel(): Flow<MessageVM> =
    this.map { m -> m.asViewModel() }

/**
 * this.copy
 *      - 객체가 수정이 불가능 할때 copy를 하면서 값을 변경하고 새로 만듦
 */

fun MessageVM.asRendered(contentType: ContentType = ContentType.MARKDOWN): MessageVM =
    this.copy(content = contentType.render(this.content))