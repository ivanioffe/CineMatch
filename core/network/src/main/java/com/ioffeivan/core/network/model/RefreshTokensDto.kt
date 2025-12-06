package com.ioffeivan.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokensResponseDto(
    @SerialName("authentication_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
)

@Serializable
data class RefreshTokensRequestDto(
    @SerialName("refresh_token") val refreshToken: String,
)
