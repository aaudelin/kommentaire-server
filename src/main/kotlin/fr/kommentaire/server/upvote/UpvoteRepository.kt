package fr.kommentaire.server.upvote

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class UpvoteRepository(val jdbcTemplate: JdbcTemplate) {

    fun upvoteQuestion(questionId: Int, userId: Int): Int? {
        return jdbcTemplate.update("INSERT INTO USER_UPVOTE VALUES ($userId, $questionId, '${UpvoteType.UPVOTE.name}')")
    }

    fun downvoteQuestion(questionId: Int, userId: Int): Int? {
        return jdbcTemplate.update("INSERT INTO USER_UPVOTE VALUES ($userId, $questionId, '${UpvoteType.DOWNVOTE.name}')")
    }

    fun cancelVote(questionId: Int, userId: Int): Int? {
        return jdbcTemplate.update("DELETE FROM USER_UPVOTE WHERE USERID=$userId AND QUESTIONID=$questionId")
    }

    fun findQuestionUserVote(questionId: Int, userId: Int): Upvote? {
        return jdbcTemplate.query("SELECT * FROM USER_UPVOTE WHERE USERID=$userId AND QUESTIONID=$questionId") { rs, rowNum ->
            val upvoteType = if (rs.getString(3) == "UPVOTE") UpvoteType.UPVOTE else UpvoteType.DOWNVOTE
            Upvote(rs.getInt(1), rs.getInt(2), upvoteType)
        }.getOrNull(0)
    }

    fun findQuestionVotes(questionId: Int): List<Upvote> {
        return jdbcTemplate.query("SELECT * FROM USER_UPVOTE WHERE QUESTIONID=$questionId") { rs, rowNum ->
            val upvoteType = if (rs.getString(3) == "UPVOTE") UpvoteType.UPVOTE else UpvoteType.DOWNVOTE
            Upvote(rs.getInt(1), rs.getInt(2), upvoteType)
        }
    }


}