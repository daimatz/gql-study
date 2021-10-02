
package net.daimatz.gql.study.graphql
import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import graphql.GraphQL

enum class TodoStatus {
  NOT_YET, DONE
}

data class Todo(
  val id: Int,
  val userId: Int,
  val name: String,
  val status: TodoStatus
)

data class TodoWithoutID(
  val userId: Int,
  val name: String,
  val status: TodoStatus
)

data class User(
  val id: Int,
  val name: String
)

class TodoQuery {
  fun todos(): Array<Todo> {
    return arrayOf()
  }
}

class TodoMutation {
  fun create(input: TodoWithoutID): Todo {
    return Todo(1,1,"",TodoStatus.NOT_YET)
  }
}

val config = SchemaGeneratorConfig(supportedPackages = listOf("net.daimatz.gql.study.graphql"))
val todoQuery = TodoQuery()
val todoMutation = TodoMutation()
val schema = toSchema(
  config = config,
  queries = listOf(TopLevelObject(todoQuery)),
  mutations = listOf(TopLevelObject(todoMutation))
)

fun getGraphQLObject(): GraphQL = GraphQL.newGraphQL(schema).build()
