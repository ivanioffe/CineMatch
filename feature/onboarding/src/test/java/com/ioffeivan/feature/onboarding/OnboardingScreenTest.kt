package com.ioffeivan.feature.onboarding

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(RobolectricTestRunner::class)
class OnboardingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoginButtonClick_callOnLoginButtonClick(): Unit =
        with(composeTestRule) {
            val onLoginButtonClicked = AtomicBoolean(false)

            setContent {
                OnboardingScreen(
                    onLoginButtonClick = { onLoginButtonClicked.set(true) },
                    onSignupButtonClick = {},
                )
            }

            onNodeWithTag("loginButton")
                .performClick()

            assertThat(onLoginButtonClicked.get()).isTrue()
        }

    @Test
    fun whenSignupButtonClick_callOnSignupButtonClick(): Unit =
        with(composeTestRule) {
            val onSignupButtonClicked = AtomicBoolean(false)

            setContent {
                OnboardingScreen(
                    onLoginButtonClick = {},
                    onSignupButtonClick = { onSignupButtonClicked.set(true) },
                )
            }

            onNodeWithTag("signupButton")
                .performClick()

            assertThat(onSignupButtonClicked.get()).isTrue()
        }
}
