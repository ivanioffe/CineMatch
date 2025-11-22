package com.ioffeivan.feature.auth.presentation.login

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.presentation.BaseViewModelTest
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.usecase.LoginUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest : BaseViewModelTest() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase

    private val email = "exapmle@example.com"
    private val password = "password"

    private val loginCredentials =
        LoginCredentials(
            email = email,
            password = password,
        )

    @BeforeEach
    override fun setUp() {
        super.setUp()
        loginUseCase = mockk(relaxed = true)
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

    @Test
    fun performLogin_whenLoginUpUseCaseReturnsSuccess_shouldCallOnEventLoginSuccess() =
        runTest {
            every { loginUseCase(loginCredentials) } returns flowOf(Result.Success(Unit))

            fillState()
            loginViewModel.onEvent(LoginEvent.LoginSuccess)

            verify(exactly = 1) { loginViewModel.onEvent(LoginEvent.LoginSuccess) }
        }

    @Test
    fun performLogin_whenLoginUseCaseReturnsError_shouldCallSendEventLoginError() =
        runTest {
            val errorMessage = "message"
            every { loginUseCase(loginCredentials) } returns flowOf(Result.Error(errorMessage))

            fillState()
            loginViewModel.onEvent(LoginEvent.LoginClick)

            verify(exactly = 1) { loginViewModel.onEvent(LoginEvent.LoginError(errorMessage)) }
        }

    @Test
    fun performLogin_whenLoginUseCaseReturnsException_shouldCallSendEventLoginError() =
        runTest {
            val exception = IOException()
            every { loginUseCase(loginCredentials) } returns flowOf(Result.Exception(exception))

            fillState()
            loginViewModel.onEvent(LoginEvent.LoginClick)

            verify(exactly = 1) { loginViewModel.onEvent(LoginEvent.LoginError(exception.message)) }
        }

    private fun fillState() {
        loginViewModel.onEvent(LoginEvent.EmailChange(email))
        loginViewModel.onEvent(LoginEvent.PasswordChange(password))
    }
}
