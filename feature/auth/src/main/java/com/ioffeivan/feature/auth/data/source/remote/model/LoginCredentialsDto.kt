package com.ioffeivan.feature.auth.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentialsDto(
    val email: String,
    val password: String,
)
