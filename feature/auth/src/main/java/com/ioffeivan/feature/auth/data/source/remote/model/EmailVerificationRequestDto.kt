package com.ioffeivan.feature.auth.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailVerificationRequestDto(
    @SerialName("token")
    val otp: String,
)
