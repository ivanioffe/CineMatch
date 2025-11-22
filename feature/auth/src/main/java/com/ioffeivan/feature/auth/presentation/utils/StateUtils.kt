package com.ioffeivan.feature.auth.presentation.utils

import com.ioffeivan.core.ui.UiText

internal const val VALID_EMAIL = "example@example.com"
internal const val VALID_PASSWORD = "testpassword"
internal const val INVALID_EMAIL = "example@"
internal const val INVALID_PASSWORD_CHARS = "p@ss word"
internal const val INVALID_PASSWORD_LENGTH = "p@ss"

data class EmailState(
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
