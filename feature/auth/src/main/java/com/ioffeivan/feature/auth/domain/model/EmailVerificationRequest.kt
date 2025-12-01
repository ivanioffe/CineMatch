package com.ioffeivan.feature.auth.domain.model

data class EmailVerificationRequest(
    val otp: String,
)
