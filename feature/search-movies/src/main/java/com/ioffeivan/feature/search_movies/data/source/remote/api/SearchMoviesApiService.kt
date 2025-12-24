package com.ioffeivan.feature.search_movies.data.source.remote.api

import com.ioffeivan.feature.search_movies.data.source.remote.model.SearchMoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchMoviesApiService {
    @GET("movies")
    suspend fun search(
        @Query("title") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): SearchMoviesResponseDto
}
