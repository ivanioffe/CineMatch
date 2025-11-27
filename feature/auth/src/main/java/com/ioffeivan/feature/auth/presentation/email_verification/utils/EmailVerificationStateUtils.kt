package com.ioffeivan.feature.auth.presentation.email_verification.utils

import com.ioffeivan.feature.auth.presentation.email_verification.EmailVerificationState
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL

internal const val OTP = "12345"

internal val emailVerificationLoadingState =
    EmailVerificationState.initial(VALID_EMAIL).copy(
        isLoading = true,
    )

internal val emailVerificationFilledState =
    EmailVerificationState.initial(VALID_EMAIL).copy(
        otp = OTP,
    )
