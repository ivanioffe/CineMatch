package com.ioffeivan.feature.onboarding

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.feature.onboarding.presentation.OnboardingScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class OnboardingScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun whenLoginButtonClick_shouldCallOnLoginCallback(): Unit =
        with(composeTestRule) {
            var isClicked = false

            setContent {
                OnboardingScreen(
                    onLoginButtonClick = { isClicked = true },
                    onSignupButtonClick = {},
                )
            }

            onNodeWithTag("loginButton")
                .performClick()

            assertThat(isClicked).isTrue()
        }

    @Test
    fun whenSignUpButtonClick_shouldCallOnSignUpCallback(): Unit =
        with(composeTestRule) {
            var isClicked = false

            setContent {
                OnboardingScreen(
                    onLoginButtonClick = {},
                    onSignupButtonClick = { isClicked = true },
                )
            }

            onNodeWithTag("signupButton")
                .performClick()

            assertThat(isClicked).isTrue()
        }
}
