
package net.daimatz.gql.study

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import java.sql.DriverManager

class Env {
  val ktorServer: KtorServer

  init {
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/todo", "todo", "todo")
    val mapper = jacksonObjectMapper()

    val todoRepository = TodoRepositoryImpl(mapper, connection)
    val todoService = TodoServiceImpl(todoRepository)
    val gqlService = GqlServiceImpl(todoService)

    val dataLoaderRegistryFactory = KtorDataLoaderRegistryFactory()
    val requestParser = KtorGraphQLRequestParser(mapper)
    val contextFactory = KtorGraphQLContextFactory()
    val graphQL = gqlService.getGraphQLObject()
    val requestHandler = GraphQLRequestHandler(graphQL, dataLoaderRegistryFactory)
    val ktorGraphQLServer = KtorGraphQLServer(requestParser, contextFactory, requestHandler)

    ktorServer = KtorServerImpl(mapper, ktorGraphQLServer)
  }
}
