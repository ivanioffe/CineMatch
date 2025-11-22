package com.ioffeivan.feature.auth.presentation.sign_up.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpInvalidState
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpLoadingState
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpValidState
import com.ioffeivan.feature.auth.presentation.utils.PasswordState
import com.ioffeivan.feature.auth.presentation.utils.VALID_PASSWORD
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
class SignUpScreenScreenshotTest {
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
                    outputDirectoryPath = "src/test/screenshots/signUp/",
                    outputFileProvider = { description, outputDirectory, fileExtension ->
                        File(
                            outputDirectory,
                            "${description.methodName}.$fileExtension",
                        )
                    },
                ),
        )

    private fun signUpScreen(
        state: SignUpState = SignUpState.initial(),
    ) {
        composeTestRule.setContent {
            PreviewContainer {
                SignUpScreen(
                    state = state,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun signUpScreen_defaultState() {
        signUpScreen()
    }

    @Test
    fun signUpScreen_filledState() {
        signUpScreen(state = signUpValidState)
    }

    // password invisible by default
    @Test
    fun signUpScreen_passwordVisible() {
        val visiblePasswordsState =
            SignUpState.initial().copy(
                password =
                    PasswordState(
                        value = VALID_PASSWORD,
                        visibility = true,
                    ),
                confirmPassword =
                    PasswordState(
                        value = VALID_PASSWORD,
                        visibility = true,
                    ),
            )

        signUpScreen(state = visiblePasswordsState)
    }

    @Test
    fun signUpScreen_loadingState() {
        signUpScreen(state = signUpLoadingState)
    }

    @Test
    fun signUpScreen_invalidState() {
        signUpScreen(state = signUpInvalidState)
    }
}
