package com.ioffeivan.feature.auth.presentation.sign_up

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.turbineScope
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.presentation.BaseViewModelTest
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.usecase.SignUpUseCase
import com.ioffeivan.feature.auth.presentation.utils.DataError
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.VALID_PASSWORD
import com.ioffeivan.feature.auth.presentation.utils.VALID_USERNAME
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors
import io.mockk.clearAllMocks
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

class SignUpViewModelTest : BaseViewModelTest() {
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var signUpUseCase: SignUpUseCase

    private val signUpCredentials =
        SignUpCredentials(
            email = VALID_EMAIL,
            username = VALID_USERNAME,
            password = VALID_PASSWORD,
        )

    @BeforeEach
    override fun setUp() {
        super.setUp()
        signUpUseCase = mockk()
        signUpViewModel =
            spyk(
                SignUpViewModel(signUpUseCase),
            )
    }

    @AfterEach
    override fun tearDown() {
        super.tearDown()
        clearAllMocks()
    }

    private suspend fun ReceiveTurbine<SignUpState>.fillAndPerformSignUp() {
        awaitItem()

        signUpViewModel.onEvent(SignUpEvent.EmailChange(VALID_EMAIL))
        awaitItem()

        signUpViewModel.onEvent(SignUpEvent.UsernameChange(VALID_USERNAME))
        awaitItem()

        signUpViewModel.onEvent(SignUpEvent.PasswordChange(VALID_PASSWORD))
        awaitItem()

        signUpViewModel.onEvent(SignUpEvent.ConfirmPasswordChange(VALID_PASSWORD))
        awaitItem()

        signUpViewModel.onEvent(SignUpEvent.SignUpClick)
    }

    @Test
    fun signUp_whenUseCaseReturnsSuccess_shouldSetLoadingFalseAndNavigateToVerifyEmail() =
        runTest {
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Success(Unit))

            turbineScope {
                val state = signUpViewModel.state.testIn(backgroundScope)
                val effect = signUpViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformSignUp()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val successState = state.awaitItem()
                assertThat(successState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    SignUpEffect.NavigateToVerifyEmail(VALID_EMAIL),
                )

                state.cancel()
                effect.cancel()
            }

            verify(exactly = 1) { signUpUseCase(signUpCredentials) }
        }

    @Test
    fun signUp_whenUseCaseReturnsError_shouldSetLoadingFalseAndShowErrorEffect() =
        runTest {
            val errorMessage = DataError.Message.INVALID_EMAIL
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Error(errorMessage))

            turbineScope {
                val state = signUpViewModel.state.testIn(backgroundScope)
                val effect = signUpViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformSignUp()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val errorState = state.awaitItem()
                assertThat(errorState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    SignUpEffect.ShowError(ValidationErrors.emailInvalid),
                )

                state.cancel()
                effect.cancel()
            }

            verify(exactly = 1) { signUpUseCase(signUpCredentials) }
        }

    @Test
    fun signUp_whenUseCaseReturnsException_shouldSetLoadingFalseAndShowErrorEffect() =
        runTest {
            val exception = IOException()
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Exception(exception))

            turbineScope {
                val state = signUpViewModel.state.testIn(backgroundScope)
                val effect = signUpViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformSignUp()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val exceptionState = state.awaitItem()
                assertThat(exceptionState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    SignUpEffect.ShowError(DataError.somethingWentWrong),
                )

                state.cancel()
                effect.cancel()
            }

            verify(exactly = 1) { signUpUseCase(signUpCredentials) }
        }
}
