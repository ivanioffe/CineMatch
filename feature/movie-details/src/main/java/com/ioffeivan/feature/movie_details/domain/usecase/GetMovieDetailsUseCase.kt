package com.ioffeivan.feature.movie_details.domain.usecase

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.domain.model.MovieDetails
import com.ioffeivan.feature.movie_details.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
) {
    suspend operator fun invoke(id: Int): Result<MovieDetails> {
        return movieDetailsRepository.getMovieDetails(id)
    }
}
