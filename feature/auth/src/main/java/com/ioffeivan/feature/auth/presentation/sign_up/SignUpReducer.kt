package com.ioffeivan.feature.auth.presentation.sign_up

import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.presentation.sign_up.utils.SignUpDataError
import com.ioffeivan.feature.auth.presentation.sign_up.utils.SignUpValidation
import com.ioffeivan.feature.auth.presentation.utils.EmailState
import com.ioffeivan.feature.auth.presentation.utils.PasswordState
import com.ioffeivan.feature.auth.presentation.utils.UsernameState

internal class SignUpReducer : Reducer<SignUpState, SignUpEvent, SignUpEffect> {
    override fun reduce(
        previousState: SignUpState,
        event: SignUpEvent,
    ): ReducerResult<SignUpState, SignUpEffect> {
        return when (event) {
            is SignUpEvent.ConfirmPasswordChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            confirmPassword =
                                PasswordState(
                                    event.confirmPassword,
                                ),
                        ),
                )
            }

            is SignUpEvent.EmailChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            email =
                                EmailState(
                                    value = event.email,
                                ),
                        ),
                )
            }

            is SignUpEvent.PasswordChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            password =
                                PasswordState(
                                    event.password,
                                ),
                        ),
                )
            }

            is SignUpEvent.UsernameChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            username =
                                UsernameState(
                                    event.username,
                                ),
                        ),
                )
            }

            SignUpEvent.LoginClick -> {
                ReducerResult(
                    state = previousState,
                    effect = SignUpEffect.Ui.NavigateToLogin,
                )
            }

            is SignUpEvent.PasswordVisibilityToggle -> {
                val targetState =
                    if (event.isConfirm) previousState.confirmPassword else previousState.password
                val newVisibility = !targetState.visibility

                val newState =
                    if (event.isConfirm) {
                        previousState.copy(confirmPassword = targetState.copy(visibility = newVisibility))
                    } else {
                        previousState.copy(password = targetState.copy(visibility = newVisibility))
                    }

                ReducerResult(
                    state = newState,
                )
            }

            SignUpEvent.SignUpClick -> {
                val validationResult = SignUpValidation.validate(previousState)

                when (validationResult) {
                    SignUpValidation.Result.Success -> {
                        ReducerResult(
                            state = previousState.copy(isLoading = true),
                            effect =
                                SignUpEffect.Internal.PerformSignUp(
                                    signUpCredentials = previousState.toSignUpCredentials(),
                                ),
                        )
                    }

                    is SignUpValidation.Result.Error -> {
                        val newState =
                            previousState.copy(
                                email =
                                    previousState.email.copy(
                                        isError = validationResult.emailError != null,
                                        errorMessage = validationResult.emailError,
                                    ),
                                username =
                                    previousState.username.copy(
                                        isError = validationResult.usernameError != null,
                                        errorMessage = validationResult.usernameError,
                                    ),
                                password =
                                    previousState.password.copy(
                                        isError = validationResult.passwordError != null,
                                        errorMessage = validationResult.passwordError,
                                    ),
                                confirmPassword =
                                    previousState.confirmPassword.copy(
                                        isError = validationResult.confirmPasswordError != null,
                                        errorMessage = validationResult.confirmPasswordError,
                                    ),
                            )

                        ReducerResult(
                            state = newState,
                        )
                    }
                }
            }

            is SignUpEvent.SignUpError -> {
                val error = SignUpDataError.getError(event.message)

                ReducerResult(
                    state = previousState.copy(isLoading = false),
                    effect = SignUpEffect.Ui.ShowError(error),
                )
            }

            SignUpEvent.SignUpSuccess -> {
                ReducerResult(
                    state = previousState.copy(isLoading = false),
                    effect = SignUpEffect.Ui.NavigateToVerifyEmail,
                )
            }
        }
    }
}

data class SignUpState(
    val email: EmailState,
    val username: UsernameState,
    val password: PasswordState,
    val confirmPassword: PasswordState,
    val isLoading: Boolean,
) : Reducer.UiState {
    fun isFilled() =
        email.value.isNotBlank() &&
            username.value.isNotBlank() &&
            password.value.isNotBlank() &&
            confirmPassword.value.isNotBlank()

    companion object {
        fun initial(): SignUpState {
            return SignUpState(
                email = EmailState(),
                username = UsernameState(),
                password = PasswordState(),
                confirmPassword = PasswordState(),
                isLoading = false,
            )
        }
    }
}

sealed interface SignUpEvent : Reducer.UiEvent {
    data class EmailChange(val email: String) : SignUpEvent

    data class UsernameChange(val username: String) : SignUpEvent

    data class PasswordChange(val password: String) : SignUpEvent

    data class ConfirmPasswordChange(val confirmPassword: String) : SignUpEvent

    data class PasswordVisibilityToggle(val isConfirm: Boolean) : SignUpEvent

    data object SignUpClick : SignUpEvent

    data object LoginClick : SignUpEvent

    data object SignUpSuccess : SignUpEvent

    data class SignUpError(val message: String?) : SignUpEvent
}

sealed interface SignUpEffect : Reducer.UiEffect {
    sealed interface Ui : SignUpEffect {
        data object NavigateToLogin : Ui

        data object NavigateToVerifyEmail : Ui

        data class ShowError(val message: UiText) : Ui
    }

    sealed interface Internal : SignUpEffect {
        data class PerformSignUp(val signUpCredentials: SignUpCredentials) : Internal
    }
}
