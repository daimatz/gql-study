
package net.daimatz.gql.study

// import com.expediagroup.graphql.examples.server.ktor.schema.dataloaders.BookDataLoader
// import com.expediagroup.graphql.examples.server.ktor.schema.dataloaders.CourseDataLoader
// import com.expediagroup.graphql.examples.server.ktor.schema.dataloaders.UniversityDataLoader
import com.expediagroup.graphql.server.execution.DataLoaderRegistryFactory
import org.dataloader.DataLoaderRegistry

class KtorDataLoaderRegistryFactory : DataLoaderRegistryFactory {
  override fun generate(): DataLoaderRegistry {
    val registry = DataLoaderRegistry()
    // registry.register(UniversityDataLoader.dataLoaderName, UniversityDataLoader.getDataLoader())
    // registry.register(CourseDataLoader.dataLoaderName, CourseDataLoader.getDataLoader())
    // registry.register(BookDataLoader.dataLoaderName, BookDataLoader.getDataLoader())
    return registry
  }
}
