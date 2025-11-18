package com.ioffeivan.feature.auth.presentation.sign_up.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpStateFilled
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpStateLoading
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpStateValidationError
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
                    outputDirectoryPath = "src/test/screenshots/",
                    outputFileProvider = { description, outputDirectory, fileExtension ->
                        File(
                            outputDirectory,
                            "${description.methodName}.$fileExtension",
                        )
                    },
                ),
        )

    @Test
    fun defaultState() {
        composeTestRule.setContent {
            PreviewContainer {
                SignUpScreen(
                    state = SignUpState.initial(),
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun filledState() {
        composeTestRule.setContent {
            PreviewContainer {
                SignUpScreen(
                    state = signUpStateFilled,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun passwordsVisible() {
        val visiblePasswordsState = createPasswordsState(passwordVisibility = false)

        composeTestRule.setContent {
            PreviewContainer {
                SignUpScreen(
                    state = visiblePasswordsState,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun passwordsInvisible() {
        val invisiblePasswordsState = createPasswordsState(passwordVisibility = true)

        composeTestRule.setContent {
            PreviewContainer {
                SignUpScreen(
                    state = invisiblePasswordsState,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun validationErrorState() {
        composeTestRule.setContent {
            PreviewContainer {
                SignUpScreen(
                    state = signUpStateValidationError,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun loadingState() {
        composeTestRule.setContent {
            PreviewContainer {
                SignUpScreen(
                    state = signUpStateLoading,
                    onEvent = {},
                )
            }
        }
    }

    private fun createPasswordsState(passwordVisibility: Boolean): SignUpState {
        return SignUpState.initial().copy(
            password =
                SignUpState.PasswordState(
                    value = "password",
                    visibility = passwordVisibility,
                ),
            confirmPassword =
                SignUpState.PasswordState(
                    value = "password",
                    visibility = passwordVisibility,
                ),
        )
    }
}
