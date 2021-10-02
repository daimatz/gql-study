
package net.daimatz.gql.study

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.setup() {
  val env = Env()
  install(Routing)

  routing {
    post("graphql") {
      env.ktorServer.handle(this.call)
    }

    get("playground") {
      val html = Application::class.java.classLoader.getResource("graphql-playground.html")?.readText()
        ?.replace("\${graphQLEndpoint}", "graphql")
        ?.replace("\${subscriptionsEndpoint}", "subscriptions")
        ?: throw IllegalStateException("graphql-playground.html cannot be found in the classpath")
      this.call.respondText(html, ContentType.Text.Html)
    }
  }
}
