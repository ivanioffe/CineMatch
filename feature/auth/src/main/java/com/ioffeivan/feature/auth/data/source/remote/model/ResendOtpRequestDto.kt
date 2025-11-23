package com.ioffeivan.feature.auth.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResendOtpRequestDto(
    @SerialName("email")
    val email: String,
)
