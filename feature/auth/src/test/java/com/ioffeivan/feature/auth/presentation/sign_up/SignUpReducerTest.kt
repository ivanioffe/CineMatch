package com.ioffeivan.feature.auth.presentation.sign_up

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SignUpReducerTest {
    private lateinit var reducer: SignUpReducer
    private lateinit var state: SignUpState

    companion object {
        private const val VALID_EMAIL = "example@example.com"
        private const val VALID_USERNAME = "testuser"
        private const val VALID_PASSWORD = "testpassword"
        private const val CONFIRM_PASSWORD = "p@ssw"
        private const val INVALID_EMAIL = "example@"
        private const val INVALID_USERNAME = "user 1234"
        private const val INVALID_PASSWORD = "p@ss"
        private const val DUPLICATE_EMAIL_MESSAGE = "duplicate email"

        private val ERROR_EMAIL_INVALID = UiText.StringResource(R.string.error_email_invalid)
        private val ERROR_USERNAME_INVALID =
            UiText.StringResource(R.string.error_username_invalid_chars)
        private val ERROR_PASSWORD_LENGTH_INVALID =
            UiText.StringResource(
                R.string.error_password_invalid_length,
                arrayOf(PasswordValidator.MIN_LENGTH, PasswordValidator.MAX_LENGTH),
            )
        private val ERROR_EMAIL_DUPLICATE = UiText.StringResource(R.string.error_email_duplicate)
    }

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
                        confirmPassword = SignUpState.PasswordState(value = CONFIRM_PASSWORD),
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
                        email = SignUpState.EmailState(value = VALID_EMAIL),
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
                        password = SignUpState.PasswordState(value = VALID_PASSWORD),
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
                        username = SignUpState.UsernameState(value = VALID_USERNAME),
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
        val validState =
            state.copy(
                email = SignUpState.EmailState(VALID_EMAIL),
                username = SignUpState.UsernameState(VALID_USERNAME),
                password = SignUpState.PasswordState(VALID_PASSWORD),
                confirmPassword = SignUpState.PasswordState(VALID_PASSWORD),
            )
        val signUpCredentials =
            SignUpCredentials(
                email = VALID_EMAIL,
                username = VALID_USERNAME,
                password = VALID_PASSWORD,
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
        val invalidState =
            state.copy(
                email = SignUpState.EmailState(INVALID_EMAIL),
                username = SignUpState.UsernameState(INVALID_USERNAME),
                password = SignUpState.PasswordState(INVALID_PASSWORD),
                confirmPassword = SignUpState.PasswordState(CONFIRM_PASSWORD),
            )
        val event = SignUpEvent.SignUpClick
        val expected =
            createReducerResult(
                state =
                    invalidState.copy(
                        email =
                            invalidState.email.copy(
                                isError = true,
                                errorMessage = ERROR_EMAIL_INVALID,
                            ),
                        username =
                            invalidState.username.copy(
                                isError = true,
                                errorMessage = ERROR_USERNAME_INVALID,
                            ),
                        password =
                            invalidState.password.copy(
                                isError = true,
                                errorMessage = ERROR_PASSWORD_LENGTH_INVALID,
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
                effect = SignUpEffect.Ui.ShowError(ERROR_EMAIL_DUPLICATE),
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
