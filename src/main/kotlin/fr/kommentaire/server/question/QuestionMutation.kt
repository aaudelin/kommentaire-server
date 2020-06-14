package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component

@Component
class QuestionMutation(
        val questionRepository: QuestionRepository
) : Mutation {

    fun createQuestion(content: String) : Question {
        val question = Question(0, content, 0)
        val questionID = questionRepository.insertQuestion(question)
        return question.copy(id = questionID ?: 0)
    }

    fun upvoteQuestion(questionId: Int): Question? {
        return questionRepository.updateQuestionVote(questionId, 1)
    }

    fun downvoteQuestion(questionId: Int): Question? {
        return questionRepository.updateQuestionVote(questionId, -1)
    }

}