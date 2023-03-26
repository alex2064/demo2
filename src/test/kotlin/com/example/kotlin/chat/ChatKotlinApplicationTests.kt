package com.example.kotlin.chat

import com.example.kotlin.chat.repository.MessageRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import java.time.Instant

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = [
		"spring.datasource.url=jdbc:h2:mem:testdb"
	]
)
class ChatKotlinApplicationTests {

	@Autowired
	lateinit var client: TestRestTemplate

	@Autowired
	lateinit var messageRepository: MessageRepository

	lateinit var lastMessageId: String

	val now: Instant = Instant.now()
}

