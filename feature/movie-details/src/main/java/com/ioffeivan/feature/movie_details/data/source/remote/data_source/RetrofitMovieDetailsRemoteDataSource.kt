package com.ioffeivan.feature.movie_details.data.source.remote.data_source

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.data.source.remote.api.MovieDetailsApiService
import com.ioffeivan.feature.movie_details.data.source.remote.model.MovieDetailsDto
import javax.inject.Inject

internal class RetrofitMovieDetailsRemoteDataSource @Inject constructor(
    private val movieDetailsApiService: MovieDetailsApiService,
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieDetails(id: Int): Result<MovieDetailsDto> {
        return movieDetailsApiService.getMovieDetails(id)
    }
}
