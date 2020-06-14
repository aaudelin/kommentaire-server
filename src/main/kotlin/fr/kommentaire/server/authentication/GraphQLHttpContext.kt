package fr.kommentaire.server.authentication

import com.expediagroup.graphql.execution.GraphQLContext
import com.expediagroup.graphql.spring.execution.GraphQLContextFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component


class GraphQLHttpContext(val authenticationToken: String) : GraphQLContext

@Component
class GraphQLHttpContextFactory: GraphQLContextFactory<GraphQLHttpContext> {
    override suspend fun generateContext(
            request: ServerHttpRequest,
            response: ServerHttpResponse
    ): GraphQLHttpContext = GraphQLHttpContext(
            authenticationToken = request.headers.getFirst("access_token") ?: "NONE"
    )
}