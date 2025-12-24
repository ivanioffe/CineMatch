package com.ioffeivan.feature.movie_details.presentation

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.common.result.onError
import com.ioffeivan.core.common.result.onException
import com.ioffeivan.core.common.result.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.feature.movie_details.domain.usecase.GetMovieDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MovieDetailsViewModel.Factory::class)
class MovieDetailsViewModel @AssistedInject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    @Assisted private val movieId: Int,
) : BaseViewModel<MovieDetailsState, MovieDetailsEvent, MovieDetailsEffect>(
        initialState = MovieDetailsState.initial(),
        reducer = MovieDetailsReducer(),
    ) {
    override fun onEvent(event: MovieDetailsEvent) {
        sendEvent(event)

        when (event) {
            is MovieDetailsEvent.RetryLoadClick -> {
                viewModelScope.launch {
                    getMovieDetails()
                }
            }

            else -> {}
        }
    }

    override suspend fun initialDataLoad() {
        getMovieDetails()
    }

    private suspend fun getMovieDetails() {
        getMovieDetailsUseCase(movieId)
            .onSuccess { movieDetails ->
                sendEvent(MovieDetailsEvent.MovieDetailsLoadSuccess(movieDetails))
            }.onError {
                sendEvent(MovieDetailsEvent.MovieDetailsLoadError)
            }.onException {
                sendEvent(MovieDetailsEvent.MovieDetailsLoadError)
            }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            movieId: Int,
        ): MovieDetailsViewModel
    }
}
