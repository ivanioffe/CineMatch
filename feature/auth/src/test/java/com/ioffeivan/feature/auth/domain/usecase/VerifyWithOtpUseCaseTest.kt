package com.ioffeivan.feature.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.repository.EmailVerificationRepository
import com.ioffeivan.feature.auth.utils.VerifyWithOtpResultArgumentsProvider
import com.ioffeivan.feature.auth.utils.testEmailVerificationRequest
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

class VerifyWithOtpUseCaseTest {
    private lateinit var emailVerificationRepository: EmailVerificationRepository
    private lateinit var verifyWithOtpUseCase: VerifyWithOtpUseCase

    @BeforeEach
    fun setUp() {
        emailVerificationRepository = mockk()
        verifyWithOtpUseCase = VerifyWithOtpUseCase(emailVerificationRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(VerifyWithOtpResultArgumentsProvider::class)
    fun whenRepositoryReturnsResult_shouldReturnsSameResult(result: Result<Unit>) =
        runTest {
            coEvery { emailVerificationRepository.verifyWithOtp(testEmailVerificationRequest) } returns result

            val actual = verifyWithOtpUseCase(testEmailVerificationRequest)

            assertThat(actual).isEqualTo(result)
            coVerify(exactly = 1) {
                emailVerificationRepository.verifyWithOtp(
                    testEmailVerificationRequest,
                )
            }
        }
}
