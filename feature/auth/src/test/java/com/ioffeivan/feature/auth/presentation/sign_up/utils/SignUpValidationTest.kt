package com.ioffeivan.feature.auth.presentation.sign_up.utils

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SignUpValidationTest {
    private lateinit var state: SignUpState

    companion object {
        private const val VALID_PASSWORD = "password"
        private const val INVALID_EMAIL = "email"
        private const val INVALID_USERNAME = "user name"
        private const val INVALID_PASSWORD_SHORT = "pass"
        private const val CONFIRM_PASSWORD = "confirmPassword"

        private val emailError = UiText.StringResource(R.string.error_email_invalid)
        private val usernameError = UiText.StringResource(R.string.error_username_invalid_chars)
        private val passwordError =
            UiText.StringResource(
                id = R.string.error_password_invalid_length,
                args = arrayOf(PasswordValidator.MIN_LENGTH, PasswordValidator.MAX_LENGTH),
            )
        private val mismatchPasswordsError = UiText.StringResource(R.string.error_password_mismatch)
    }

    @BeforeEach
    fun setUp() {
        state =
            SignUpState.initial().copy(
                email = SignUpState.EmailState("test@example.com"),
                username = SignUpState.UsernameState("validuser"),
                password = SignUpState.PasswordState("password123"),
                confirmPassword = SignUpState.PasswordState("password123"),
            )
    }

    @Test
    fun validate_whenValidState_shouldReturnsSuccess() {
        val expected = SignUpValidation.Result.Success

        val actual = SignUpValidation.validate(state)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenInvalidEmail_shouldReturnsError() {
        val emailErrorState =
            state.copy(
                email = SignUpState.EmailState(INVALID_EMAIL),
            )
        val expected = SignUpValidation.Result.Error(emailError = emailError)

        val actual = SignUpValidation.validate(emailErrorState)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenInvalidUsername_shouldReturnsError() {
        val usernameErrorState =
            state.copy(
                username = SignUpState.UsernameState(INVALID_USERNAME),
            )
        val expected = SignUpValidation.Result.Error(usernameError = usernameError)

        val actual = SignUpValidation.validate(usernameErrorState)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenInvalidPassword_shouldReturnsError() {
        val passwordErrorState =
            state.copy(
                password = SignUpState.PasswordState(INVALID_PASSWORD_SHORT),
            )
        val expected = SignUpValidation.Result.Error(passwordError = passwordError)

        val actual = SignUpValidation.validate(passwordErrorState)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenMismatchPasswords_shouldReturnsError() {
        val passwordErrorState =
            state.copy(
                password = SignUpState.PasswordState(VALID_PASSWORD),
                confirmPassword = SignUpState.PasswordState(CONFIRM_PASSWORD),
            )
        val expected =
            SignUpValidation.Result.Error(
                confirmPasswordError = mismatchPasswordsError,
            )

        val actual = SignUpValidation.validate(passwordErrorState)

        assertThat(actual).isEqualTo(expected)
    }
}
