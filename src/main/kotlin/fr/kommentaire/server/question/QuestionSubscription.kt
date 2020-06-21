package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Subscription
import fr.kommentaire.server.authentication.AuthenticationDirective
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.asPublisher
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import kotlin.random.Random

@Component
class QuestionSubscription(
        val questionRepository: QuestionRepository
) : Subscription {

    fun question(lastQuestionId: Int?): Flux<Question> {
        var questionId = lastQuestionId ?: questionRepository.findLastQuestion()?.id ?: 0
        questionId++

        return Flux.interval(Duration.ofSeconds(1)).flatMap {
            val question = questionRepository.findQuestion(questionId)
            question?.let { questionId++ }
            Mono.justOrEmpty(question)
        }
    }

}