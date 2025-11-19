package com.ioffeivan.feature.auth.presentation.sign_up.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpEvent
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpStateFilled
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class SignUpScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var recordEvent: SignUpEvent? = null

    private fun signUpScreen(
        state: SignUpState = SignUpState.initial(),
    ) {
        composeTestRule.setContent {
            PreviewContainer {
                SignUpScreen(
                    state = state,
                    onEvent = { recordEvent = it },
                )
            }
        }
    }

    @Test
    fun emailTextField_whenEmailIsTyped_shouldCallEmailChangeEvent(): Unit =
        with(composeTestRule) {
            val email = "test@example.com"
            val event = SignUpEvent.EmailChange(email)

            signUpScreen()

            onNodeWithTag("emailTextField")
                .performTextInput(email)

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun usernameTextField_whenUsernameIsTyped_shouldCallUsernameChangeEvent(): Unit =
        with(composeTestRule) {
            val username = "username"
            val event = SignUpEvent.UsernameChange(username)

            signUpScreen()

            onNodeWithTag("usernameTextField")
                .performTextInput(username)

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun passwordTextField_whenPasswordIsTyped_shouldCallPasswordChangeEvent(): Unit =
        with(composeTestRule) {
            val password = "password"
            val event = SignUpEvent.PasswordChange(password)

            signUpScreen()

            onNodeWithTag("passwordTextField")
                .performTextInput(password)

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun confirmPasswordTextField_whenConfirmPasswordIsTyped_shouldCallConfirmPasswordChangeEvent(): Unit =
        with(composeTestRule) {
            val confirmPassword = "password"
            val event = SignUpEvent.ConfirmPasswordChange(confirmPassword)

            signUpScreen()

            onNodeWithTag("confirmPasswordTextField")
                .performTextInput(confirmPassword)

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun emailTextField_whenStateIsFilledAndImeActionIsClicked_shouldCallSignUpClickEvent(): Unit =
        with(composeTestRule) {
            val event = SignUpEvent.SignUpClick

            signUpScreen(state = signUpStateFilled)

            val textField = onNodeWithTag("emailTextField")

            textField.performImeAction()
            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun usernameTextField_whenStateIsFilledAndImeActionIsClicked_shouldCallSignUpClickEvent(): Unit =
        with(composeTestRule) {
            val event = SignUpEvent.SignUpClick

            signUpScreen(state = signUpStateFilled)

            val textField = onNodeWithTag("usernameTextField")

            textField.performImeAction()
            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun passwordTextField_whenStateIsFilledAndImeActionIsClicked_shouldCallSignUpClickEvent(): Unit =
        with(composeTestRule) {
            val event = SignUpEvent.SignUpClick

            signUpScreen(state = signUpStateFilled)

            val textField = onNodeWithTag("passwordTextField")

            textField.performImeAction()
            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun confirmPasswordTextField_whenStateIsFilledAndImeActionIsClicked_shouldCallSignUpClickEvent(): Unit =
        with(composeTestRule) {
            val event = SignUpEvent.SignUpClick

            signUpScreen(state = signUpStateFilled)

            val textField = onNodeWithTag("confirmPasswordTextField")

            textField.performImeAction()
            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun signUpButton_whenIsClicked_shouldCallSignUpClickEvent(): Unit =
        with(composeTestRule) {
            val event = SignUpEvent.SignUpClick

            signUpScreen()

            onNodeWithTag("signUpButton")
                .performClick()
                .assertHasClickAction()

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun loginText_whenIsClicked_shouldCallLoginClickEvent(): Unit =
        with(composeTestRule) {
            val event = SignUpEvent.LoginClick

            signUpScreen()

            onNodeWithTag("loginText")
                .performClick()
                .assertHasClickAction()

            assertThat(recordEvent).isEqualTo(event)
        }
}
