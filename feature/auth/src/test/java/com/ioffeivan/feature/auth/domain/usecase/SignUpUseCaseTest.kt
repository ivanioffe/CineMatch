package com.ioffeivan.feature.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.repository.AuthRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class SignUpUseCaseTest {
    private lateinit var authRepository: AuthRepository
    private lateinit var signUpUseCase: SignUpUseCase

    private val testCredentials =
        SignUpCredentials(
            email = "email",
            username = "username",
            password = "password",
        )

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        signUpUseCase = SignUpUseCase(authRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun whenRepositoryReturnsSuccess_shouldReturnsSuccess() =
        runTest {
            val expected = Result.Success(Unit)
            every { authRepository.signUp(testCredentials) } returns flowOf(expected)

            val flow = signUpUseCase.invoke(testCredentials)
            val actual = flow.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { authRepository.signUp(testCredentials) }
        }

    @Test
    fun whenRepositoryReturnsError_shouldReturnsError() =
        runTest {
            val expected = Result.Error("error")
            every { authRepository.signUp(testCredentials) } returns flowOf(expected)

            val flow = signUpUseCase.invoke(testCredentials)
            val actual = flow.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { authRepository.signUp(testCredentials) }
        }

    @Test
    fun whenRepositoryReturnsException_shouldReturnsException() =
        runTest {
            val expected = Result.Exception(IOException())
            every { authRepository.signUp(testCredentials) } returns flowOf(expected)

            val flow = signUpUseCase.invoke(testCredentials)
            val actual = flow.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { authRepository.signUp(testCredentials) }
        }
}
