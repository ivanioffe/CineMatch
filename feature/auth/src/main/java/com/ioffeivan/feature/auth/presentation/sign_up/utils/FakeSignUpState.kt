package com.ioffeivan.feature.auth.presentation.sign_up.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator

internal val signUpStateFilled =
    SignUpState.initial().copy(
        email = SignUpState.EmailState("example@exmaple.com"),
        username = SignUpState.UsernameState("username"),
        password = SignUpState.PasswordState("password"),
        confirmPassword = SignUpState.PasswordState("password"),
    )

internal val signUpStateLoading =
    SignUpState.initial().copy(
        isLoading = true,
    )

internal val signUpStateValidationError =
    SignUpState.initial().copy(
        email =
            SignUpState.EmailState(
                value = "example@",
                isError = true,
                errorMessage = UiText.StringResource(R.string.error_email_invalid),
            ),
        username =
            SignUpState.UsernameState(
                value = "user name",
                isError = true,
                errorMessage = UiText.StringResource(R.string.error_username_invalid_chars),
            ),
        password =
            SignUpState.PasswordState(
                value = "pass",
                visibility = true,
                isError = true,
                errorMessage =
                    UiText.StringResource(
                        R.string.error_password_invalid_length,
                        arrayOf(
                            PasswordValidator.MIN_LENGTH,
                            PasswordValidator.MAX_LENGTH,
                        ),
                    ),
            ),
        confirmPassword =
            SignUpState.PasswordState(
                value = "Password",
                visibility = true,
                isError = false,
                errorMessage = null,
            ),
    )
