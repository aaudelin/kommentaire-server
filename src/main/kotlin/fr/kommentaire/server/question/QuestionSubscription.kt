package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Subscription
import fr.kommentaire.server.BeanUtil
import fr.kommentaire.server.authentication.AuthenticationDirective
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.asPublisher
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant
import java.util.Date
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

    fun questionChange(): Flux<List<Question>> {
        var timestamp = Date.from(Instant.now())

        return Flux.interval(Duration.ofSeconds(3)).flatMap {
            val questionsIds = BeanUtil.getQuestions().filter {
                it.value.after(timestamp)
            }.keys
            timestamp = Date.from(Instant.now())
            val questions = questionsIds.mapNotNull { questionRepository.findQuestion(it) }.ifEmpty { null }
            Mono.justOrEmpty(questions)
        }
    }

}