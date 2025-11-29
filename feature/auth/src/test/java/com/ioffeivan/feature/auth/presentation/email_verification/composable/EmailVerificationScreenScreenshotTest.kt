package com.ioffeivan.feature.auth.presentation.email_verification.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.feature.auth.presentation.email_verification.EmailVerificationState
import com.ioffeivan.feature.auth.presentation.email_verification.utils.emailVerificationFilledState
import com.ioffeivan.feature.auth.presentation.email_verification.utils.emailVerificationLoadingState
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class EmailVerificationScreenScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val roborazziRule =
        RoborazziRule(
            composeRule = composeTestRule,
            captureRoot = composeTestRule.onRoot(),
            options =
                RoborazziRule.Options(
                    captureType = RoborazziRule.CaptureType.LastImage(),
                    outputDirectoryPath = "src/test/screenshots/email_verification/",
                    outputFileProvider = { description, outputDirectory, fileExtension ->
                        File(
                            outputDirectory,
                            "${description.methodName}.$fileExtension",
                        )
                    },
                ),
        )

    private val initialState = EmailVerificationState.initial(VALID_EMAIL)

    private val testTimerState = EmailVerificationState.TimerState.Running(30)

    private fun emailVerificationScreen(
        state: EmailVerificationState = initialState,
    ) {
        composeTestRule.setContent {
            PreviewContainer {
                EmailVerificationScreen(
                    state = state,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun emailVerification_defaultState() {
        emailVerificationScreen(initialState.copy(timerState = testTimerState))
    }

    @Test
    fun emailVerification_filledState() {
        emailVerificationScreen(
            state = emailVerificationFilledState.copy(timerState = testTimerState),
        )
    }

    @Test
    fun emailVerification_loadingState() {
        emailVerificationScreen(
            state = emailVerificationLoadingState.copy(timerState = testTimerState),
        )
    }

    // default by running
    @Test
    fun emailVerification_finishedTimer() {
        emailVerificationScreen(
            state =
                initialState.copy(
                    timerState = EmailVerificationState.TimerState.Finished,
                ),
        )
    }
}
