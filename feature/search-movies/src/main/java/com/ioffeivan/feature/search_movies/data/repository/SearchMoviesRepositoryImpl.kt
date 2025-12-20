package com.ioffeivan.feature.search_movies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ioffeivan.core.database.AppDatabase
import com.ioffeivan.core.database.model.SearchMovieEntity
import com.ioffeivan.feature.search_movies.data.mapper.toDomain
import com.ioffeivan.feature.search_movies.data.source.SearchMoviesRemoteMediator
import com.ioffeivan.feature.search_movies.data.source.remote.api.SearchMoviesApiService
import com.ioffeivan.feature.search_movies.domain.model.SearchMovie
import com.ioffeivan.feature.search_movies.domain.repository.SearchMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PAGE_SIZE = 10

internal class SearchMoviesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val searchMoviesApiService: SearchMoviesApiService,
) : SearchMoviesRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun search(query: String): Flow<PagingData<SearchMovie>> {
        return Pager(
            config =
                PagingConfig(
                    pageSize = PAGE_SIZE,
                    initialLoadSize = PAGE_SIZE,
                ),
            remoteMediator =
                SearchMoviesRemoteMediator(
                    query = query,
                    searchMoviesApiService = searchMoviesApiService,
                    appDatabase = appDatabase,
                ),
            pagingSourceFactory = {
                appDatabase.searchMoviesDao().pagingSource(query)
            },
        )
            .flow
            .map { it.map(SearchMovieEntity::toDomain) }
    }
}
