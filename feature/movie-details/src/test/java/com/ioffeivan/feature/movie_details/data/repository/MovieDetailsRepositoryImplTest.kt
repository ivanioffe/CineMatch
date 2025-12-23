package com.ioffeivan.feature.movie_details.data.repository

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.data.source.remote.data_source.MovieDetailsRemoteDataSource
import com.ioffeivan.feature.movie_details.domain.repository.MovieDetailsRepository
import com.ioffeivan.feature.movie_details.movieDetailsDtoTest
import com.ioffeivan.feature.movie_details.movieDetailsTest
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class MovieDetailsRepositoryImplTest {
    private lateinit var movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    @BeforeEach
    fun setUp() {
        movieDetailsRemoteDataSource = mockk()
        movieDetailsRepository = MovieDetailsRepositoryImpl(movieDetailsRemoteDataSource)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getMovieDetails_whenRemoteDataSourceReturnsSuccess_shouldReturnsSuccessMappedDomainResult() =
        runTest {
            val id = 1
            val dto = Result.Success(movieDetailsDtoTest)
            val domain = Result.Success(movieDetailsTest)
            coEvery { movieDetailsRemoteDataSource.getMovieDetails(id) } returns dto

            val actual = movieDetailsRepository.getMovieDetails(id)

            assertThat(actual).isEqualTo(domain)
            coVerify(exactly = 1) { movieDetailsRemoteDataSource.getMovieDetails(id) }
        }

    @Test
    fun getMovieDetails_whenRemoteDataSourceReturnsError_shouldReturnsError() =
        runTest {
            val id = 1
            val expected = Result.Error("error")
            coEvery { movieDetailsRemoteDataSource.getMovieDetails(id) } returns expected

            val actual = movieDetailsRepository.getMovieDetails(id)

            assertThat(actual).isEqualTo(expected)
            coVerify(exactly = 1) { movieDetailsRemoteDataSource.getMovieDetails(id) }
        }

    @Test
    fun getMovieDetails_whenRemoteDataSourceReturnsException_shouldReturnsException() =
        runTest {
            val id = 1
            val expected = Result.Exception(IOException())
            coEvery { movieDetailsRemoteDataSource.getMovieDetails(id) } returns expected

            val actual = movieDetailsRepository.getMovieDetails(id)

            assertThat(actual).isEqualTo(expected)
            coVerify(exactly = 1) { movieDetailsRemoteDataSource.getMovieDetails(id) }
        }
}
