package com.ioffeivan.feature.auth.presentation.email_verification.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.presentation.email_verification.EmailVerificationEvent
import com.ioffeivan.feature.auth.presentation.email_verification.EmailVerificationState
import com.ioffeivan.feature.auth.presentation.email_verification.utils.emailVerificationFilledState
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class EmailVerificationScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var recordEvent: EmailVerificationEvent? = null

    private val initialState = EmailVerificationState.initial(VALID_EMAIL)

    private fun emailVerificationScreen(
        state: EmailVerificationState = initialState,
    ) {
        composeTestRule.setContent {
            PreviewContainer {
                EmailVerificationScreen(
                    state = state,
                    onEvent = { recordEvent = it },
                )
            }
        }
    }

    @Test
    fun otpField_whenOtpIsTyped_shouldCallOtpChangeEvent(): Unit =
        with(composeTestRule) {
            val inputOtp = "1"
            val event = EmailVerificationEvent.OtpChange(inputOtp)

            emailVerificationScreen()

            onNodeWithTag("OtpField")
                .performTextInput(inputOtp)

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun otpField_whenOtpIsFilled_shouldCallOtpCompleteEvent(): Unit =
        with(composeTestRule) {
            val filledState = emailVerificationFilledState
            val event = EmailVerificationEvent.OtpComplete

            emailVerificationScreen(state = filledState)

            assertThat(recordEvent).isEqualTo(event)
        }

    @Test
    fun resendButton_whenTimerIsActive_shouldBeDisabled(): Unit =
        with(composeTestRule) {
            val seconds = 30
            val runningState =
                initialState.copy(
                    timerState = EmailVerificationState.TimerState.Running(seconds),
                )

            emailVerificationScreen(state = runningState)

            val buttonText = activity.getString(R.string.resend_code_with_timer, "00:$seconds")

            onNodeWithText(buttonText)
                .assertIsNotEnabled()
        }

    @Test
    fun resendButton_whenTimerIsFinished_shouldBeEnabledAndCallResendOtpClickEvent(): Unit =
        with(composeTestRule) {
            val finishedState =
                initialState.copy(
                    timerState = EmailVerificationState.TimerState.Finished,
                )
            val event = EmailVerificationEvent.ResendOtpClick

            emailVerificationScreen(state = finishedState)

            val buttonText = activity.getString(R.string.resend_code_with_timer, "")

            onNodeWithText(buttonText)
                .assertIsEnabled()
                .performClick()

            assertThat(recordEvent).isEqualTo(event)
        }
}
