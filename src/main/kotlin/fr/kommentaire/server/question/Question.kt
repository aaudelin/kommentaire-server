package fr.kommentaire.server.question

import com.expediagroup.graphql.annotations.GraphQLIgnore
import fr.kommentaire.server.user.User
import fr.kommentaire.server.user.UserRole

data class Question(val id: Int, val content: String, val votes: Int, @GraphQLIgnore val userId: Int = 0) {

    fun user() = User(0, "PSEUDO", "PWD", "TOKEN", "2020-06-07", UserRole.ADMIN)
}