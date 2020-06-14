package fr.kommentaire.server.authentication

import com.expediagroup.graphql.execution.GraphQLContext
import com.expediagroup.graphql.spring.execution.GraphQLContextFactory
import fr.kommentaire.server.BeanUtil
import fr.kommentaire.server.user.User
import fr.kommentaire.server.user.UserRepository
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component


class GraphQLHttpContext(val token: String) : GraphQLContext {

    val user : User?
    get() {
        val userRepository = BeanUtil.getBean(UserRepository::class.java)
        return userRepository.findUserFromToken(token)
    }

}

@Component
class GraphQLHttpContextFactory(val userRepository: UserRepository): GraphQLContextFactory<GraphQLHttpContext> {
    override suspend fun generateContext(
            request: ServerHttpRequest,
            response: ServerHttpResponse
    ): GraphQLHttpContext {
        return GraphQLHttpContext(
                token = request.headers.getFirst("access_token") ?: "NONE"
        )

    }
}