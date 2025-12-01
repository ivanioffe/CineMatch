package com.ioffeivan.feature.auth.domain.usecase

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.model.ResendOtpRequest
import com.ioffeivan.feature.auth.domain.repository.EmailVerificationRepository
import javax.inject.Inject

class ResendOtpUseCase @Inject constructor(
    private val emailVerificationRepository: EmailVerificationRepository,
) {
    suspend operator fun invoke(resendOtpRequest: ResendOtpRequest): Result<Unit> {
        return emailVerificationRepository.resendOtp(resendOtpRequest)
    }
}
