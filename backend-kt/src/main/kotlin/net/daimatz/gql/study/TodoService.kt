
package net.daimatz.gql.study

import net.daimatz.gql.study.graphql.Todo
import net.daimatz.gql.study.graphql.TodoStatus
import net.daimatz.gql.study.graphql.TodoWithoutID

interface TodoService {
  fun list(): Array<Todo>
  fun createTodo(input: TodoWithoutID): Todo
  fun setStatus(id: Int, status: TodoStatus): Todo
}

class TodoServiceImpl(val repository: TodoRepository): TodoService {
  override fun list(): Array<Todo> {
    return repository.list()
  }
  override fun createTodo(input: TodoWithoutID): Todo {
    return repository.createTodo(input)
  }
  override fun setStatus(id: Int, status: TodoStatus): Todo {
    return repository.setStatus(id, status)
  }
}
