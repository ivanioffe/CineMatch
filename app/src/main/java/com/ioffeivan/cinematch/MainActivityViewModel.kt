package com.ioffeivan.cinematch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ioffeivan.feature.auth.domain.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {
    val uiState =
        isLoggedInUseCase()
            .map { isLoggedIn ->
                if (isLoggedIn) {
                    MainActivityUiState.LoggedIn
                } else {
                    MainActivityUiState.LoggedOut
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = MainActivityUiState.Loading,
            )
}

sealed class MainActivityUiState {
    data object Loading : MainActivityUiState()

    data object LoggedIn : MainActivityUiState()

    data object LoggedOut : MainActivityUiState()
}
