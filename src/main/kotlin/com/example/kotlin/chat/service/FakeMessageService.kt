package com.example.kotlin.chat.service

import com.example.kotlin.chat.dto.MessageDto
import com.example.kotlin.chat.dto.UserDto
import com.github.javafaker.Faker
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.random.Random

@Service
class FakeMessageService {

    val users: Map<String, UserDto> = mapOf(
        "Shakespeare"  to UserDto("Shakespeare", URL("https://blog.12min.com/wp-content/uploads/2018/05/27d-William-Shakespeare.jpg")),
        "RickAndMorty" to UserDto("RickAndMorty", URL("http://thecircular.org/wp-content/uploads/2015/04/rick-and-morty-fb-pic1.jpg")),
        "Yoda"         to UserDto("Yoda", URL("https://news.toyark.com/wp-content/uploads/sites/4/2019/03/SH-Figuarts-Yoda-001.jpg"))
    )

    val usersQuotes: Map<String, () -> String> = mapOf(
        "Shakespeare"  to { Faker.instance().shakespeare().asYouLikeItQuote() },
        "RickAndMorty" to { Faker.instance().rickAndMorty().quote() },
        "Yoda"         to { Faker.instance().yoda().quote() }
    )

    fun latest(): List<MessageDto> {
        val count = Random.nextInt(1, 15)
        return (0..count).map {
            val user = users.values.random()
            val userQuote = usersQuotes.getValue(user.name).invoke()

            MessageDto(userQuote, user, "", Random.nextBytes(10).toString())
        }.toList()
    }

    suspend fun after(messageId: String): List<MessageDto> {
        return latest()
    }

    suspend fun post(message: MessageDto) {
        TODO("Not yet implemented")
    }
}
