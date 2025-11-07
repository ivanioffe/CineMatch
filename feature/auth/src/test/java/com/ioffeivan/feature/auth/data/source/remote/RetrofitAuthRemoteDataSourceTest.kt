package com.ioffeivan.feature.auth.data.source.remote

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.model.LoginCredentialsDto
import com.ioffeivan.feature.auth.data.source.remote.model.LoginResponseDto
import com.ioffeivan.feature.auth.data.source.remote.model.SignUpCredentialsDto
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.IOException

class RetrofitAuthRemoteDataSourceTest {
    private lateinit var authApiService: AuthApiService

    private lateinit var dataSource: AuthRemoteDataSource

    @BeforeEach
    fun setUp() {
        authApiService = mockk()
        dataSource = RetrofitAuthRemoteDataSource(authApiService)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class SignUp {
        private val signUpCredentialsDto =
            SignUpCredentialsDto(
                email = "example@example.com",
                username = "username",
                password = "p@SSw0rd",
            )

        @Test
        fun whenApiServiceReturnsSuccess_shouldReturnsSuccess() =
            runTest {
                val expected = Result.Success(Unit)
                every { authApiService.signUp(signUpCredentialsDto) } returns flowOf(expected)

                val actual = dataSource.signUp(signUpCredentialsDto).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { authApiService.signUp(signUpCredentialsDto) }
            }

        @Test
        fun whenApiServiceReturnsError_shouldReturnsError() =
            runTest {
                val expected = Result.Error("error")
                every { authApiService.signUp(signUpCredentialsDto) } returns flowOf(expected)

                val actual = dataSource.signUp(signUpCredentialsDto).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { authApiService.signUp(signUpCredentialsDto) }
            }

        @Test
        fun whenApiServiceReturnsException_shouldReturnsException() =
            runTest {
                val expected = Result.Exception(IOException())
                every { authApiService.signUp(signUpCredentialsDto) } returns flowOf(expected)

                val actual = dataSource.signUp(signUpCredentialsDto).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { authApiService.signUp(signUpCredentialsDto) }
            }
    }

    @Nested
    inner class Login {
        private val loginCredentialsDto =
            LoginCredentialsDto(
                email = "example@example.com",
                password = "p@SSw0rd",
            )

        @Test
        fun whenApiServiceReturnsSuccess_shouldReturnsSuccess() =
            runTest {
                val loginResponseDto =
                    LoginResponseDto(
                        accessToken = "accessToken",
                        refreshToken = "refreshToken",
                    )
                val expected = Result.Success(loginResponseDto)
                every { authApiService.login(loginCredentialsDto) } returns flowOf(expected)

                val actual = dataSource.login(loginCredentialsDto).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { authApiService.login(loginCredentialsDto) }
            }

        @Test
        fun whenApiServiceReturnsError_shouldReturnsError() =
            runTest {
                val expected = Result.Error("error")
                every { authApiService.login(loginCredentialsDto) } returns flowOf(expected)

                val actual = dataSource.login(loginCredentialsDto).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { authApiService.login(loginCredentialsDto) }
            }

        @Test
        fun whenApiServiceReturnsException_shouldReturnsException() =
            runTest {
                val expected = Result.Exception(IOException())
                every { authApiService.login(loginCredentialsDto) } returns flowOf(expected)

                val actual = dataSource.login(loginCredentialsDto).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { authApiService.login(loginCredentialsDto) }
            }
    }
}
