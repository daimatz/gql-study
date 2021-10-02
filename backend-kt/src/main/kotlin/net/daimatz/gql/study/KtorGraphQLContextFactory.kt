
package net.daimatz.gql.study

import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import io.ktor.request.ApplicationRequest

class KtorGraphQLContextFactory: GraphQLContextFactory<AuthorizedContext, ApplicationRequest> {
  override suspend fun generateContext(request: ApplicationRequest): AuthorizedContext {
    return AuthorizedContext(request)
  }
}
