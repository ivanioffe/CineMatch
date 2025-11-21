package com.ioffeivan.feature.auth.presentation.sign_up

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.presentation.BaseViewModelTest
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.usecase.SignUpUseCase
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

    private val email = "example@example.com"
    private val username = "username"
    private val password = "password"
    private val signUpCredentials =
        SignUpCredentials(
            email = email,
            username = username,
            password = password,
        )

    @BeforeEach
    override fun setUp() {
        super.setUp()
        signUpUseCase = mockk(relaxed = true)
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

    @Test
    fun performSignUp_whenSignUpUseCaseReturnsSuccess_shouldCallOnEventSignUpSuccess() =
        runTest {
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Success(Unit))

            fillState()
            signUpViewModel.onEvent(SignUpEvent.SignUpClick)

            verify(exactly = 1) { signUpViewModel.onEvent(SignUpEvent.SignUpSuccess) }
        }

    @Test
    fun performSignUp_whenSignUpUseCaseReturnsError_shouldCallSendEventSignUpError() =
        runTest {
            val errorMessage = "message"
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Error(errorMessage))

            fillState()
            signUpViewModel.onEvent(SignUpEvent.SignUpClick)

            verify(exactly = 1) { signUpViewModel.onEvent(SignUpEvent.SignUpError(errorMessage)) }
        }

    @Test
    fun performSignUp_whenSignUpUseCaseReturnsException_shouldCallSendEventSignUpError() =
        runTest {
            val exception = IOException()
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Exception(exception))

            fillState()
            signUpViewModel.onEvent(SignUpEvent.SignUpClick)

            verify(exactly = 1) { signUpViewModel.onEvent(SignUpEvent.SignUpError(exception.message)) }
        }

    private fun fillState() {
        signUpViewModel.onEvent(SignUpEvent.EmailChange(email))
        signUpViewModel.onEvent(SignUpEvent.UsernameChange(username))
        signUpViewModel.onEvent(SignUpEvent.PasswordChange(password))
        signUpViewModel.onEvent(SignUpEvent.ConfirmPasswordChange(password))
    }
}
