import React, { Component } from 'react';
import axios from 'axios';

const TITLE = 'TODO';

const api = axios.create({
  baseURL: 'http://localhost:8080/query',
});

const QUERY_TODOS = `
  {
    todos {
      id
      name
      status
    }
  }
`;

const QUERY_CREATE = `
  mutation($name: String!) {
    createTodo(input: {
      user_id: 1,
      name: $name,
      status: NOT_YET
    }) {
      id
      name
      status
    }
  }
`;

const TodoList = ({
  todo,
  onClickDone,
  onClickUndone,
  onClickDelete,
}) => (
  <li>
    {todo.name}
    [{todo.status === 'NOT_YET' ?
      <button onClick={onClickDone}>DONE</button> :
      <button onClick={onClickUndone}>UNDONE</button>
    }]
    [<button onClick={onClickDelete}>DELETE</button>]
  </li>
);

class App extends Component {
  state = {
    todos: [],
    errors: [],
    input: '',
  };

  componentDidMount() {
    this.refreshTodos();
  }

  refreshTodos() {
    api.post('', {
      query: QUERY_TODOS,
    }).then(res => {
      this.setState({ todos: res.data.data.todos });
    });
  }

  onClickDone(id) {
  }

  onClickUndone(id) {
  }

  onClickDelete(id) {
  }

  onChangeInput(e) {
    this.setState({ input: e.target.value });

    e.preventDefault();
  }

  onSubmit() {
    api.post('', {
      query: QUERY_CREATE,
      variables: { name: this.state.input },
    }).then(res => {
      this.setState(s => ({
        todos: s.todos.concat(res.data.data.createTodo),
        input: '',
      }));
    });
  }

  render() {
    return (
      <div>
        <h1>{TITLE}</h1>

        <input
          type="text"
          size="40"
          value={this.state.input}
          onChange={this.onChangeInput.bind(this)}
        />
        <button
          onClick={this.onSubmit.bind(this)}
        >create</button>

        <ul>
        {this.state.todos.map(todo => (
          <TodoList
            key={todo.id}
            todo={todo}
            onClickDone={() => this.onClickDone(todo.id)}
            onClickDelete={() => this.onClickDelete(todo.id)}
          />
        ))}
        </ul>
      </div>
    );
  }
};

export default App;
