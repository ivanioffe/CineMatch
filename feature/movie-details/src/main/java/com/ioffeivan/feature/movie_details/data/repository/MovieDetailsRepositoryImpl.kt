package com.ioffeivan.feature.movie_details.data.repository

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.data.mapper.toDomain
import com.ioffeivan.feature.movie_details.data.source.remote.data_source.MovieDetailsRemoteDataSource
import com.ioffeivan.feature.movie_details.domain.model.MovieDetails
import com.ioffeivan.feature.movie_details.domain.repository.MovieDetailsRepository
import javax.inject.Inject

internal class MovieDetailsRepositoryImpl @Inject constructor(
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(id: Int): Result<MovieDetails> {
        val result = movieDetailsRemoteDataSource.getMovieDetails(id)

        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.toDomain())
            }

            is Result.Error -> {
                Result.Error(result.message)
            }

            is Result.Exception -> {
                Result.Exception(result.exception)
            }
        }
    }
}
