package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Mutation
import fr.kommentaire.server.authentication.AuthenticationDirective
import org.springframework.stereotype.Component

@Component
class QuestionMutation(
        val questionRepository: QuestionRepository
) : Mutation {

    @AuthenticationDirective
    fun createQuestion(content: String) : Question {
        val question = Question(0, content, 0)
        val questionID = questionRepository.insertQuestion(question)
        return question.copy(id = questionID ?: 0)
    }

    @AuthenticationDirective
    fun upvoteQuestion(questionId: Int): Question? {
        return questionRepository.updateQuestionVote(questionId, 1)
    }

    @AuthenticationDirective
    fun downvoteQuestion(questionId: Int): Question? {
        return questionRepository.updateQuestionVote(questionId, -1)
    }

}