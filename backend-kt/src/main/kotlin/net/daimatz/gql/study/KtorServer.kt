
package net.daimatz.gql.study

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

class KtorServer {
  private val mapper = jacksonObjectMapper()
  private val ktorGraphQLServer = getGraphQLServer(mapper)

  suspend fun handle(applicationCall: ApplicationCall) {
    val result = ktorGraphQLServer.execute(applicationCall.request)

    if (result != null) {
      val json = mapper.writeValueAsString(result)
      applicationCall.response.call.respond(json)
    } else {
      applicationCall.response.call.respond(HttpStatusCode.BadRequest, "Invalid request")
    }
  }
}
