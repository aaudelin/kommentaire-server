package fr.kommentaire.server.question

import com.expediagroup.graphql.annotations.GraphQLIgnore
import fr.kommentaire.server.BeanUtil
import fr.kommentaire.server.user.UserRepository

data class Question(val id: Int, val content: String, val votes: Int, @GraphQLIgnore val userId: Int = 0) {

    @GraphQLIgnore
    private val userRepository = BeanUtil.getBean(UserRepository::class.java)

    fun user() = userRepository.findUserFromId(userId)
}
