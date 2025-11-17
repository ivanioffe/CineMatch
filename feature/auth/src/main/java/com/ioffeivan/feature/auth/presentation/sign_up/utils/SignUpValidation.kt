package com.ioffeivan.feature.auth.presentation.sign_up.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.utils.EmailValidator
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import com.ioffeivan.feature.auth.presentation.utils.UsernameValidator

internal object SignUpValidation {
    sealed class Result {
        data object Success : Result()

        data class Error(
            val emailError: UiText? = null,
            val usernameError: UiText? = null,
            val passwordError: UiText? = null,
            val confirmPasswordError: UiText? = null,
        ) : Result()
    }

    fun validate(state: SignUpState): Result {
        val emailError = EmailValidator.validate(state.email.value)
        val usernameError = UsernameValidator.validate(state.username.value)
        val passwordError = PasswordValidator.validate(state.password.value)
        val confirmPasswordError: UiText? =
            if (passwordError == null) {
                PasswordValidator.validateMismatch(
                    password = state.password.value,
                    confirmPassword = state.confirmPassword.value,
                )
            } else {
                null
            }

        val hasError =
            emailError != null || usernameError != null || passwordError != null || confirmPasswordError != null

        return if (hasError) {
            Result.Error(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
            )
        } else {
            Result.Success
        }
    }
}
