package com.ioffeivan.feature.auth.data.source.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.api.EmailVerificationApiService
import com.ioffeivan.feature.auth.utils.ResendOtpResultArgumentsProvider
import com.ioffeivan.feature.auth.utils.VerifyWithOtpResultArgumentsProvider
import com.ioffeivan.feature.auth.utils.testEmailVerificationRequestDto
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

class RetrofitEmailVerificationRemoteDataSourceTest {
    private lateinit var emailVerificationApiService: EmailVerificationApiService
    private lateinit var emailVerificationRemoteDataSource: EmailVerificationRemoteDataSource

    @BeforeEach
    fun setUp() {
        emailVerificationApiService = mockk()
        emailVerificationRemoteDataSource =
            RetrofitEmailVerificationRemoteDataSource(emailVerificationApiService)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(VerifyWithOtpResultArgumentsProvider::class)
    fun verifyWithOtp_whenApiReturnsResult_shouldReturnsSameResult(result: Result<Unit>) =
        runTest {
            coEvery {
                emailVerificationApiService.verifyWithOtp(testEmailVerificationRequestDto)
            } returns result

            val actual =
                emailVerificationRemoteDataSource.verifyWithOtp(testEmailVerificationRequestDto)

            assertThat(actual).isEqualTo(result)
            coVerify(exactly = 1) {
                emailVerificationApiService.verifyWithOtp(
                    testEmailVerificationRequestDto,
                )
            }
        }

    @ParameterizedTest
    @ArgumentsSource(ResendOtpResultArgumentsProvider::class)
    fun resendOtp_whenApiReturnsResult_shouldReturnsSameResult(result: Result<Unit>) =
        runTest {
            coEvery {
                emailVerificationApiService.resendOtp(testResendOtpRequestDto)
            } returns result

            val actual =
                emailVerificationRemoteDataSource.resendOtp(testResendOtpRequestDto)

            assertThat(actual).isEqualTo(result)
            coVerify(exactly = 1) {
                emailVerificationApiService.resendOtp(
                    testResendOtpRequestDto,
                )
            }
        }
}
