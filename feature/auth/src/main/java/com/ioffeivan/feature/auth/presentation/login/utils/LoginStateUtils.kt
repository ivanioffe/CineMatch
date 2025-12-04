package com.ioffeivan.feature.auth.presentation.login.utils

import com.ioffeivan.feature.auth.presentation.login.LoginState
import com.ioffeivan.feature.auth.presentation.utils.EmailState
import com.ioffeivan.feature.auth.presentation.utils.INVALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.INVALID_PASSWORD_LENGTH
import com.ioffeivan.feature.auth.presentation.utils.PasswordState
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.VALID_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors

internal val loginValidState =
    LoginState.initial().copy(
        email = EmailState(VALID_EMAIL),
        password = PasswordState(VALID_PASSWORD),
    )

internal val loginLoadingState =
    LoginState.initial().copy(
        isLoading = true,
    )

internal val loginInvalidState =
    LoginState.initial().copy(
        email =
            EmailState(
                value = INVALID_EMAIL,
                isError = true,
                errorMessage = ValidationErrors.emailInvalid,
            ),
        password =
            PasswordState(
                value = INVALID_PASSWORD_LENGTH,
                visibility = false,
                isError = true,
                errorMessage =
                    ValidationErrors.passwordInvalidLength(
                        min = PasswordValidator.MIN_LENGTH,
                        max = PasswordValidator.MAX_LENGTH,
                    ),
            ),
    )
