package com.ioffeivan.feature.auth.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class CountdownTimerUseCaseTest {
    private val countdownTimerUseCase = CountdownTimerUseCase()

    private val testSeconds = 3

    @Test
    fun invoke_shouldEmitCorrectSeconds() =
        runTest {
            countdownTimerUseCase(testSeconds).test {
                assertThat(awaitItem()).isEqualTo(testSeconds)

                assertThat(awaitItem()).isEqualTo(testSeconds - 1)

                assertThat(awaitItem()).isEqualTo(testSeconds - 2)

                assertThat(awaitItem()).isEqualTo(testSeconds - 3)

                awaitComplete()
            }
        }
}
