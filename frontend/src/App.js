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
    }
  }
`;

class App extends Component {
  state = {
    todos: [],
    errors: [],
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

  render() {
    return (
      <div>
        <h1>{TITLE}</h1>

        <ul>
        {this.state.todos.map(todo => (
          <li key={todo.id}>{todo.name}</li>
        ))}
        </ul>
      </div>
    );
  }
};

export default App;
