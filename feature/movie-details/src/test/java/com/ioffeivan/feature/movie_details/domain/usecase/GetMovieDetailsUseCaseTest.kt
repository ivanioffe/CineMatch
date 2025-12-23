package com.ioffeivan.feature.movie_details.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.GetMovieDetailsResultDomainArgumentsProvider
import com.ioffeivan.feature.movie_details.domain.model.MovieDetails
import com.ioffeivan.feature.movie_details.domain.repository.MovieDetailsRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

class GetMovieDetailsUseCaseTest {
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @BeforeEach
    fun setUp() {
        movieDetailsRepository = mockk()
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieDetailsRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(GetMovieDetailsResultDomainArgumentsProvider::class)
    fun whenRepositoryReturnsResult_shouldReturnsSameResult(result: Result<MovieDetails>) =
        runTest {
            val id = 1
            coEvery { movieDetailsRepository.getMovieDetails(id) } returns result

            val actual = getMovieDetailsUseCase(id)

            assertThat(actual).isEqualTo(result)
            coVerify(exactly = 1) { movieDetailsRepository.getMovieDetails(id) }
        }
}
