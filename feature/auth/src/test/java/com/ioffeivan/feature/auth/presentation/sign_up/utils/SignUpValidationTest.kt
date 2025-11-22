package com.ioffeivan.feature.auth.presentation.sign_up.utils

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.utils.EmailState
import com.ioffeivan.feature.auth.presentation.utils.INVALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.INVALID_PASSWORD_LENGTH
import com.ioffeivan.feature.auth.presentation.utils.INVALID_USERNAME_CHARS
import com.ioffeivan.feature.auth.presentation.utils.MISMATCH_CONFIRM_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.PasswordState
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import com.ioffeivan.feature.auth.presentation.utils.UsernameState
import com.ioffeivan.feature.auth.presentation.utils.VALID_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SignUpValidationTest {
    private lateinit var state: SignUpState

    @BeforeEach
    fun setUp() {
        state = signUpValidState
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
                email = EmailState(INVALID_EMAIL),
            )
        val expected = SignUpValidation.Result.Error(emailError = ValidationErrors.emailInvalid)

        val actual = SignUpValidation.validate(emailErrorState)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenInvalidUsername_shouldReturnsError() {
        val usernameErrorState =
            state.copy(
                username = UsernameState(INVALID_USERNAME_CHARS),
            )
        val expected =
            SignUpValidation.Result.Error(usernameError = ValidationErrors.usernameInvalidChars)

        val actual = SignUpValidation.validate(usernameErrorState)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenInvalidPassword_shouldReturnsError() {
        val passwordErrorState =
            state.copy(
                password = PasswordState(INVALID_PASSWORD_LENGTH),
            )
        val expected =
            SignUpValidation.Result.Error(
                passwordError =
                    ValidationErrors.passwordInvalidLength(
                        min = PasswordValidator.MIN_LENGTH,
                        max = PasswordValidator.MAX_LENGTH,
                    ),
            )

        val actual = SignUpValidation.validate(passwordErrorState)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenMismatchPasswords_shouldReturnsError() {
        val passwordErrorState =
            state.copy(
                password = PasswordState(VALID_PASSWORD),
                confirmPassword = PasswordState(MISMATCH_CONFIRM_PASSWORD),
            )
        val expected =
            SignUpValidation.Result.Error(
                confirmPasswordError = ValidationErrors.passwordMismatch,
            )

        val actual = SignUpValidation.validate(passwordErrorState)

        assertThat(actual).isEqualTo(expected)
    }
}
