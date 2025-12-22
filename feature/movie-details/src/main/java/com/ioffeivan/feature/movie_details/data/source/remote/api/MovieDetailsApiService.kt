package com.ioffeivan.feature.movie_details.data.source.remote.api

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.data.source.remote.model.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieDetailsApiService {
    @GET("movie")
    suspend fun getMovieDetails(
        @Query("id") id: Int,
    ): Result<MovieDetailsDto>
}
