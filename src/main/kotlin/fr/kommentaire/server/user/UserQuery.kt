package fr.kommentaire.server.user

import com.expediagroup.graphql.spring.operations.Query
import fr.kommentaire.server.authentication.AuthenticationDirective
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class UserQuery(
        val userRepository: UserRepository
) : Query {

    @AuthenticationDirective
    fun user(pseudo: String?) : List<User?>? {
        return if (pseudo != null) listOf(userRepository.findUserFromPseudo(pseudo)) else userRepository.findAll()
    }

}