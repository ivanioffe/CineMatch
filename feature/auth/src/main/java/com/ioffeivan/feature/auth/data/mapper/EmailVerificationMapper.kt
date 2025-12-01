package com.ioffeivan.feature.auth.data.mapper

import com.ioffeivan.feature.auth.data.source.remote.model.EmailVerificationRequestDto
import com.ioffeivan.feature.auth.data.source.remote.model.ResendOtpRequestDto
import com.ioffeivan.feature.auth.domain.model.EmailVerificationRequest
import com.ioffeivan.feature.auth.domain.model.ResendOtpRequest

fun EmailVerificationRequest.toDto() =
    EmailVerificationRequestDto(
        otp = otp,
    )

fun ResendOtpRequest.toDto() =
    ResendOtpRequestDto(
        email = email,
    )
