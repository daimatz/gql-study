
package net.daimatz.gql.study

import com.expediagroup.graphql.generator.execution.GraphQLContext
import io.ktor.request.ApplicationRequest

data class AuthorizedContext(
  val applicationRequest: ApplicationRequest
) : GraphQLContext
