package graph

import "github.com/daimatz/gql-study/dba"

// This file will not be regenerated automatically.
//
// It serves as dependency injection for your app, add any dependencies you require here.

type Resolver struct {
	Dba *dba.DBA
}
