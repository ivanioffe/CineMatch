package com.ioffeivan.feature.auth.presentation.sign_up

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.presentation.BaseViewModelTest
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.usecase.SignUpUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest : BaseViewModelTest() {
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var signUpUseCase: SignUpUseCase
    val signUpCredentials =
        SignUpCredentials(
            email = "email@gmail.com",
            username = "username",
            password = "password",
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
    fun onEvent_shouldCallSendEvent() {
        val event = SignUpEvent.SignUpClick
        signUpViewModel.onEvent(event)

        verify(exactly = 1) { signUpViewModel.sendEvent(event) }
    }

    @Test
    fun handleEffect_whenEffectIsUi_shouldUpdateEffect() =
        runTest {
            val expected = SignUpEffect.Ui.NavigateToLogin
            signUpViewModel.handleEffect(expected)

            val actual = signUpViewModel.effect.first()

            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun handleEffect_whenEffectIsPerformSignUp_shouldCallSignUpUseCase() =
        runTest {
            val effect = SignUpEffect.Internal.PerformSignUp(signUpCredentials)

            signUpViewModel.handleEffect(effect)

            verify(exactly = 1) { signUpUseCase(signUpCredentials) }
        }

    @Test
    fun performSignUp_whenSignUpUseCaseReturnsSuccess_shouldCallSendEventSignUpSuccess() =
        runTest {
            val effect = SignUpEffect.Internal.PerformSignUp(signUpCredentials)
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Success(Unit))

            signUpViewModel.handleEffect(effect)

            verify(exactly = 1) { signUpViewModel.sendEvent(SignUpEvent.SignUpSuccess) }
        }

    @Test
    fun performSignUp_whenSignUpUseCaseReturnsError_shouldCallSendEventSignUpError() =
        runTest {
            val errorMessage = "message"
            val effect = SignUpEffect.Internal.PerformSignUp(signUpCredentials)
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Error(errorMessage))

            signUpViewModel.handleEffect(effect)

            verify(exactly = 1) { signUpViewModel.sendEvent(SignUpEvent.SignUpError(errorMessage)) }
        }

    @Test
    fun performSignUp_whenSignUpUseCaseReturnsException_shouldCallSendEventSignUpError() =
        runTest {
            val exception = IOException()
            val effect = SignUpEffect.Internal.PerformSignUp(signUpCredentials)
            every { signUpUseCase(signUpCredentials) } returns flowOf(Result.Exception(exception))

            signUpViewModel.handleEffect(effect)

            verify(exactly = 1) {
                signUpViewModel.sendEvent(SignUpEvent.SignUpError(exception.message))
            }
        }
}
