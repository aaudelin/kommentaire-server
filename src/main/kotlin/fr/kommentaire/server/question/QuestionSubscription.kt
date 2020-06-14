package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Subscription
import fr.kommentaire.server.authentication.AuthenticationDirective
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.asPublisher
import org.reactivestreams.Publisher
import org.springframework.stereotype.Component

@Component
class QuestionSubscription(
        val questionRepository: QuestionRepository
) : Subscription {

    @AuthenticationDirective
    fun question(): Publisher<Question> {
        val questions = questionRepository.selectAllQuestions().toTypedArray()
        return flowOf(*questions).asPublisher()
    }

}