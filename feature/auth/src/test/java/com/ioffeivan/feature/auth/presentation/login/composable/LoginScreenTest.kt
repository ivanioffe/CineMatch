package com.ioffeivan.feature.auth.presentation.login.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.feature.auth.presentation.login.LoginEvent
import com.ioffeivan.feature.auth.presentation.login.LoginState
import com.ioffeivan.feature.auth.presentation.login.utils.loginValidState
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import com.ioffeivan.feature.auth.presentation.utils.VALID_PASSWORD
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var recordEvent: LoginEvent? = null

    private fun loginScreen(
        state: LoginState = LoginState.initial(),
    ) {
        composeTestRule.setContent {
            PreviewContainer {
                LoginScreen(
                    state = state,
                    onEvent = { recordEvent = it },
                )
            }
        }
    }

    @Test
    fun emailTextField_whenEmailIsTyped_shouldCallEmailChangeEvent(): Unit =
        with(composeTestRule) {
            val email = VALID_EMAIL
            val event = LoginEvent.EmailChange(email)

            loginScreen()

            onNodeWithTag("emailTextField")
                .performTextInput(email)

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun passwordTextField_whenPasswordIsTyped_shouldCallPasswordChangeEvent(): Unit =
        with(composeTestRule) {
            val password = VALID_PASSWORD
            val event = LoginEvent.PasswordChange(password)

            loginScreen()

            onNodeWithTag("passwordTextField")
                .performTextInput(password)

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun emailTextField_whenStateIsFilledAndImeActionIsClicked_shouldCallLoginClickEvent(): Unit =
        with(composeTestRule) {
            val event = LoginEvent.LoginClick

            loginScreen(state = loginValidState)

            onNodeWithTag("emailTextField")
                .performImeAction()

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun passwordTextField_whenStateIsFilledAndImeActionIsClicked_shouldCallLoginClickEvent(): Unit =
        with(composeTestRule) {
            val event = LoginEvent.LoginClick

            loginScreen(state = loginValidState)

            onNodeWithTag("passwordTextField")
                .performImeAction()

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun loginButton_whenIsClicked_shouldCallLoginClickEvent(): Unit =
        with(composeTestRule) {
            val event = LoginEvent.LoginClick

            loginScreen()

            onNodeWithTag("loginButton")
                .performClick()

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun signUpText_whenIsClicked_shouldCallSignUpClickEvent(): Unit =
        with(composeTestRule) {
            val event = LoginEvent.SignUpClick

            loginScreen()

            onNodeWithTag("signUpText")
                .performClick()

            assertThat(recordEvent).isEqualTo(event)
        }
}
