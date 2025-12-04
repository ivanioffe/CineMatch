package com.ioffeivan.feature.auth.presentation.login

import com.ioffeivan.feature.auth.domain.model.LoginCredentials

internal fun LoginState.toLoginCredentials(): LoginCredentials {
    return LoginCredentials(
        email = email.value,
        password = password.value,
    )
}
