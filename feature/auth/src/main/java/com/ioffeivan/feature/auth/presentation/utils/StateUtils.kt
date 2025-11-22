package com.ioffeivan.feature.auth.presentation.utils

import com.ioffeivan.core.ui.UiText

internal const val VALID_EMAIL = "example@example.com"
internal const val VALID_USERNAME = "username"
internal const val VALID_PASSWORD = "testpassword"
internal const val CONFIRM_PASSWORD = "testpassword"

internal const val INVALID_EMAIL = "example@"
internal const val INVALID_USERNAME_CHARS = "user name"
internal const val INVALID_PASSWORD_CHARS = "p@ss word"
internal const val INVALID_PASSWORD_LENGTH = "p@ss"
internal const val MISMATCH_CONFIRM_PASSWORD = "mismatchConfirmPassword"

data class EmailState(
    val value: String = "",
    val isError: Boolean = false,
    val errorMessage: UiText? = null,
)

data class UsernameState(
    val value: String = "",
    val isError: Boolean = false,
    val errorMessage: UiText? = null,
)

data class PasswordState(
    val value: String = "",
    val visibility: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: UiText? = null,
)
