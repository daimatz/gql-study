
package net.daimatz.gql.study

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.netty.EngineMain
import java.net.URI

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.setup() {
  val env = Env()
  install(Routing)

  val frontEndUri = URI(environment.config.property("ktor.frontend.url").getString())
  install(CORS) {
    method(HttpMethod.Get)
    method(HttpMethod.Post)
    method(HttpMethod.Put)
    method(HttpMethod.Delete)
    method(HttpMethod.Options)
    host(frontEndUri.authority, schemes = listOf(frontEndUri.scheme))
    allowCredentials = true
    allowNonSimpleContentTypes = true
  }

  routing {
    post("query") {
      env.ktorServer.handle(this.call)
    }

    get("playground") {
      val html = Application::class.java.classLoader.getResource("graphql-playground.html")?.readText()
        ?.replace("\${graphQLEndpoint}", "query")
        ?.replace("\${subscriptionsEndpoint}", "subscriptions")
        ?: throw IllegalStateException("graphql-playground.html cannot be found in the classpath")
      this.call.respondText(html, ContentType.Text.Html)
    }
  }
}
