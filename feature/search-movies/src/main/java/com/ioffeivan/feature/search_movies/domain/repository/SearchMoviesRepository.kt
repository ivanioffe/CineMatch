package com.ioffeivan.feature.search_movies.domain.repository

import androidx.paging.PagingData
import com.ioffeivan.feature.search_movies.domain.model.SearchMovie
import kotlinx.coroutines.flow.Flow

interface SearchMoviesRepository {
    fun search(query: String): Flow<PagingData<SearchMovie>>
}
