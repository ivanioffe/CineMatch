package com.ioffeivan.feature.search_movies.domain.usecase

import androidx.paging.PagingData
import com.ioffeivan.feature.search_movies.domain.model.SearchMovie
import com.ioffeivan.feature.search_movies.domain.repository.SearchMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val searchMoviesRepository: SearchMoviesRepository,
) {
    operator fun invoke(query: String): Flow<PagingData<SearchMovie>> {
        return searchMoviesRepository.search(query)
    }
}
