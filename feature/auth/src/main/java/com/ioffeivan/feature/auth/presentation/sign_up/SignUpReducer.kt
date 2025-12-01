package com.ioffeivan.feature.auth.presentation.sign_up

import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText
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
                                    value = event.confirmPassword,
                                    visibility = previousState.confirmPassword.visibility,
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
                                    value = event.password,
                                    visibility = previousState.password.visibility,
                                ),
                            confirmPassword =
                                PasswordState(
                                    value = previousState.confirmPassword.value,
                                    visibility = previousState.confirmPassword.visibility,
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
                                    value = event.username,
                                ),
                        ),
                )
            }

            SignUpEvent.LoginClick -> {
                ReducerResult(
                    state = previousState,
                    effect = SignUpEffect.NavigateToLogin,
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
                ReducerResult(
                    state = previousState.copy(isLoading = true),
                )
            }

            is SignUpEvent.SignUpError -> {
                val error = SignUpDataError.getError(event.message)

                ReducerResult(
                    state = previousState.copy(isLoading = false),
                    effect = SignUpEffect.ShowError(error),
                )
            }

            SignUpEvent.SignUpSuccess -> {
                ReducerResult(
                    state = previousState.copy(isLoading = false),
                    effect = SignUpEffect.NavigateToVerifyEmail(previousState.email.value),
                )
            }

            is SignUpEvent.ValidationError -> {
                val error = event.error
                val newState =
                    previousState.copy(
                        email =
                            previousState.email.copy(
                                isError = error.emailError != null,
                                errorMessage = error.emailError,
                            ),
                        username =
                            previousState.username.copy(
                                isError = error.usernameError != null,
                                errorMessage = error.usernameError,
                            ),
                        password =
                            previousState.password.copy(
                                isError = error.passwordError != null,
                                errorMessage = error.passwordError,
                            ),
                        confirmPassword =
                            previousState.confirmPassword.copy(
                                isError = error.confirmPasswordError != null,
                                errorMessage = error.confirmPasswordError,
                            ),
                    )

                ReducerResult(
                    state = newState,
                )
            }
        }
    }
}

internal data class SignUpState(
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

internal sealed interface SignUpEvent : Reducer.UiEvent {
    data class EmailChange(val email: String) : SignUpEvent

    data class UsernameChange(val username: String) : SignUpEvent

    data class PasswordChange(val password: String) : SignUpEvent

    data class ConfirmPasswordChange(val confirmPassword: String) : SignUpEvent

    data class PasswordVisibilityToggle(val isConfirm: Boolean) : SignUpEvent

    data object SignUpClick : SignUpEvent

    data object LoginClick : SignUpEvent

    data object SignUpSuccess : SignUpEvent

    data class SignUpError(val message: String?) : SignUpEvent

    data class ValidationError(val error: SignUpValidation.Result.Error) : SignUpEvent
}

internal sealed interface SignUpEffect : Reducer.UiEffect {
    data object NavigateToLogin : SignUpEffect

    data class NavigateToVerifyEmail(val email: String) : SignUpEffect

    data class ShowError(val message: UiText) : SignUpEffect
}
