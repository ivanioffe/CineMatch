package com.ioffeivan.feature.auth.data.repository

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.data_source.EmailVerificationRemoteDataSource
import com.ioffeivan.feature.auth.domain.repository.EmailVerificationRepository
import com.ioffeivan.feature.auth.utils.ResendOtpResultArgumentsProvider
import com.ioffeivan.feature.auth.utils.VerifyWithOtpResultArgumentsProvider
import com.ioffeivan.feature.auth.utils.testEmailVerificationRequest
import com.ioffeivan.feature.auth.utils.testEmailVerificationRequestDto
import com.ioffeivan.feature.auth.utils.testResendOtpRequest
import com.ioffeivan.feature.auth.utils.testResendOtpRequestDto
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

class EmailVerificationRepositoryImplTest {
    private lateinit var emailVerificationRemoteDataSource: EmailVerificationRemoteDataSource
    private lateinit var emailVerificationRepository: EmailVerificationRepository

    @BeforeEach
    fun setUp() {
        emailVerificationRemoteDataSource = mockk()
        emailVerificationRepository =
            EmailVerificationRepositoryImpl(
                emailVerificationRemoteDataSource = emailVerificationRemoteDataSource,
            )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(VerifyWithOtpResultArgumentsProvider::class)
    fun verifyWithOtp_whenRemoteDataSourceReturnsResult_shouldReturnsSameResult(result: Result<Unit>) =
        runTest {
            coEvery {
                emailVerificationRemoteDataSource.verifyWithOtp(testEmailVerificationRequestDto)
            } returns result

            val actual = emailVerificationRepository.verifyWithOtp(testEmailVerificationRequest)

            assertThat(actual).isEqualTo(result)
            coVerify(exactly = 1) {
                emailVerificationRemoteDataSource.verifyWithOtp(
                    testEmailVerificationRequestDto,
                )
            }
        }

    @ParameterizedTest
    @ArgumentsSource(ResendOtpResultArgumentsProvider::class)
    fun resendOtp_whenRemoteDataSourceReturnsResult_shouldReturnsSameResult(result: Result<Unit>) =
        runTest {
            coEvery {
                emailVerificationRemoteDataSource.resendOtp(
                    testResendOtpRequestDto,
                )
            } returns result

            val actual = emailVerificationRepository.resendOtp(testResendOtpRequest)

            assertThat(actual).isEqualTo(result)
            coVerify(exactly = 1) {
                emailVerificationRemoteDataSource.resendOtp(
                    testResendOtpRequestDto,
                )
            }
        }
}
