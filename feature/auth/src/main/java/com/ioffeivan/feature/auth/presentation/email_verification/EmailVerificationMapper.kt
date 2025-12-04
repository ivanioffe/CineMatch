package com.ioffeivan.feature.auth.presentation.email_verification

import com.ioffeivan.feature.auth.domain.model.EmailVerificationRequest
import com.ioffeivan.feature.auth.domain.model.ResendOtpRequest

internal fun EmailVerificationState.toEmailVerificationRequest(): EmailVerificationRequest {
    return EmailVerificationRequest(
        otp = otp,
    )
}

internal fun EmailVerificationState.toResendOtpRequest(): ResendOtpRequest {
    return ResendOtpRequest(
        email = email,
    )
}
