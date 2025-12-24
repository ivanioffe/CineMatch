package com.ioffeivan.feature.movie_details.presentation

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.feature.movie_details.movieDetailsStateTest
import com.ioffeivan.feature.movie_details.movieDetailsTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieDetailsReducerTest {
    private lateinit var reducer: MovieDetailsReducer
    private lateinit var initialState: MovieDetailsState

    @BeforeEach
    fun setUp() {
        reducer = MovieDetailsReducer()
        initialState = MovieDetailsState.initial()
    }

    @Test
    fun backClick_shouldEmitNavigateToBackEffect() {
        val event = MovieDetailsEvent.BackClick
        val expected =
            createReducerResult(
                state = initialState,
                effect = MovieDetailsEffect.NavigateToBack,
            )

        val actual = reducer.reduce(initialState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun movieDetailsLoadError_shouldSetIsLoadingFalseAndIsErrorTrue() {
        val prevState = initialState.copy(isLoading = true, isError = false)
        val event = MovieDetailsEvent.MovieDetailsLoadError
        val expected =
            createReducerResult(
                state = prevState.copy(isLoading = false, isError = true),
            )

        val actual = reducer.reduce(prevState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun movieDetailsLoadSuccess_shouldMapDataToStateAndStopLoading() {
        val movieDetails = movieDetailsTest
        val event = MovieDetailsEvent.MovieDetailsLoadSuccess(movieDetails)
        val expected =
            createReducerResult(
                state = movieDetailsStateTest,
            )

        val actual = reducer.reduce(initialState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun retryLoadClick_shouldSetIsLoadingTrueAndResetError() {
        val errorState = initialState.copy(isLoading = false, isError = true)
        val event = MovieDetailsEvent.RetryLoadClick
        val expected =
            createReducerResult(
                state = errorState.copy(isLoading = true, isError = false),
            )

        val actual = reducer.reduce(errorState, event)

        assertThat(actual).isEqualTo(expected)
    }

    private fun createReducerResult(
        state: MovieDetailsState,
        effect: MovieDetailsEffect? = null,
    ): ReducerResult<MovieDetailsState, MovieDetailsEffect> {
        return ReducerResult(state = state, effect = effect)
    }
}
