
package net.daimatz.gql.study

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

interface KtorServer {
  suspend fun handle(applicationCall: ApplicationCall)
}

class KtorServerImpl(
  private val mapper: ObjectMapper,
  private val ktorGraphQLServer: KtorGraphQLServer
): KtorServer {
  override suspend fun handle(applicationCall: ApplicationCall) {
    val result = ktorGraphQLServer.execute(applicationCall.request)

    if (result != null) {
      val json = mapper.writeValueAsString(result)
      applicationCall.response.call.respond(json)
    } else {
      applicationCall.response.call.respond(HttpStatusCode.BadRequest, "Invalid request")
    }
  }
}
