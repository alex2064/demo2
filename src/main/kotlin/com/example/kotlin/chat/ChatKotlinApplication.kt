package com.example.kotlin.chat

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@SpringBootApplication
class ChatKotlinApplication

fun main(args: Array<String>) {
	runApplication<ChatKotlinApplication>(*args)
}


/**
 * 초기 DB 셋팅
 *
 * ConnectionFactory
 * 		- r2dbc 연결 팩토리
 */
@Configuration
class Config {

	@Bean
	fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
		// 초기화될때 채우고 구성요소가 종료될 때 정리할 DB에 대한 연결 팩토리
		val initializer = ConnectionFactoryInitializer()
		initializer.setConnectionFactory(connectionFactory)

		// DB에 기본 셋팅
		val populator = CompositeDatabasePopulator()
		populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("./sql/schema.sql")))

		initializer.setDatabasePopulator(populator)

		return initializer
	}
}