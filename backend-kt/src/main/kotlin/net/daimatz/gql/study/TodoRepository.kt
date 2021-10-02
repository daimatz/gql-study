
package net.daimatz.gql.study

import java.sql.Connection
import java.sql.Statement
import java.sql.ResultSet
import java.sql.PreparedStatement
import com.fasterxml.jackson.databind.ObjectMapper

import net.daimatz.gql.study.graphql.Todo
import net.daimatz.gql.study.graphql.TodoStatus
import net.daimatz.gql.study.graphql.TodoWithoutID

interface TodoRepository {
  fun list(): Array<Todo>
  fun createTodo(input: TodoWithoutID): Todo
  fun setStatus(id: Int, status: TodoStatus): Todo
}

class TodoRepositoryImpl(
  val mapper: ObjectMapper,
  val conn: Connection
): TodoRepository {
  override fun list(): Array<Todo> {
    val stmt: Statement = conn.createStatement()
    val rs: ResultSet = stmt.executeQuery("SELECT id, json FROM todo")
    val res: MutableList<Todo> = mutableListOf()

    while (rs.next()) {
      val id: Int = rs.getInt("id")
      val json: String = rs.getString("json")
      val todoWithoutID: TodoWithoutID = mapper.readValue(json, TodoWithoutID::class.java)
      val todo = Todo(
        id = id,
        user_id = todoWithoutID.user_id,
        name = todoWithoutID.name,
        status = todoWithoutID.status
      )
      res.add(todo)
    }
    return res.toTypedArray()
  }
  override fun createTodo(input: TodoWithoutID): Todo {
    val stmt: PreparedStatement = conn.prepareStatement(
      "INSERT INTO todo (user_id, json) VALUES (?, ?)",
      Statement.RETURN_GENERATED_KEYS
    )
    val json: String = mapper.writeValueAsString(input)
    stmt.setInt(1, input.user_id)
    stmt.setString(2, json)
    stmt.executeUpdate()

    val res: ResultSet = stmt.getGeneratedKeys()
    if (res.next()) {
      return Todo(
        id = res.getInt(1),
        user_id = input.user_id,
        name = input.name,
        status = input.status
      )
    } else {
      throw RuntimeException("Couldn't get LastInsertId")
    }
  }
  override fun setStatus(id: Int, status: TodoStatus): Todo {
    var updated: TodoWithoutID?
    while (true) {
      val stmt1: PreparedStatement = conn.prepareStatement("SELECT json FROM todo WHERE id = ?")
      stmt1.setInt(1, id)
      val rs: ResultSet = stmt1.executeQuery()
      rs.next()
      val json: String = rs.getString("json")
      val todoWithoutID: TodoWithoutID = mapper.readValue(json, TodoWithoutID::class.java)
      updated = todoWithoutID.copy(status = status)
      val json2: String = mapper.writeValueAsString(updated)
      val stmt2: PreparedStatement = conn.prepareStatement("UPDATE todo SET json = ? WHERE id = ? AND json = ?")
      stmt2.setString(1, json2)
      stmt2.setInt(2, id)
      stmt2.setString(3, json)
      stmt2.executeUpdate()

      val affectedRows = stmt2.executeUpdate()
      if (affectedRows == 1) {
        break
      }
    }
    if (updated == null) {
      throw RuntimeException("Something went wrong: updated is null")
    } else {
      return Todo(
        id = id,
        user_id = updated.user_id,
        name = updated.name,
        status = updated.status
      )
    }
  }

  private fun log(s: String) {
    java.nio.file.Files.writeString(
      java.nio.file.Paths.get("/tmp/log.txt"),
      s+"\n",
      java.nio.file.StandardOpenOption.APPEND
    )
  }
}
