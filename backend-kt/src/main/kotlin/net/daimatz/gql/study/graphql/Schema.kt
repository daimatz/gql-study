
package net.daimatz.gql.study.graphql

import net.daimatz.gql.study.TodoService

enum class TodoStatus {
  NOT_YET, DONE
}

data class Todo(
  val id: Int,
  val user_id: Int,
  val name: String,
  val status: TodoStatus
)

data class TodoWithoutID(
  val user_id: Int,
  val name: String,
  val status: TodoStatus
)

data class User(
  val id: Int,
  val name: String
)

class TodoQuery(val todoService: TodoService) {
  fun todos(): Array<Todo> = todoService.list()
}

class TodoMutation(val todoService: TodoService) {
  fun createTodo(input: TodoWithoutID): Todo = todoService.createTodo(input)
  fun setStatus(id: Int, status: TodoStatus): Todo = todoService.setStatus(id, status)
}
