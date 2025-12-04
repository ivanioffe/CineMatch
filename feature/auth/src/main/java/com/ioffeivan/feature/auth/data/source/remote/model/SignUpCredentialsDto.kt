package com.ioffeivan.feature.auth.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpCredentialsDto(
    @SerialName("email") val email: String,
    @SerialName("name") val username: String,
    @SerialName("password") val password: String,
)
