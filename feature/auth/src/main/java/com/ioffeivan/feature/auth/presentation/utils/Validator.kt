package com.ioffeivan.feature.auth.presentation.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
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
            value.isBlank() ->
                UiText.StringResource(id = R.string.error_email_blank)

            !regex.matcher(value).matches() ->
                UiText.StringResource(id = R.string.error_email_invalid)

            else -> null
        }
    }
}

internal object UsernameValidator : Validator {
    const val MIN_LENGTH = 1
    const val MAX_LENGTH = 500

    override fun validate(value: String): UiText? {
        return when {
            value.isBlank() ->
                UiText.StringResource(id = R.string.error_username_blank)

            value.any { it !in 'a'..'z' && it !in 'A'..'Z' && it !in '0'..'9' } ->
                UiText.StringResource(id = R.string.error_username_invalid_chars)

            value.length !in MIN_LENGTH..MAX_LENGTH ->
                UiText.StringResource(
                    id = R.string.error_username_invalid_length,
                    args = arrayOf(MIN_LENGTH, MAX_LENGTH),
                )

            else -> null
        }
    }
}

internal object PasswordValidator : Validator {
    const val MIN_LENGTH = 8
    const val MAX_LENGTH = 72

    override fun validate(value: String): UiText? {
        return when {
            value.isBlank() ->
                UiText.StringResource(id = R.string.error_password_blank)

            value.any { it == ' ' } ->
                UiText.StringResource(id = R.string.error_invalid_characters)

            value.length !in MIN_LENGTH..MAX_LENGTH ->
                UiText.StringResource(
                    id = R.string.error_password_invalid_length,
                    args = arrayOf(MIN_LENGTH, MAX_LENGTH),
                )

            else -> null
        }
    }

    fun validateMismatch(password: String, confirmPassword: String): UiText? {
        return if (password != confirmPassword) {
            UiText.StringResource(R.string.error_password_mismatch)
        } else {
            null
        }
    }
}
