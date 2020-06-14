package fr.kommentaire.server.authentication

import com.expediagroup.graphql.annotations.GraphQLDirective
import com.expediagroup.graphql.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.directives.KotlinSchemaDirectiveWiring
import fr.kommentaire.server.HttpException
import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition
import org.springframework.http.HttpStatus


@GraphQLDirective(name = "auth", description = "Checks if authentication is respected")
annotation class AuthenticationDirective

class AuthenticationSchemaDirectiveWiring : KotlinSchemaDirectiveWiring {

    override fun onField(environment: KotlinFieldDirectiveEnvironment): GraphQLFieldDefinition {
        val field = environment.element
        val originalDataFetcher = environment.getDataFetcher()

        val authorisationFetcherFetcher = DataFetcher { dataEnv ->
            dataEnv.getContext<GraphQLHttpContext>().user ?: throw HttpException(HttpStatus.UNAUTHORIZED, "User not allowed")
            originalDataFetcher.get(dataEnv)
        }
        environment.setDataFetcher(authorisationFetcherFetcher)
        return field
    }

}

