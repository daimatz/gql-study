
package net.daimatz.gql.study

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler

class Env {
  val dba = DbaImpl()
  val todoService = TodoServiceImpl(dba)
  val gqlService = GqlServiceImpl(todoService)

  val mapper = jacksonObjectMapper()

  val dataLoaderRegistryFactory = KtorDataLoaderRegistryFactory()
  val requestParser = KtorGraphQLRequestParser(mapper)
  val contextFactory = KtorGraphQLContextFactory()
  val graphQL = gqlService.getGraphQLObject()
  val requestHandler = GraphQLRequestHandler(graphQL, dataLoaderRegistryFactory)
  val ktorGraphQLServer = KtorGraphQLServer(requestParser, contextFactory, requestHandler)

  val ktorServer = KtorServerImpl(mapper, ktorGraphQLServer)
}
