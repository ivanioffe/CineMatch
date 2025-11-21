package com.ioffeivan.feature.auth.presentation.login

import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.presentation.login.utils.LoginDataError
import com.ioffeivan.feature.auth.presentation.login.utils.LoginValidation

class LoginReducer : Reducer<LoginState, LoginEvent, LoginEffect> {
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
                                LoginState.EmailState(
                                    event.email,
                                ),
                        ),
                )
            }

            LoginEvent.LoginClick -> {
                val validationResult = LoginValidation.validate(previousState)

                when (validationResult) {
                    LoginValidation.Result.Success -> {
                        ReducerResult(
                            state = previousState.copy(isLoading = true),
                            effect =
                                LoginEffect.Internal.PerformLogin(
                                    loginCredentials = previousState.toLoginCredentials(),
                                ),
                        )
                    }

                    is LoginValidation.Result.Error -> {
                        val newState =
                            previousState.copy(
                                email =
                                    previousState.email.copy(
                                        isError = validationResult.emailError != null,
                                        errorMessage = validationResult.emailError,
                                    ),
                                password =
                                    previousState.password.copy(
                                        isError = validationResult.passwordError != null,
                                        errorMessage = validationResult.passwordError,
                                    ),
                            )

                        ReducerResult(
                            state = newState,
                        )
                    }
                }
            }

            is LoginEvent.LoginError -> {
                val error = LoginDataError.getError(event.message)

                ReducerResult(
                    state = previousState.copy(isLoading = false),
                    effect = LoginEffect.Ui.ShowError(error),
                )
            }

            LoginEvent.LoginSuccess -> {
                ReducerResult(
                    state = previousState.copy(isLoading = false),
                    effect = LoginEffect.Ui.NavigateToMain,
                )
            }

            is LoginEvent.PasswordChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            password =
                                LoginState.PasswordState(
                                    event.password,
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
                    effect = LoginEffect.Ui.NavigateToSignUp,
                )
            }
        }
    }
}

data class LoginState(
    val email: EmailState,
    val password: PasswordState,
    val isLoading: Boolean,
) : Reducer.UiState {
    data class EmailState(
        val value: String = "",
        val isError: Boolean = false,
        val errorMessage: UiText? = null,
    )

    data class PasswordState(
        val value: String = "",
        val visibility: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: UiText? = null,
    )

    fun isFilledState() = email.value.isNotBlank() && password.value.isNotBlank()

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

sealed interface LoginEvent : Reducer.UiEvent {
    data class EmailChange(val email: String) : LoginEvent

    data class PasswordChange(val password: String) : LoginEvent

    data object PasswordVisibilityToggle : LoginEvent

    data object LoginClick : LoginEvent

    data object SignUpClick : LoginEvent

    data object LoginSuccess : LoginEvent

    data class LoginError(val message: String?) : LoginEvent
}

sealed interface LoginEffect : Reducer.UiEffect {
    sealed interface Ui : LoginEffect {
        data object NavigateToSignUp : Ui

        data object NavigateToMain : Ui

        data class ShowError(val message: UiText) : Ui
    }

    sealed interface Internal : LoginEffect {
        data class PerformLogin(val loginCredentials: LoginCredentials) : Internal
    }
}
