package com.ioffeivan.feature.auth.presentation.login.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.presentation.login.LoginState
import com.ioffeivan.feature.auth.presentation.utils.EmailValidator
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator

internal object LoginValidation {
    sealed class Result {
        data object Success : Result()

        data class Error(
            val emailError: UiText? = null,
            val passwordError: UiText? = null,
        ) : Result()
    }

    fun validate(state: LoginState): Result {
        val emailError = EmailValidator.validate(state.email.value)
        val passwordError = PasswordValidator.validate(state.password.value)

        val hasError = emailError != null || passwordError != null

        return if (hasError) {
            Result.Error(
                emailError = emailError,
                passwordError = passwordError,
            )
        } else {
            Result.Success
        }
    }
}
