package com.ioffeivan.feature.movie_details.data.source.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.GetMovieDetailsResultDtoArgumentsProvider
import com.ioffeivan.feature.movie_details.data.source.remote.api.MovieDetailsApiService
import com.ioffeivan.feature.movie_details.data.source.remote.model.MovieDetailsDto
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

class RetrofitMovieDetailsRemoteDataSourceTest {
    private lateinit var movieDetailsApiService: MovieDetailsApiService
    private lateinit var movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource

    @BeforeEach
    fun setUp() {
        movieDetailsApiService = mockk()
        movieDetailsRemoteDataSource = RetrofitMovieDetailsRemoteDataSource(movieDetailsApiService)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(GetMovieDetailsResultDtoArgumentsProvider::class)
    fun getMovieDetails_whenApiReturnsResult_shouldReturnsSameResult(result: Result<MovieDetailsDto>) =
        runTest {
            val id = 1
            coEvery {
                movieDetailsApiService.getMovieDetails(id)
            } returns result

            val actual = movieDetailsRemoteDataSource.getMovieDetails(id)

            assertThat(actual).isEqualTo(result)
            coVerify(exactly = 1) { movieDetailsApiService.getMovieDetails(id) }
        }
}
