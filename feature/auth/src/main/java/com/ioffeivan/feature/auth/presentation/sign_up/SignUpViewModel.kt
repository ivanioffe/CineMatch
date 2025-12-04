package com.ioffeivan.feature.auth.presentation.sign_up

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.common.result.onError
import com.ioffeivan.core.common.result.onException
import com.ioffeivan.core.common.result.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.usecase.SignUpUseCase
import com.ioffeivan.feature.auth.presentation.sign_up.utils.SignUpValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel<SignUpState, SignUpEvent, SignUpEffect>(
        initialState = SignUpState.initial(),
        reducer = SignUpReducer(),
    ) {
    override fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUpClick -> onSignUpClickEvent()
            else -> sendEvent(event)
        }
    }

    private fun onSignUpClickEvent() {
        val validationResult = SignUpValidation.validate(state.value)

        when (validationResult) {
            SignUpValidation.Result.Success -> {
                sendEvent(SignUpEvent.SignUpClick)
                signUp(state.value.toSignUpCredentials())
            }

            is SignUpValidation.Result.Error -> {
                sendEvent(SignUpEvent.ValidationError(validationResult))
            }
        }
    }

    private fun signUp(signUpCredentials: SignUpCredentials) {
        signUpUseCase(signUpCredentials)
            .onEach { result ->
                result.onSuccess {
                    sendEvent(SignUpEvent.SignUpSuccess)
                }.onError {
                    sendEvent(SignUpEvent.SignUpError(it))
                }.onException {
                    sendEvent(SignUpEvent.SignUpError(it.message))
                }
            }
            .launchIn(viewModelScope)
    }
}
