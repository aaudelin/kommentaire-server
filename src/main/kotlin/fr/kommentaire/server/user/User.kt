package fr.kommentaire.server.user

import com.expediagroup.graphql.annotations.GraphQLIgnore
import fr.kommentaire.server.BeanUtil
import fr.kommentaire.server.question.QuestionRepository

data class User(val id: Int, val pseudo: String, val pwd: String, val token: String, val dateToken: String, val role: UserRole) {
    @GraphQLIgnore
    private val questionRepository = BeanUtil.getBean(QuestionRepository::class.java)

    fun question() = questionRepository.findQuestionsFromUserId(id)
}

enum class UserRole {
    USER,
    ADMIN
}
