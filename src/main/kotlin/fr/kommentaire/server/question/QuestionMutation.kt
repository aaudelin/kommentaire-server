package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Mutation
import fr.kommentaire.server.authentication.AuthenticationDirective
import fr.kommentaire.server.authentication.GraphQLHttpContext
import fr.kommentaire.server.upvote.UpvoteRepository
import org.springframework.stereotype.Component

@Component
class QuestionMutation(
        val questionRepository: QuestionRepository,
        val upvoteRepository: UpvoteRepository
) : Mutation {

    @AuthenticationDirective
    fun createQuestion(content: String, graphQLHttpContext: GraphQLHttpContext) : Question {
        val question = Question(0, content, graphQLHttpContext.user?.id ?: 0)
        val questionID = questionRepository.insertQuestion(question)
        return question.copy(id = questionID ?: 0)
    }

    @AuthenticationDirective
    fun upvoteQuestion(questionId: Int, graphQLHttpContext: GraphQLHttpContext): Question? {
        upvoteRepository.cancelVote(questionId, graphQLHttpContext.user!!.id)
        upvoteRepository.upvoteQuestion(questionId, graphQLHttpContext.user!!.id)
        return questionRepository.findQuestion(questionId)
    }

    @AuthenticationDirective
    fun downvoteQuestion(questionId: Int, graphQLHttpContext: GraphQLHttpContext): Question? {
        upvoteRepository.cancelVote(questionId, graphQLHttpContext.user!!.id)
        upvoteRepository.downvoteQuestion(questionId, graphQLHttpContext.user!!.id)
        return questionRepository.findQuestion(questionId)
    }

    @AuthenticationDirective
    fun cancelQuestionVote(questionId: Int, graphQLHttpContext: GraphQLHttpContext): Question? {
        upvoteRepository.cancelVote(questionId, graphQLHttpContext.user!!.id)
        return questionRepository.findQuestion(questionId)
    }

}