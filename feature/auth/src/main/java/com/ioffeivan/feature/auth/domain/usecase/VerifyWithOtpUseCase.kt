package com.ioffeivan.feature.auth.domain.usecase

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.model.EmailVerificationRequest
import com.ioffeivan.feature.auth.domain.repository.EmailVerificationRepository
import javax.inject.Inject

class VerifyWithOtpUseCase @Inject constructor(
    private val emailVerificationRepository: EmailVerificationRepository,
) {
    suspend operator fun invoke(emailVerificationRequest: EmailVerificationRequest): Result<Unit> {
        return emailVerificationRepository.verifyWithOtp(emailVerificationRequest)
    }
}
