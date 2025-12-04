package com.ioffeivan.feature.auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.common.result.onError
import com.ioffeivan.core.common.result.onException
import com.ioffeivan.core.common.result.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.usecase.LoginUseCase
import com.ioffeivan.feature.auth.presentation.login.utils.LoginValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(
        initialState = LoginState.initial(),
        reducer = LoginReducer(),
    ) {
    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginClick -> onLoginClickEvent()
            else -> sendEvent(event)
        }
    }

    private fun onLoginClickEvent() {
        val validationError = LoginValidation.validate(state.value)

        when (validationError) {
            LoginValidation.Result.Success -> {
                sendEvent(LoginEvent.LoginClick)
                login(state.value.toLoginCredentials())
            }

            is LoginValidation.Result.Error -> {
                sendEvent(LoginEvent.ValidationError(validationError))
            }
        }
    }

    private fun login(loginCredentials: LoginCredentials) {
        loginUseCase(loginCredentials)
            .onEach { result ->
                result.onSuccess {
                    sendEvent(LoginEvent.LoginSuccess)
                }.onError {
                    sendEvent(LoginEvent.LoginError(it))
                }.onException {
                    sendEvent(LoginEvent.LoginError(it.message))
                }
            }
            .launchIn(viewModelScope)
    }
}
