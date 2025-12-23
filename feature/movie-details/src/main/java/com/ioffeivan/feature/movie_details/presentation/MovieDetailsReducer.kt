package com.ioffeivan.feature.movie_details.presentation

import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.feature.movie_details.domain.model.MovieDetails

class MovieDetailsReducer : Reducer<MovieDetailsState, MovieDetailsEvent, MovieDetailsEffect> {
    override fun reduce(
        previousState: MovieDetailsState,
        event: MovieDetailsEvent,
    ): ReducerResult<MovieDetailsState, MovieDetailsEffect> {
        return when (event) {
            MovieDetailsEvent.BackClick -> {
                ReducerResult(
                    state = previousState,
                    effect = MovieDetailsEffect.NavigateToBack,
                )
            }

            MovieDetailsEvent.MovieDetailsLoadError -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = false,
                            isError = true,
                        ),
                )
            }

            is MovieDetailsEvent.MovieDetailsLoadSuccess -> {
                ReducerResult(
                    state = event.movieDetails.toMovieDetailsState(),
                )
            }

            MovieDetailsEvent.RetryLoadClick -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = true,
                            isError = false,
                        ),
                )
            }
        }
    }
}

data class MovieDetailsState(
    val id: Int,
    val title: String,
    val genres: String,
    val overview: String,
    val year: String,
    val imageUrl: String,
    val isLoading: Boolean,
    val isError: Boolean,
) : Reducer.UiState {
    companion object {
        fun initial(): MovieDetailsState {
            return MovieDetailsState(
                id = 0,
                title = "",
                genres = "",
                overview = "",
                year = "",
                imageUrl = "",
                isLoading = true,
                isError = false,
            )
        }
    }
}

sealed interface MovieDetailsEvent : Reducer.UiEvent {
    data object MovieDetailsLoadError : MovieDetailsEvent

    data class MovieDetailsLoadSuccess(
        val movieDetails: MovieDetails,
    ) : MovieDetailsEvent

    data object RetryLoadClick : MovieDetailsEvent

    data object BackClick : MovieDetailsEvent
}

sealed interface MovieDetailsEffect : Reducer.UiEffect {
    data object NavigateToBack : MovieDetailsEffect
}
