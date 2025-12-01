package com.ioffeivan.feature.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.repository.EmailVerificationRepository
import com.ioffeivan.feature.auth.utils.ResendOtpResultArgumentsProvider
import com.ioffeivan.feature.auth.utils.testResendOtpRequest
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResendOtpUseCaseTest {
    private lateinit var emailVerificationRepository: EmailVerificationRepository
    private lateinit var resendOtpUseCase: ResendOtpUseCase

    @BeforeEach
    fun setUp() {
        emailVerificationRepository = mockk()
        resendOtpUseCase = ResendOtpUseCase(emailVerificationRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(ResendOtpResultArgumentsProvider::class)
    fun whenRepositoryReturnsResult_shouldReturnsSameResult(result: Result<Unit>) {
        runTest {
            coEvery { emailVerificationRepository.resendOtp(testResendOtpRequest) } returns result

            val actual = resendOtpUseCase(testResendOtpRequest)

            assertThat(actual).isEqualTo(result)
            coVerify(exactly = 1) { emailVerificationRepository.resendOtp(testResendOtpRequest) }
        }
    }
}
