package com.ioffeivan.feature.auth.presentation.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R

internal object ValidationErrors {
    val emailBlank = UiText.StringResource(R.string.error_email_blank)
    val emailInvalid = UiText.StringResource(R.string.error_email_invalid)

    val usernameBlank = UiText.StringResource(R.string.error_username_blank)
    val usernameInvalidChars = UiText.StringResource(R.string.error_username_invalid_chars)

    val passwordBlank = UiText.StringResource(R.string.error_password_blank)
    val passwordInvalidChars = UiText.StringResource(R.string.error_invalid_characters)

    val passwordMismatch = UiText.StringResource(R.string.error_password_mismatch)

    fun usernameInvalidLength(min: Int, max: Int) =
        UiText.StringResource(R.string.error_username_invalid_length, arrayOf(min, max))

    fun passwordInvalidLength(min: Int, max: Int) =
        UiText.StringResource(R.string.error_password_invalid_length, arrayOf(min, max))
}
