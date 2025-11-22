package com.ioffeivan.feature.auth.presentation.sign_up

import com.ioffeivan.feature.auth.domain.model.SignUpCredentials

fun SignUpState.toSignUpCredentials(): SignUpCredentials {
    return SignUpCredentials(
        email = email.value,
        username = username.value,
        password = password.value,
    )
}
