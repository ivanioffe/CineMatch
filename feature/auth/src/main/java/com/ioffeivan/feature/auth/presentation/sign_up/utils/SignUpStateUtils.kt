package com.ioffeivan.feature.auth.presentation.sign_up.utils

import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.utils.EmailState
import com.ioffeivan.feature.auth.presentation.utils.INVALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.INVALID_PASSWORD_LENGTH
import com.ioffeivan.feature.auth.presentation.utils.INVALID_USERNAME_CHARS
import com.ioffeivan.feature.auth.presentation.utils.PasswordState
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator
import com.ioffeivan.feature.auth.presentation.utils.UsernameState
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.VALID_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.VALID_USERNAME
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors

internal val signUpValidState =
    SignUpState.initial().copy(
        email = EmailState(VALID_EMAIL),
        username = UsernameState(VALID_USERNAME),
        password = PasswordState(VALID_PASSWORD),
        confirmPassword = PasswordState(VALID_PASSWORD),
    )

internal val signUpLoadingState =
    SignUpState.initial().copy(
        isLoading = true,
    )

internal val signUpInvalidState =
    SignUpState.initial().copy(
        email =
            EmailState(
                value = INVALID_EMAIL,
                isError = true,
                errorMessage = ValidationErrors.emailInvalid,
            ),
        username =
            UsernameState(
                value = INVALID_USERNAME_CHARS,
                isError = true,
                errorMessage = ValidationErrors.usernameInvalidChars,
            ),
        password =
            PasswordState(
                value = INVALID_PASSWORD_LENGTH,
                visibility = true,
                isError = true,
                errorMessage =
                    ValidationErrors.passwordInvalidLength(
                        min = PasswordValidator.MIN_LENGTH,
                        max = PasswordValidator.MAX_LENGTH,
                    ),
            ),
        confirmPassword =
            PasswordState(
                value = "",
                visibility = true,
                isError = false,
                errorMessage = null,
            ),
    )
