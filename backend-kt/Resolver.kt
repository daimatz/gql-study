
package net.daimatz.gql.study

enum class TodoStatus {
  NOT_YET, DONE
}

data class Todo(
  id: Int,
  userId: Int,
  name: String,
  status: TodoStatus
)

data class TodoWithoutID(
  userId: Int,
  name: String,
  status: TodoStatus
)

data class User(
  id: Int,
  name: String
)



interface Resolver {

}
