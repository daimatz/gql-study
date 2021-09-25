package graph

// This file will be automatically regenerated based on the schema, any resolver implementations
// will be copied through when generating and any unknown code will be moved to the end.

import (
	"context"

	"github.com/daimatz/gql-study/graph/generated"
	"github.com/daimatz/gql-study/graph/model"
)

func (r *mutationResolver) CreateTodo(ctx context.Context, input model.TodoWithoutID) (*model.Todo, error) {
	return r.Dba.CreateTodo(input)
}

func (r *mutationResolver) SetStatus(ctx context.Context, id int, status *model.TodoStatus) (*model.Todo, error) {
	return r.Dba.SetStatus(id, status)
}

func (r *queryResolver) Todos(ctx context.Context) ([]*model.Todo, error) {
	return r.Dba.ListTodos()
}

// Mutation returns generated.MutationResolver implementation.
func (r *Resolver) Mutation() generated.MutationResolver { return &mutationResolver{r} }

// Query returns generated.QueryResolver implementation.
func (r *Resolver) Query() generated.QueryResolver { return &queryResolver{r} }

type mutationResolver struct{ *Resolver }
type queryResolver struct{ *Resolver }
