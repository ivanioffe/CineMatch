package com.ioffeivan.feature.movie_details.data.source.remote.data_source

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.data.source.remote.model.MovieDetailsDto

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieDetails(id: Int): Result<MovieDetailsDto>
}
