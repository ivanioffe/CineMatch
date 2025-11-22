package com.ioffeivan.feature.auth.presentation.login.utils

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.feature.auth.presentation.login.LoginState
import com.ioffeivan.feature.auth.presentation.utils.EmailState
import com.ioffeivan.feature.auth.presentation.utils.INVALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.INVALID_PASSWORD_CHARS
import com.ioffeivan.feature.auth.presentation.utils.PasswordState
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginValidationTest {
    private lateinit var state: LoginState

    @BeforeEach
    fun setUp() {
        state = loginValidState
    }

    @Test
    fun validate_whenValidState_shouldReturnsSuccess() {
        val expected = LoginValidation.Result.Success

        val actual = LoginValidation.validate(state)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenInvalidEmail_shouldReturnsError() {
        val emailErrorState =
            state.copy(
                email = EmailState(INVALID_EMAIL),
            )
        val expected = LoginValidation.Result.Error(emailError = ValidationErrors.emailInvalid)

        val actual = LoginValidation.validate(emailErrorState)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun validate_whenInvalidPassword_shouldReturnsError() {
        val passwordErrorState =
            state.copy(
                password = PasswordState(INVALID_PASSWORD_CHARS),
            )
        val expected =
            LoginValidation.Result.Error(passwordError = ValidationErrors.passwordInvalidChars)

        val actual = LoginValidation.validate(passwordErrorState)

        assertThat(actual).isEqualTo(expected)
    }
}
