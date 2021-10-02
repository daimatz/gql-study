
package net.daimatz.gql.study

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import graphql.GraphQL

import net.daimatz.gql.study.graphql.*

interface GqlService {
  fun getGraphQLObject(): GraphQL
}

class GqlServiceImpl(
  private val todoService: TodoService
): GqlService {
  override fun getGraphQLObject(): GraphQL {
    val todoQuery = TodoQuery(todoService)
    val todoMutation = TodoMutation(todoService)
    val config = SchemaGeneratorConfig(supportedPackages = listOf("net.daimatz.gql.study.graphql"))
    val schema = toSchema(
      config = config,
      queries = listOf(TopLevelObject(todoQuery)),
      mutations = listOf(TopLevelObject(todoMutation))
    )
    return GraphQL.newGraphQL(schema).build()
  }
}
