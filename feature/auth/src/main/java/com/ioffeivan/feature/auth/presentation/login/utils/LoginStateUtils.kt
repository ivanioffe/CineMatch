package com.ioffeivan.feature.auth.presentation.login.utils

import com.ioffeivan.feature.auth.presentation.login.LoginState
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors

internal const val VALID_EMAIL = "example@example.com"
internal const val VALID_PASSWORD = "testpassword"
internal const val INVALID_EMAIL = "example@"
internal const val INVALID_PASSWORD_CHARS = "p@ss word"

internal const val INVALID_PASSWORD_LENGTH = "p@ss"

internal val loginValidState =
    LoginState.initial().copy(
        email = LoginState.EmailState(VALID_EMAIL),
        password = LoginState.PasswordState(VALID_PASSWORD),
    )

internal val loginLoadingState =
    LoginState.initial().copy(
        isLoading = true,
    )

internal val loginInvalidState =
    LoginState.initial().copy(
        email =
            LoginState.EmailState(
                value = INVALID_EMAIL,
                isError = true,
                errorMessage = ValidationErrors.emailInvalid,
            ),
        password =
            LoginState.PasswordState(
                value = INVALID_PASSWORD_LENGTH,
                visibility = true,
                isError = true,
                errorMessage =
                    ValidationErrors.passwordInvalidLength(
                        min = PasswordValidator.MIN_LENGTH,
                        max = PasswordValidator.MAX_LENGTH,
                    ),
            ),
    )
