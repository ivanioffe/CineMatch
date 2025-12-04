package com.ioffeivan.feature.auth.data.repository

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.mapper.toDto
import com.ioffeivan.feature.auth.data.source.local.AuthLocalDataSource
import com.ioffeivan.feature.auth.data.source.remote.data_source.AuthRemoteDataSource
import com.ioffeivan.feature.auth.data.source.remote.model.LoginResponseDto
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.repository.AuthRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AuthRepositoryImplTest {
    private lateinit var remoteDataSource: AuthRemoteDataSource
    private lateinit var localDataSource: AuthLocalDataSource
    private lateinit var authRepository: AuthRepository

    @BeforeEach
    fun setUp() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        authRepository =
            AuthRepositoryImpl(
                authRemoteDataSource = remoteDataSource,
                authLocalDataSource = localDataSource,
            )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun isLoggedIn_whenLocalSourceReturnsFalse_shouldReturnsFalse() =
        runTest {
            val expected = false
            every { localDataSource.isLoggedIn } returns flowOf(expected)

            val actual = localDataSource.isLoggedIn.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { localDataSource.isLoggedIn }
        }

    @Test
    fun isLoggedIn_whenLocalSourceReturnsTrue_shouldReturnsTrue() =
        runTest {
            val expected = true
            every { localDataSource.isLoggedIn } returns flowOf(expected)

            val actual = localDataSource.isLoggedIn.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { localDataSource.isLoggedIn }
        }

    @Nested
    inner class SingUp {
        private val signUpCredentials =
            SignUpCredentials(
                email = "example@example.com",
                username = "username",
                password = "p@SSw0rd",
            )
        private val signUpCredentialsDto = signUpCredentials.toDto()

        @Test
        fun whenRemoteDataSourceReturnsSuccess_shouldReturnsSuccess() =
            runTest {
                val expected = Result.Success(Unit)
                every { remoteDataSource.signUp(signUpCredentialsDto) } returns flowOf(expected)

                val actual = authRepository.signUp(signUpCredentials).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { remoteDataSource.signUp(signUpCredentialsDto) }
            }

        @Test
        fun whenRemoteDataSourceReturnsError_shouldReturnsError() =
            runTest {
                val expected = Result.Error("message")
                every { remoteDataSource.signUp(signUpCredentialsDto) } returns flowOf(expected)

                val actual = authRepository.signUp(signUpCredentials).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { remoteDataSource.signUp(signUpCredentialsDto) }
            }

        @Test
        fun whenRemoteDataSourceReturnsException_shouldReturnsException() =
            runTest {
                val expected = Result.Exception(IOException())
                every { remoteDataSource.signUp(signUpCredentialsDto) } returns flowOf(expected)

                val actual = authRepository.signUp(signUpCredentials).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { remoteDataSource.signUp(signUpCredentialsDto) }
            }
    }

    @Nested
    inner class Login {
        private val loginCredentials =
            LoginCredentials(
                email = "example@example.com",
                password = "p@SSw0rd",
            )
        private val loginCredentialsDto = loginCredentials.toDto()
        private val loginResponseDto =
            LoginResponseDto(
                accessToken = "accessToken",
                refreshToken = "refreshToken",
            )
        private val accessToken = loginResponseDto.accessToken
        private val refreshToken = loginResponseDto.refreshToken

        @Test
        fun whenRemoteDataSourceReturnsSuccess_shouldSaveLoginResponseAndReturnsSuccess() =
            runTest {
                val expected = Result.Success(Unit)
                every {
                    remoteDataSource.login(loginCredentialsDto)
                } returns flowOf(Result.Success(loginResponseDto))
                coEvery { localDataSource.saveAccessToken(accessToken) } just runs
                coEvery { localDataSource.saveRefreshToken(refreshToken) } just runs

                val actual = authRepository.login(loginCredentials).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { remoteDataSource.login(loginCredentialsDto) }
                coVerify(exactly = 1) { localDataSource.saveAccessToken(accessToken) }
                coVerify(exactly = 1) { localDataSource.saveRefreshToken(refreshToken) }
            }

        @Test
        fun whenRemoteDataSourceReturnsError_shouldNotSaveLoginResponseAndReturnsError() =
            runTest {
                val expected = Result.Error("message")
                every { remoteDataSource.login(loginCredentialsDto) } returns flowOf(expected)

                val actual = authRepository.login(loginCredentials).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { remoteDataSource.login(loginCredentialsDto) }
                coVerify(exactly = 0) { localDataSource.saveAccessToken(accessToken) }
                coVerify(exactly = 0) { localDataSource.saveRefreshToken(refreshToken) }
            }

        @Test
        fun whenRemoteDataSourceReturnsException_shouldNotSaveLoginResponseAndReturnsException() =
            runTest {
                val expected = Result.Exception(IOException())
                every { remoteDataSource.login(loginCredentialsDto) } returns flowOf(expected)

                val actual = authRepository.login(loginCredentials).first()

                assertThat(actual).isEqualTo(expected)
                verify(exactly = 1) { remoteDataSource.login(loginCredentialsDto) }
                coVerify(exactly = 0) { localDataSource.saveAccessToken(accessToken) }
                coVerify(exactly = 0) { localDataSource.saveRefreshToken(refreshToken) }
            }
    }
}
