package com.ioffeivan.feature.auth.data.mapper

import com.ioffeivan.feature.auth.data.source.remote.model.LoginCredentialsDto
import com.ioffeivan.feature.auth.data.source.remote.model.SignUpCredentialsDto
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials

fun LoginCredentials.toDto(): LoginCredentialsDto =
    LoginCredentialsDto(
        email = email,
        password = password,
    )

fun SignUpCredentials.toDto(): SignUpCredentialsDto =
    SignUpCredentialsDto(
        email = email,
        username = username,
        password = password,
    )
