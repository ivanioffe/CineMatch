package com.ioffeivan.feature.auth.presentation.sign_up

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.feature.auth.presentation.sign_up.utils.DUPLICATE_EMAIL_MESSAGE
import com.ioffeivan.feature.auth.presentation.sign_up.utils.ERROR_DUPLICATE_EMAIL
import com.ioffeivan.feature.auth.presentation.sign_up.utils.SignUpValidation
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpInvalidState
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpLoadingState
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpValidState
import com.ioffeivan.feature.auth.presentation.utils.CONFIRM_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.EmailState
import com.ioffeivan.feature.auth.presentation.utils.INVALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.INVALID_PASSWORD_LENGTH
import com.ioffeivan.feature.auth.presentation.utils.INVALID_USERNAME_CHARS
import com.ioffeivan.feature.auth.presentation.utils.PasswordState
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import com.ioffeivan.feature.auth.presentation.utils.UsernameState
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.VALID_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.VALID_USERNAME
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SignUpReducerTest {
    private lateinit var reducer: SignUpReducer
    private lateinit var state: SignUpState

    @BeforeEach
    fun setUp() {
        reducer = SignUpReducer()
        state = SignUpState.initial()
    }

    @Test
    fun confirmPasswordChange_shouldUpdateConfirmPasswordValueInState() {
        val event = SignUpEvent.ConfirmPasswordChange(CONFIRM_PASSWORD)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        confirmPassword = PasswordState(value = CONFIRM_PASSWORD),
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun emailChange_shouldUpdateEmailValueInState() {
        val event = SignUpEvent.EmailChange(VALID_EMAIL)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        email = EmailState(value = VALID_EMAIL),
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun passwordChange_shouldUpdatePasswordValueInState() {
        val event = SignUpEvent.PasswordChange(VALID_PASSWORD)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        password = PasswordState(value = VALID_PASSWORD),
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun usernameChange_shouldUpdateUsernameValueInState() {
        val event = SignUpEvent.UsernameChange(VALID_USERNAME)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        username = UsernameState(value = VALID_USERNAME),
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun loginClick_shouldNotUpdateStateAndEmitNavigateToLoginEffect() {
        val event = SignUpEvent.LoginClick
        val expected =
            createReducerResult(
                state = state,
                effect = SignUpEffect.NavigateToLogin,
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun passwordVisibilityToggle_whenIsConfirmFalse_shouldTogglePasswordVisibility() {
        val event = SignUpEvent.PasswordVisibilityToggle(isConfirm = false)
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
    fun passwordVisibilityToggle_whenIsConfirmTrue_shouldToggleConfirmPasswordVisibility() {
        val event = SignUpEvent.PasswordVisibilityToggle(isConfirm = true)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        confirmPassword = state.confirmPassword.copy(visibility = true),
                    ),
                effect = null,
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun signUpClick_shouldSetIsLoadingTrueInState() {
        val validState = signUpValidState
        val event = SignUpEvent.SignUpClick
        val expected =
            createReducerResult(
                state = validState.copy(isLoading = true),
            )

        val actual = reducer.reduce(validState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validationError_shouldSetErrorsInState() {
        val invalidState =
            state.copy(
                email = EmailState(value = INVALID_EMAIL),
                username = UsernameState(value = INVALID_USERNAME_CHARS),
                password = PasswordState(value = INVALID_PASSWORD_LENGTH),
            )
        val event =
            SignUpEvent.ValidationError(
                SignUpValidation.Result.Error(
                    emailError = ValidationErrors.emailInvalid,
                    usernameError = ValidationErrors.usernameInvalidChars,
                    passwordError =
                        ValidationErrors.passwordInvalidLength(
                            PasswordValidator.MIN_LENGTH,
                            PasswordValidator.MAX_LENGTH,
                        ),
                ),
            )
        val expected =
            createReducerResult(
                state = signUpInvalidState,
            )

        val actual = reducer.reduce(invalidState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun signUpError_shouldSetIsLoadingFalseInStateAndEmitShowErrorEffect() {
        val loadingState = signUpLoadingState
        val event = SignUpEvent.SignUpError(DUPLICATE_EMAIL_MESSAGE)
        val expected =
            createReducerResult(
                state = loadingState.copy(isLoading = false),
                effect = SignUpEffect.ShowError(ERROR_DUPLICATE_EMAIL),
            )

        val actual = reducer.reduce(loadingState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun signUpSuccess_shouldSetIsLoadingFalseInStateAndEmitNavigateToVerifyEmailEffect() {
        val loadingState =
            signUpLoadingState.copy(
                email = EmailState(value = VALID_EMAIL),
            )
        val event = SignUpEvent.SignUpSuccess
        val expected =
            createReducerResult(
                state = loadingState.copy(isLoading = false),
                effect = SignUpEffect.NavigateToVerifyEmail(VALID_EMAIL),
            )

        val actual = reducer.reduce(loadingState, event)

        assertThat(actual).isEqualTo(expected)
    }

    private fun createReducerResult(
        state: SignUpState,
        effect: SignUpEffect? = null,
    ): ReducerResult<SignUpState, SignUpEffect> {
        return ReducerResult(state = state, effect = effect)
    }
}
