package com.ioffeivan.feature.auth.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("authentication_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
)
