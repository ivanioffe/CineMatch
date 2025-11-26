package com.ioffeivan.feature.auth.presentation.login

import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.presentation.login.utils.LoginDataError
import com.ioffeivan.feature.auth.presentation.login.utils.LoginValidation
import com.ioffeivan.feature.auth.presentation.utils.EmailState
import com.ioffeivan.feature.auth.presentation.utils.PasswordState

internal class LoginReducer : Reducer<LoginState, LoginEvent, LoginEffect> {
    override fun reduce(
        previousState: LoginState,
        event: LoginEvent,
    ): ReducerResult<LoginState, LoginEffect> {
        return when (event) {
            is LoginEvent.EmailChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            email =
                                EmailState(
                                    event.email,
                                ),
                        ),
                )
            }

            LoginEvent.LoginClick -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = true,
                        ),
                )
            }

            is LoginEvent.LoginError -> {
                val error = LoginDataError.getError(event.message)

                ReducerResult(
                    state = previousState.copy(isLoading = false),
                    effect = LoginEffect.ShowError(error),
                )
            }

            LoginEvent.LoginSuccess -> {
                ReducerResult(
                    state = previousState.copy(isLoading = false),
                    effect = LoginEffect.NavigateToMain,
                )
            }

            is LoginEvent.PasswordChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            password =
                                PasswordState(
                                    value = event.password,
                                    visibility = previousState.password.visibility,
                                ),
                        ),
                )
            }

            LoginEvent.PasswordVisibilityToggle -> {
                val newVisibility = !previousState.password.visibility
                val newState =
                    previousState.copy(password = previousState.password.copy(visibility = newVisibility))

                ReducerResult(
                    state = newState,
                )
            }

            LoginEvent.SignUpClick -> {
                ReducerResult(
                    state = previousState,
                    effect = LoginEffect.NavigateToSignUp,
                )
            }

            is LoginEvent.ValidationError -> {
                val error = event.error
                val newState =
                    previousState.copy(
                        email =
                            previousState.email.copy(
                                isError = error.emailError != null,
                                errorMessage = error.emailError,
                            ),
                        password =
                            previousState.password.copy(
                                isError = error.passwordError != null,
                                errorMessage = error.passwordError,
                            ),
                    )

                ReducerResult(
                    state = newState,
                )
            }
        }
    }
}

internal data class LoginState(
    val email: EmailState,
    val password: PasswordState,
    val isLoading: Boolean,
) : Reducer.UiState {
    fun isFilled() = email.value.isNotBlank() && password.value.isNotBlank()

    companion object {
        fun initial(): LoginState {
            return LoginState(
                email = EmailState(),
                password = PasswordState(),
                isLoading = false,
            )
        }
    }
}

internal sealed interface LoginEvent : Reducer.UiEvent {
    data class EmailChange(val email: String) : LoginEvent

    data class PasswordChange(val password: String) : LoginEvent

    data object PasswordVisibilityToggle : LoginEvent

    data object LoginClick : LoginEvent

    data object SignUpClick : LoginEvent

    data object LoginSuccess : LoginEvent

    data class LoginError(val message: String?) : LoginEvent

    data class ValidationError(val error: LoginValidation.Result.Error) : LoginEvent
}

internal sealed interface LoginEffect : Reducer.UiEffect {
    data object NavigateToSignUp : LoginEffect

    data object NavigateToMain : LoginEffect

    data class ShowError(val message: UiText) : LoginEffect
}
