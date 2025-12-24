package com.ioffeivan.feature.movie_details.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.presentation.BaseViewModelTest
import com.ioffeivan.feature.movie_details.domain.usecase.GetMovieDetailsUseCase
import com.ioffeivan.feature.movie_details.movieDetailsStateTest
import com.ioffeivan.feature.movie_details.movieDetailsTest
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class MovieDetailsViewModelTest : BaseViewModelTest() {
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val movieId = 1

    @BeforeEach
    override fun setUp() {
        super.setUp()
        getMovieDetailsUseCase = mockk()
        movieDetailsViewModel =
            spyk(
                MovieDetailsViewModel(getMovieDetailsUseCase, movieId),
            )
    }

    @AfterEach
    override fun tearDown() {
        super.tearDown()
        clearAllMocks()
    }

    @Test
    fun initialDataLoad_whenUseCaseReturnsSuccess_shouldEmitSuccessState() =
        runTest {
            coEvery { getMovieDetailsUseCase(movieId) } returns Result.Success(movieDetailsTest)

            movieDetailsViewModel.state.test {
                val actual = awaitItem()
                assertThat(actual).isEqualTo(movieDetailsStateTest)
            }
        }

    @Test
    fun initialDataLoad_whenUseCaseReturnsError_shouldEmitErrorState() =
        runTest {
            val expected = MovieDetailsState.initial().copy(isLoading = false, isError = true)
            coEvery { getMovieDetailsUseCase(movieId) } returns Result.Error("error")

            movieDetailsViewModel.state.test {
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
            }
        }

    @Test
    fun initialDataLoad_whenUseCaseReturnsException_shouldEmitErrorState() =
        runTest {
            val expected = MovieDetailsState.initial().copy(isLoading = false, isError = true)
            coEvery { getMovieDetailsUseCase(movieId) } returns Result.Exception(IOException())

            movieDetailsViewModel.state.test {
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
            }
        }

    @Test
    fun onEvent_retryLoadClickAfterError_shouldEmitSuccessState() =
        runTest {
            coEvery {
                getMovieDetailsUseCase(movieId)
            } returnsMany
                listOf(
                    Result.Exception(IOException()),
                    Result.Success(movieDetailsTest),
                )

            movieDetailsViewModel.state.test {
                val errorState = awaitItem()
                assertThat(errorState.isError).isTrue()

                movieDetailsViewModel.onEvent(MovieDetailsEvent.RetryLoadClick)

                val loadingState = awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val successState = awaitItem()
                assertThat(successState).isEqualTo(movieDetailsStateTest)
            }
        }
}
