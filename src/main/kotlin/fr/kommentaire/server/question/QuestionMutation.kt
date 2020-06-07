package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component

@Component
class QuestionMutation : Mutation {

    fun createQuestion(content: String) : Question {
        return Question(0, content, 0)
    }

    fun upvoteQuestion(): Question {
        return Question(0, "UPVOTE", 1)
    }

    fun downvoteQuestion(): Question {
        return Question(0, "DOWNVOTE", -1)
    }

}