package fr.kommentaire.server.question

import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class QuestionRepository(val jdbcTemplate: JdbcTemplate) {
    fun selectAllQuestions(): List<Question> {
        return jdbcTemplate.query("SELECT * FROM question", BeanPropertyRowMapper(Question::class.java))
    }
}