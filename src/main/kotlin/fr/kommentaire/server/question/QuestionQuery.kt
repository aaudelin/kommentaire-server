package fr.kommentaire.server.question

import com.expediagroup.graphql.spring.operations.Query
import fr.kommentaire.server.user.User
import fr.kommentaire.server.user.UserRole
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.Date

@Component
class QuestionQuery(
        val questionRepository: QuestionRepository
) : Query {

    fun question(environment: DataFetchingEnvironment) : List<Question> {
        return questionRepository.selectAllQuestions()
    }

}