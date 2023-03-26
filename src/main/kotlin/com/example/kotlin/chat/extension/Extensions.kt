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

fun Flow<Message>.mapToViewModel(): Flow<MessageVM> = map { m -> m.asViewModel() }

fun MessageVM.asRendered(contentType: ContentType = ContentType.MARKDOWN): MessageVM =
    this.copy(content = contentType.render(this.content))