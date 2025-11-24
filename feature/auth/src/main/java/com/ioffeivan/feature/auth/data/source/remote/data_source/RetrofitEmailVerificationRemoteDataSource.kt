package com.ioffeivan.feature.auth.data.source.remote.data_source

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.api.EmailVerificationApiService
import com.ioffeivan.feature.auth.data.source.remote.model.EmailVerificationRequestDto
import com.ioffeivan.feature.auth.data.source.remote.model.ResendOtpRequestDto
import javax.inject.Inject

internal class RetrofitEmailVerificationRemoteDataSource @Inject constructor(
    private val emailVerificationApiService: EmailVerificationApiService,
) : EmailVerificationRemoteDataSource {
    override suspend fun verifyWithOtp(emailVerificationRequest: EmailVerificationRequestDto): Result<Unit> {
        return emailVerificationApiService.verifyWithOtp(emailVerificationRequest)
    }

    override suspend fun resendOtp(resendOtpRequest: ResendOtpRequestDto): Result<Unit> {
        return emailVerificationApiService.resendOtp(resendOtpRequest)
    }
}
