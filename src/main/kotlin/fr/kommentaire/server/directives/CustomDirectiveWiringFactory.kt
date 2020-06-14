package fr.kommentaire.server.directives

import com.expediagroup.graphql.directives.KotlinDirectiveWiringFactory
import fr.kommentaire.server.authentication.AuthenticationSchemaDirectiveWiring

class CustomDirectiveWiringFactory : KotlinDirectiveWiringFactory(
        manualWiring = mapOf(
                "auth" to AuthenticationSchemaDirectiveWiring()
        )
)
