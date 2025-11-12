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
        var passwordError = PasswordValidator.validate(state.password.value)
        var confirmPasswordError: UiText? = null

        if (passwordError == null) {
            val mismatchError =
                PasswordValidator.validateMismatch(
                    password = state.password.value,
                    confirmPassword = state.confirmPassword.value,
                )

            if (mismatchError != null) {
                passwordError = mismatchError
                confirmPasswordError = mismatchError
            }
        }

        val hasError = emailError != null || usernameError != null || passwordError != null

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
