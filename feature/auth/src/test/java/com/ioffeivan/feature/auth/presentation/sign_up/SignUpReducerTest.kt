package com.ioffeivan.feature.auth.presentation.sign_up

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.presentation.sign_up.utils.DUPLICATE_EMAIL_MESSAGE
import com.ioffeivan.feature.auth.presentation.sign_up.utils.ERROR_DUPLICATE_EMAIL
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpInvalidState
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpValidState
import com.ioffeivan.feature.auth.presentation.utils.CONFIRM_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.EmailState
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
                effect = SignUpEffect.Ui.NavigateToLogin,
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
    fun signUpClick_whenStateIsValid_shouldSetIsLoadingTrueAndEmitPerformSignUpEffect() {
        val validState = signUpValidState
        val signUpCredentials =
            SignUpCredentials(
                email = validState.email.value,
                username = validState.username.value,
                password = validState.password.value,
            )
        val event = SignUpEvent.SignUpClick
        val expected =
            createReducerResult(
                state = validState.copy(isLoading = true),
                effect = SignUpEffect.Internal.PerformSignUp(signUpCredentials = signUpCredentials),
            )

        val actual = reducer.reduce(validState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun signUpClick_whenStateIsInvalid_shouldSetErrorsInState() {
        val invalidState = signUpInvalidState
        val event = SignUpEvent.SignUpClick
        val expected =
            createReducerResult(
                state =
                    invalidState.copy(
                        email =
                            invalidState.email.copy(
                                isError = true,
                                errorMessage = ValidationErrors.emailInvalid,
                            ),
                        username =
                            invalidState.username.copy(
                                isError = true,
                                errorMessage = ValidationErrors.usernameInvalidChars,
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
                        confirmPassword = invalidState.confirmPassword.copy(errorMessage = null),
                    ),
            )

        val actual = reducer.reduce(invalidState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun signUpError_shouldSetIsLoadingFalseInStateAndEmitShowErrorEffect() {
        val loadingState = state.copy(isLoading = true)
        val event = SignUpEvent.SignUpError(DUPLICATE_EMAIL_MESSAGE)
        val expected =
            createReducerResult(
                state = loadingState.copy(isLoading = false),
                effect = SignUpEffect.Ui.ShowError(ERROR_DUPLICATE_EMAIL),
            )

        val actual = reducer.reduce(loadingState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun signUpSuccess_shouldSetIsLoadingFalseInStateAndEmitNavigateToVerifyEmailEffect() {
        val loadingState = state.copy(isLoading = true)
        val event = SignUpEvent.SignUpSuccess
        val expected =
            createReducerResult(
                state = loadingState.copy(isLoading = false),
                effect = SignUpEffect.Ui.NavigateToVerifyEmail,
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
