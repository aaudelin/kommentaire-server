package fr.kommentaire.server.question

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository


@Repository
class QuestionRepository(val jdbcTemplate: JdbcTemplate, val questionJdbcInsert: SimpleJdbcInsert) {

    fun selectAllQuestions(): List<Question> {
        return jdbcTemplate.query("SELECT * FROM question") { rs, rowNum ->
            Question(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4))
        }
    }

    fun insertQuestion(question: Question): Int? {
        return questionJdbcInsert
                .executeAndReturnKey(MapSqlParameterSource().apply {
                    addValue("content", question.content)
                    addValue("votes", question.votes)
                    addValue("USERID", question.userId)
                }).toInt()
    }

    fun updateQuestionVote(questionId: Int, votes: Int): Question? {
        val questionDB = findQuestion(questionId) ?: return null
        val questionResult = questionDB.copy(votes = questionDB.votes + votes)

        jdbcTemplate.update(
                "UPDATE QUESTION SET VOTES=? WHERE ID=?",
                questionDB.votes + votes,
                questionDB.id
        )
        return questionResult
    }

    fun findQuestion(questionId: Int): Question? {
        return jdbcTemplate.query("SELECT * FROM question WHERE id=$questionId") { rs, rowNum ->
            Question(rs.getInt(1), rs.getString(2), rs.getInt(3))
        }.getOrNull(0)
    }

    fun findQuestionsFromUserId(userId: Int): List<Question> {
        return jdbcTemplate.query("SELECT * FROM question WHERE USERID=$userId") { rs, rowNum ->
            Question(rs.getInt(1), rs.getString(2), rs.getInt(3))
        }
    }

    fun findLastQuestion(): Question? {
        return jdbcTemplate.query("SELECT * FROM question ORDER BY id desc LIMIT 1") { rs, rowNum ->
            Question(rs.getInt(1), rs.getString(2), rs.getInt(3))
        }.getOrNull(0)
    }

}