package com.ioffeivan.feature.auth.presentation.utils

import com.ioffeivan.core.ui.UiText
import java.util.regex.Pattern

internal interface Validator {
    fun validate(value: String): UiText?
}

internal object EmailValidator : Validator {
    private val regex =
        Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+",
        )

    override fun validate(value: String): UiText? {
        return when {
            value.isBlank() -> ValidationErrors.emailBlank
            !regex.matcher(value).matches() -> ValidationErrors.emailInvalid
            else -> null
        }
    }
}

internal object UsernameValidator : Validator {
    const val MIN_LENGTH = 1
    const val MAX_LENGTH = 500

    override fun validate(value: String): UiText? {
        return when {
            value.isBlank() -> ValidationErrors.usernameBlank

            value.any { it !in 'a'..'z' && it !in 'A'..'Z' && it !in '0'..'9' } ->
                ValidationErrors.usernameInvalidChars

            value.length !in MIN_LENGTH..MAX_LENGTH ->
                ValidationErrors.usernameInvalidLength(min = MIN_LENGTH, max = MAX_LENGTH)

            else -> null
        }
    }
}

internal object PasswordValidator : Validator {
    const val MIN_LENGTH = 8
    const val MAX_LENGTH = 72

    override fun validate(value: String): UiText? {
        return when {
            value.isBlank() -> ValidationErrors.passwordBlank

            value.any { it == ' ' } -> ValidationErrors.passwordInvalidChars

            value.length !in MIN_LENGTH..MAX_LENGTH ->
                ValidationErrors.passwordInvalidLength(min = MIN_LENGTH, max = MAX_LENGTH)

            else -> null
        }
    }

    fun validateMismatch(password: String, confirmPassword: String): UiText? {
        return if (password != confirmPassword) {
            ValidationErrors.passwordMismatch
        } else {
            null
        }
    }
}
