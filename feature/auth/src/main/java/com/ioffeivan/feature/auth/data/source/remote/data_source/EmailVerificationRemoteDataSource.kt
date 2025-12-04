package com.ioffeivan.feature.auth.data.source.remote.data_source

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.model.EmailVerificationRequestDto
import com.ioffeivan.feature.auth.data.source.remote.model.ResendOtpRequestDto

interface EmailVerificationRemoteDataSource {
    suspend fun verifyWithOtp(
        emailVerificationRequest: EmailVerificationRequestDto,
    ): Result<Unit>

    suspend fun resendOtp(
        resendOtpRequest: ResendOtpRequestDto,
    ): Result<Unit>
}
