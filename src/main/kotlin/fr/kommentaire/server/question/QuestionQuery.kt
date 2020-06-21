package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Query
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class QuestionQuery(
        val questionRepository: QuestionRepository
) : Query {

    fun questions(environment: DataFetchingEnvironment) : List<Question> {
        return questionRepository.selectAllQuestions()
    }

}