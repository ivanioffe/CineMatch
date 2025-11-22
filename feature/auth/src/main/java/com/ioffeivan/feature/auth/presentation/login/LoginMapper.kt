package com.ioffeivan.feature.auth.presentation.login

import com.ioffeivan.feature.auth.domain.model.LoginCredentials

fun LoginState.toLoginCredentials(): LoginCredentials {
    return LoginCredentials(
        email = email.value,
        password = password.value,
    )
}
