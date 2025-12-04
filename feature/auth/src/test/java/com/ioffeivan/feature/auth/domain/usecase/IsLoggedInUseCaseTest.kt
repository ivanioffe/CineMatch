package com.ioffeivan.feature.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
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

class IsLoggedInUseCaseTest {
    private lateinit var authRepository: AuthRepository
    private lateinit var isLoggedInUseCase: IsLoggedInUseCase

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        isLoggedInUseCase = IsLoggedInUseCase(authRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun whenRepositoryReturnsTrue_shouldReturnsTrue() =
        runTest {
            val expected = true
            every { authRepository.isLoggedIn } returns flowOf(expected)

            val flow = isLoggedInUseCase.invoke()
            val actual = flow.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { authRepository.isLoggedIn }
        }

    @Test
    fun whenRepositoryReturnsFalse_shouldReturnsFalse() =
        runTest {
            val expected = false
            every { authRepository.isLoggedIn } returns flowOf(expected)

            val flow = isLoggedInUseCase.invoke()
            val actual = flow.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { authRepository.isLoggedIn }
        }
}
