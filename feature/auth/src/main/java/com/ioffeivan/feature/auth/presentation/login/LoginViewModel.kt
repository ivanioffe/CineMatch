package com.ioffeivan.feature.auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.common.result.onError
import com.ioffeivan.core.common.result.onException
import com.ioffeivan.core.common.result.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(
        initialState = LoginState.initial(),
        reducer = LoginReducer(),
    ) {
    override fun handleEffect(effect: LoginEffect) {
        when (effect) {
            is LoginEffect.Internal.PerformLogin -> {
                performLogin(effect.loginCredentials)
            }

            is LoginEffect.Ui -> super.handleEffect(effect)
        }
    }

    private fun performLogin(loginCredentials: LoginCredentials) {
        loginUseCase(loginCredentials)
            .onEach { result ->
                result.onSuccess {
                    onEvent(LoginEvent.LoginSuccess)
                }.onError {
                    onEvent(LoginEvent.LoginError(it))
                }.onException {
                    onEvent(LoginEvent.LoginError(it.message))
                }
            }
            .launchIn(viewModelScope)
    }
}
