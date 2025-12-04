package com.ioffeivan.feature.auth.data.repository

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.mapper.toDto
import com.ioffeivan.feature.auth.data.source.remote.data_source.EmailVerificationRemoteDataSource
import com.ioffeivan.feature.auth.domain.model.EmailVerificationRequest
import com.ioffeivan.feature.auth.domain.model.ResendOtpRequest
import com.ioffeivan.feature.auth.domain.repository.EmailVerificationRepository
import javax.inject.Inject

class EmailVerificationRepositoryImpl @Inject constructor(
    private val emailVerificationRemoteDataSource: EmailVerificationRemoteDataSource,
) : EmailVerificationRepository {
    override suspend fun verifyWithOtp(emailVerificationRequest: EmailVerificationRequest): Result<Unit> {
        return emailVerificationRemoteDataSource.verifyWithOtp(emailVerificationRequest.toDto())
    }

    override suspend fun resendOtp(resendOtpRequest: ResendOtpRequest): Result<Unit> {
        return emailVerificationRemoteDataSource.resendOtp(resendOtpRequest.toDto())
    }
}
