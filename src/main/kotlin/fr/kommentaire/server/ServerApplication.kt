package fr.kommentaire.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}

@Configuration
class Configuration {

	@Bean
	fun questionJdbcInsert(jdbcTemplate: JdbcTemplate) : SimpleJdbcInsert {
		return SimpleJdbcInsert(jdbcTemplate).withTableName("QUESTION").usingGeneratedKeyColumns("id")
	}
}
