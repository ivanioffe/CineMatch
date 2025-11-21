package com.ioffeivan.feature.auth.presentation.login

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.presentation.login.utils.ERROR_INVALID_AUTHENTICATION_CREDENTIALS
import com.ioffeivan.feature.auth.presentation.login.utils.INVALID_AUTHENTICATION_CREDENTIALS_MESSAGE
import com.ioffeivan.feature.auth.presentation.login.utils.INVALID_EMAIL
import com.ioffeivan.feature.auth.presentation.login.utils.INVALID_PASSWORD_LENGTH
import com.ioffeivan.feature.auth.presentation.login.utils.VALID_EMAIL
import com.ioffeivan.feature.auth.presentation.login.utils.VALID_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginReducerTest {
    private lateinit var reducer: LoginReducer
    private lateinit var state: LoginState

    @BeforeEach
    fun setUp() {
        reducer = LoginReducer()
        state = LoginState.initial()
    }

    @Test
    fun emailChange_shouldUpdateEmailValueInState() {
        val event = LoginEvent.EmailChange(VALID_EMAIL)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        email = LoginState.EmailState(value = VALID_EMAIL),
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun loginClick_whenStateIsValid_shouldSetIsLoadingTrueInStateAndEmitPerformLoginEffect() {
        val state =
            state.copy(
                email = LoginState.EmailState(VALID_EMAIL),
                password = LoginState.PasswordState(VALID_PASSWORD),
            )
        val loginCredentials =
            LoginCredentials(
                email = VALID_EMAIL,
                password = VALID_PASSWORD,
            )
        val event = LoginEvent.LoginClick
        val expected =
            createReducerResult(
                state = state.copy(isLoading = true),
                effect = LoginEffect.Internal.PerformLogin(loginCredentials),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun loginClick_whenStateIsInvalid_shouldSetErrorsInState() {
        val invalidState =
            state.copy(
                email = LoginState.EmailState(INVALID_EMAIL),
                password = LoginState.PasswordState(INVALID_PASSWORD_LENGTH),
            )
        val event = LoginEvent.LoginClick
        val expected =
            createReducerResult(
                state =
                    invalidState.copy(
                        email =
                            invalidState.email.copy(
                                isError = true,
                                errorMessage = ValidationErrors.emailInvalid,
                            ),
                        password =
                            invalidState.password.copy(
                                isError = true,
                                errorMessage =
                                    ValidationErrors.passwordInvalidLength(
                                        min = PasswordValidator.MIN_LENGTH,
                                        max = PasswordValidator.MAX_LENGTH,
                                    ),
                            ),
                    ),
            )

        val actual = reducer.reduce(invalidState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun loginError_shouldSetIsLoadingFalseInStateAndEmitShowErrorEffect() {
        val loadingState = state.copy(isLoading = true)
        val event = LoginEvent.LoginError(INVALID_AUTHENTICATION_CREDENTIALS_MESSAGE)
        val expected =
            createReducerResult(
                state = loadingState.copy(isLoading = false),
                effect = LoginEffect.Ui.ShowError(ERROR_INVALID_AUTHENTICATION_CREDENTIALS),
            )

        val actual = reducer.reduce(loadingState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun loginSuccess_shouldSetIsLoadingFalseInStateAndEmitNavigateToVerifyEmailEffect() {
        val loadingState = state.copy(isLoading = true)
        val event = LoginEvent.LoginSuccess
        val expected =
            createReducerResult(
                state = loadingState.copy(isLoading = false),
                effect = LoginEffect.Ui.NavigateToMain,
            )

        val actual = reducer.reduce(loadingState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun passwordChange_shouldUpdatePasswordValueInState() {
        val event = LoginEvent.PasswordChange(VALID_PASSWORD)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        password = LoginState.PasswordState(VALID_PASSWORD),
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun passwordVisibilityToggle_shouldTogglePasswordVisibility() {
        val event = LoginEvent.PasswordVisibilityToggle
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        password = state.password.copy(visibility = true),
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun signUpClick_shouldNotUpdateStateAndEmitNavigateToSignUpEffect() {
        val event = LoginEvent.SignUpClick
        val expected =
            createReducerResult(
                state = state,
                effect = LoginEffect.Ui.NavigateToSignUp,
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    private fun createReducerResult(
        state: LoginState,
        effect: LoginEffect? = null,
    ): ReducerResult<LoginState, LoginEffect> {
        return ReducerResult(state = state, effect = effect)
    }
}
