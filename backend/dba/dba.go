package dba

import (
	"database/sql"
	"encoding/json"
	"fmt"

	"github.com/daimatz/gql-study/graph/model"
	_ "github.com/go-sql-driver/mysql"
)

const accessInfo = "todo:todo@/todo"

type DBA struct {
	conn *sql.DB
}

func (db *DBA) Close() error {
	if db.conn == nil {
		return fmt.Errorf("connection is empty")
	}
	db.conn.Close()
	db.conn = nil
	return nil
}

func (db *DBA) Open() error {
	conn, err := sql.Open("mysql", accessInfo)
	if err != nil {
		return err
	}
	db.conn = conn
	return nil
}

func (db *DBA) CreateTodo(input model.TodoWithoutID) (*model.Todo, error) {
	if db.conn == nil {
		return nil, fmt.Errorf("connection is empty")
	}
	js, err := json.Marshal(input)
	if err != nil {
		return nil, err
	}

	ps, err := db.conn.Prepare("INSERT INTO todo (`user_id`, `json`) values (?, ?)")
	if err != nil {
		return nil, err
	}
	defer ps.Close()

	res, err := ps.Exec(input.UserID, js)
	if err != nil {
		return nil, err
	}

	id, err := res.LastInsertId()
	if err != nil {
		return nil, err
	}
	return &model.Todo{
		ID:     int(id),
		UserID: input.UserID,
		Name:   input.Name,
		Status: input.Status,
	}, nil
}

func (db *DBA) SetStatus(id int, status *model.TodoStatus) (*model.Todo, error) {
	if db.conn == nil {
		return nil, fmt.Errorf("connection is empty")
	}
	var withoutID model.TodoWithoutID
	for {
		ps, err := db.conn.Prepare("SELECT json FROM todo WHERE id = ?")
		if err != nil {
			return nil, err
		}
		res, err := ps.Query(id)
		if err != nil {
			return nil, err
		}

		res.Next()
		var str string
		if err := res.Scan(&str); err != nil {
			return nil, err
		}
		if err := json.Unmarshal([]byte(str), &withoutID); err != nil {
			return nil, err
		}
		withoutID.Status = *status
		js, err := json.Marshal(withoutID)
		if err != nil {
			return nil, err
		}
		ps2, err := db.conn.Prepare("UPDATE todo SET json = ? WHERE id = ? AND json = ?")
		if err != nil {
			return nil, err
		}
		res2, err := ps2.Exec(js, id, str)
		if err != nil {
			return nil, err
		}
		rows, err := res2.RowsAffected()
		if err != nil {
			return nil, err
		}
		if rows == 1 {
			break
		}
	}
	return &model.Todo{
		ID:     id,
		UserID: withoutID.UserID,
		Name:   withoutID.Name,
		Status: withoutID.Status,
	}, nil
}

func (db *DBA) ListTodos() ([]*model.Todo, error) {
	if db.conn == nil {
		return nil, fmt.Errorf("connection is empty")
	}

	res, err := db.conn.Query("SELECT id, json FROM todo")
	if err != nil {
		return nil, err
	}
	defer res.Close()

	todos := make([]*model.Todo, 0)
	for res.Next() {
		var id int
		var str string
		if err := res.Scan(&id, &str); err != nil {
			return nil, err
		}
		var withoutID model.TodoWithoutID
		if err := json.Unmarshal([]byte(str), &withoutID); err != nil {
			return nil, err
		}
		todo := &model.Todo{
			ID:     id,
			UserID: withoutID.UserID,
			Name:   withoutID.Name,
			Status: withoutID.Status,
		}
		todos = append(todos, todo)
	}

	return todos, nil
}
