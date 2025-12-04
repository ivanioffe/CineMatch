package com.ioffeivan.feature.auth.presentation.login

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.turbineScope
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.presentation.BaseViewModelTest
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.usecase.LoginUseCase
import com.ioffeivan.feature.auth.presentation.login.utils.ERROR_INVALID_AUTHENTICATION_CREDENTIALS
import com.ioffeivan.feature.auth.presentation.login.utils.INVALID_AUTHENTICATION_CREDENTIALS_MESSAGE
import com.ioffeivan.feature.auth.presentation.utils.DataError
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.VALID_PASSWORD
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

class LoginViewModelTest : BaseViewModelTest() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase

    private val loginCredentials =
        LoginCredentials(
            email = VALID_EMAIL,
            password = VALID_PASSWORD,
        )

    @BeforeEach
    override fun setUp() {
        super.setUp()
        loginUseCase = mockk()
        loginViewModel =
            spyk(
                LoginViewModel(loginUseCase),
            )
    }

    @AfterEach
    override fun tearDown() {
        super.tearDown()
        clearAllMocks()
    }

    private suspend fun ReceiveTurbine<LoginState>.fillAndPerformLogin() {
        awaitItem()

        loginViewModel.onEvent(LoginEvent.EmailChange(VALID_EMAIL))
        awaitItem()

        loginViewModel.onEvent(LoginEvent.PasswordChange(VALID_PASSWORD))
        awaitItem()

        loginViewModel.onEvent(LoginEvent.LoginClick)
    }

    @Test
    fun login_whenUseCaseReturnsSuccess_shouldSetLoadingFalseAndNavigateToVerifyEmail() =
        runTest {
            every { loginUseCase(loginCredentials) } returns flowOf(Result.Success(Unit))

            turbineScope {
                val state = loginViewModel.state.testIn(backgroundScope)
                val effect = loginViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformLogin()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val successState = state.awaitItem()
                assertThat(successState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    LoginEffect.NavigateToMain,
                )

                state.cancel()
                effect.cancel()
            }

            verify(exactly = 1) { loginUseCase(loginCredentials) }
        }

    @Test
    fun login_whenUseCaseReturnsError_shouldSetLoadingFalseAndShowErrorEffect() =
        runTest {
            val errorMessage = INVALID_AUTHENTICATION_CREDENTIALS_MESSAGE
            every { loginUseCase(loginCredentials) } returns flowOf(Result.Error(errorMessage))

            turbineScope {
                val state = loginViewModel.state.testIn(backgroundScope)
                val effect = loginViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformLogin()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val errorState = state.awaitItem()
                assertThat(errorState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    LoginEffect.ShowError(ERROR_INVALID_AUTHENTICATION_CREDENTIALS),
                )

                state.cancel()
                effect.cancel()
            }

            verify(exactly = 1) { loginUseCase(loginCredentials) }
        }

    @Test
    fun login_whenUseCaseReturnsException_shouldSetLoadingFalseAndShowErrorEffect() =
        runTest {
            val exception = IOException()
            every { loginUseCase(loginCredentials) } returns flowOf(Result.Exception(exception))

            turbineScope {
                val state = loginViewModel.state.testIn(backgroundScope)
                val effect = loginViewModel.effect.testIn(backgroundScope)

                state.fillAndPerformLogin()

                val loadingState = state.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val exceptionState = state.awaitItem()
                assertThat(exceptionState.isLoading).isFalse()
                assertThat(effect.awaitItem()).isEqualTo(
                    LoginEffect.ShowError(DataError.somethingWentWrong),
                )

                state.cancel()
                effect.cancel()
            }

            verify(exactly = 1) { loginUseCase(loginCredentials) }
        }
}
