package com.ioffeivan.feature.auth.domain.repository

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.model.EmailVerificationRequest
import com.ioffeivan.feature.auth.domain.model.ResendOtpRequest

interface EmailVerificationRepository {
    suspend fun verifyWithOtp(emailVerificationRequest: EmailVerificationRequest): Result<Unit>

    suspend fun resendOtp(resendOtpRequest: ResendOtpRequest): Result<Unit>
}
