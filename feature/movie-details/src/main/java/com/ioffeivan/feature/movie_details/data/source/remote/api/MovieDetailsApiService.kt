package com.ioffeivan.feature.movie_details.data.source.remote.api

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.data.source.remote.model.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface MovieDetailsApiService {
    @GET("movies/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
    ): Result<MovieDetailsDto>
}
