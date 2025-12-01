package com.ioffeivan.feature.auth.presentation.email_verification

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.turbineScope
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.presentation.BaseViewModelTest
import com.ioffeivan.feature.auth.domain.model.EmailVerificationRequest
import com.ioffeivan.feature.auth.domain.model.ResendOtpRequest
import com.ioffeivan.feature.auth.domain.usecase.CountdownTimerUseCase
import com.ioffeivan.feature.auth.domain.usecase.ResendOtpUseCase
import com.ioffeivan.feature.auth.domain.usecase.VerifyWithOtpUseCase
import com.ioffeivan.feature.auth.presentation.email_verification.utils.ERROR_INVALID_OTP
import com.ioffeivan.feature.auth.presentation.email_verification.utils.INVALID_OTP_MESSAGE
import com.ioffeivan.feature.auth.presentation.email_verification.utils.OTP
import com.ioffeivan.feature.auth.presentation.utils.DataError
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import dagger.Lazy
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class EmailVerificationViewModelTest : BaseViewModelTest() {
    private lateinit var emailVerificationViewModel: EmailVerificationViewModel
    private lateinit var verifyWithOtpUseCase: VerifyWithOtpUseCase
    private lateinit var resendOtpUseCase: Lazy<ResendOtpUseCase>
    private lateinit var countdownTimerUseCase: CountdownTimerUseCase

    private val emailVerificationRequest =
        EmailVerificationRequest(
            otp = OTP,
        )

    private val resendOtpRequest =
        ResendOtpRequest(
            email = VALID_EMAIL,
        )

    @BeforeEach
    override fun setUp() {
        super.setUp()
        verifyWithOtpUseCase = mockk()
        resendOtpUseCase = mockk()
        countdownTimerUseCase = mockk(relaxed = true)
        emailVerificationViewModel =
            spyk(
                EmailVerificationViewModel(
                    verifyWithOtpUseCase = verifyWithOtpUseCase,
                    resendOtpUseCase = resendOtpUseCase,
                    countdownTimerUseCase = countdownTimerUseCase,
                    email = VALID_EMAIL,
                ),
            )
    }

    @AfterEach
    override fun tearDown() {
        super.tearDown()
    }

    private suspend fun ReceiveTurbine<EmailVerificationState>.fillAndPerformEmailVerification() {
        awaitItem()

        emailVerificationViewModel.onEvent(EmailVerificationEvent.OtpChange(OTP))
        awaitItem()

        emailVerificationViewModel.onEvent(EmailVerificationEvent.OtpComplete)
    }

    @Test
    fun verifyEmail_whenUseCaseReturnsSuccess_shouldSetLoadingFalseAndAndEmitNavigateToAccountCreated() =
        runTest {
            coEvery { verifyWithOtpUseCase(emailVerificationRequest) } returns Result.Success(Unit)

            turbineScope {
                val state = emailVerificationViewModel.state.testIn(backgroundScope)
                val effect = emailVerificationViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformEmailVerification()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val successState = state.awaitItem()
                assertThat(successState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    EmailVerificationEffect.NavigateToAccountCreated,
                )

                state.cancel()
                effect.cancel()
            }

            coVerify(exactly = 1) { verifyWithOtpUseCase(emailVerificationRequest) }
        }

    @Test
    fun verifyEmail_whenUseCaseReturnsError_shouldSetLoadingFalseAndAndEmitShowErrorEffect() =
        runTest {
            val errorMessage = INVALID_OTP_MESSAGE
            coEvery {
                verifyWithOtpUseCase(emailVerificationRequest)
            } returns Result.Error(errorMessage)

            turbineScope {
                val state = emailVerificationViewModel.state.testIn(backgroundScope)
                val effect = emailVerificationViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformEmailVerification()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val errorState = state.awaitItem()
                assertThat(errorState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    EmailVerificationEffect.ShowError(ERROR_INVALID_OTP),
                )

                state.cancel()
                effect.cancel()
            }

            coVerify(exactly = 1) { verifyWithOtpUseCase(emailVerificationRequest) }
        }

    @Test
    fun verifyEmail_whenUseCaseReturnsException_shouldSetLoadingFalseAndAndEmitShowErrorEffect() =
        runTest {
            val exception = IOException()
            coEvery {
                verifyWithOtpUseCase(emailVerificationRequest)
            } returns Result.Exception(exception)

            turbineScope {
                val state = emailVerificationViewModel.state.testIn(backgroundScope)
                val effect = emailVerificationViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformEmailVerification()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val exceptionState = state.awaitItem()
                assertThat(exceptionState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    EmailVerificationEffect.ShowError(DataError.somethingWentWrong),
                )

                state.cancel()
                effect.cancel()
            }

            coVerify(exactly = 1) { verifyWithOtpUseCase(emailVerificationRequest) }
        }

    @Test
    fun resendOtp_whenUseCaseReturnsException_shouldUpdateTimerStateAndEmitShowErrorEffect() =
        runTest {
            val exception = IOException()
            val seconds = 30
            every {
                countdownTimerUseCase(any())
            } returns flowOf(seconds)
            coEvery {
                resendOtpUseCase.get().invoke(resendOtpRequest)
            } returns Result.Exception(exception)

            turbineScope {
                val state = emailVerificationViewModel.state.testIn(backgroundScope)
                val effect = emailVerificationViewModel.effect.testIn(backgroundScope)

                state.awaitItem()

                emailVerificationViewModel.onEvent(EmailVerificationEvent.ResendOtpClick)

                assertThat(state.awaitItem().timerState.displayTime).isEqualTo("00:$seconds")
                assertThat(effect.awaitItem()).isEqualTo(
                    EmailVerificationEffect.ShowError(DataError.somethingWentWrong),
                )

                state.cancel()
                effect.cancel()
            }

            coVerify(exactly = 1) { resendOtpUseCase.get().invoke(resendOtpRequest) }
            verify(atLeast = 1) { countdownTimerUseCase(any()) }
        }
}
