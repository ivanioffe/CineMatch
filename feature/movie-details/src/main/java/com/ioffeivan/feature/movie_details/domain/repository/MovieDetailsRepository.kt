package com.ioffeivan.feature.movie_details.domain.repository

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.domain.model.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieDetails(id: Int): Result<MovieDetails>
}
