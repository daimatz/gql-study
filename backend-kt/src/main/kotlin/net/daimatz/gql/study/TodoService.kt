
package net.daimatz.gql.study

import net.daimatz.gql.study.graphql.Todo
import net.daimatz.gql.study.graphql.TodoStatus
import net.daimatz.gql.study.graphql.TodoWithoutID

interface TodoService {
  fun list(): Array<Todo>
  fun create(input: TodoWithoutID): Todo
}

class TodoServiceImpl(val dba: Dba): TodoService {
  override fun list(): Array<Todo> {
    return arrayOf()
  }
  override fun create(input: TodoWithoutID): Todo {
    return Todo(1,1,"",TodoStatus.NOT_YET)
  }
}
