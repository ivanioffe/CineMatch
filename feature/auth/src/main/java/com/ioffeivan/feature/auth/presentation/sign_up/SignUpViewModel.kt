package com.ioffeivan.feature.auth.presentation.sign_up

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.common.result.onError
import com.ioffeivan.core.common.result.onException
import com.ioffeivan.core.common.result.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel<SignUpState, SignUpEvent, SignUpEffect>(
        initialState = SignUpState.initial(),
        reducer = SignUpReducer(),
    ) {
    override fun handleEffect(effect: SignUpEffect) {
        when (effect) {
            is SignUpEffect.Internal.PerformSignUp ->
                performSignUp(effect.signUpCredentials)

            is SignUpEffect.Ui -> super.handleEffect(effect)
        }
    }

    private fun performSignUp(signUpCredentials: SignUpCredentials) {
        signUpUseCase(signUpCredentials)
            .onEach { result ->
                result.onSuccess {
                    onEvent(SignUpEvent.SignUpSuccess)
                }.onError {
                    onEvent(SignUpEvent.SignUpError(it))
                }.onException {
                    onEvent(SignUpEvent.SignUpError(it.message))
                }
            }
            .launchIn(viewModelScope)
    }
}
