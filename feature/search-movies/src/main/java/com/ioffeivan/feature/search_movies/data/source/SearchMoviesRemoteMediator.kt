package com.ioffeivan.feature.search_movies.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ioffeivan.core.database.AppDatabase
import com.ioffeivan.core.database.model.RemoteKeyEntity
import com.ioffeivan.core.database.model.SearchMovieEntity
import com.ioffeivan.feature.search_movies.data.mapper.toEntity
import com.ioffeivan.feature.search_movies.data.source.remote.api.SearchMoviesApiService
import com.ioffeivan.feature.search_movies.data.source.remote.model.PagingMetadata
import com.ioffeivan.feature.search_movies.data.source.remote.model.SearchMovieDto

@OptIn(ExperimentalPagingApi::class)
internal class SearchMoviesRemoteMediator(
    private val query: String,
    private val appDatabase: AppDatabase,
    private val searchMoviesApiService: SearchMoviesApiService,
) : RemoteMediator<Int, SearchMovieEntity>() {
    private val remoteKeyDao = appDatabase.remoteKeyDao()
    private val searchMoviesDao = appDatabase.searchMoviesDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchMovieEntity>,
    ): MediatorResult {
        return try {
            val remoteKey =
                when (loadType) {
                    LoadType.REFRESH -> 1
                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                    LoadType.APPEND -> {
                        val key = remoteKeyDao.getByLabel(query)

                        key?.nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }

            val searchMoviesResponse =
                searchMoviesApiService.search(
                    query = query,
                    page = remoteKey,
                    pageSize = state.config.pageSize,
                )
            val nextKey = getNextKey(searchMoviesResponse.metadata)

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByLabel(query)
                    searchMoviesDao.clearAll()
                }

                remoteKeyDao.upsert(
                    RemoteKeyEntity(
                        label = query,
                        nextKey = nextKey,
                    ),
                )

                val movieSearchEntities =
                    searchMoviesResponse.movies?.map(SearchMovieDto::toEntity) ?: listOf()
                searchMoviesDao.upsertAll(movieSearchEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = nextKey == null,
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getNextKey(pagingMetadata: PagingMetadata): Int? {
        return if (pagingMetadata.currentPage < pagingMetadata.lastPage) {
            pagingMetadata.currentPage + 1
        } else {
            null
        }
    }
}
