package com.ioffeivan.feature.auth.presentation.login.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.feature.auth.presentation.login.LoginState
import com.ioffeivan.feature.auth.presentation.login.utils.loginInvalidState
import com.ioffeivan.feature.auth.presentation.login.utils.loginLoadingState
import com.ioffeivan.feature.auth.presentation.login.utils.loginValidState
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
class LoginScreenScreenshotTest {
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
                    outputDirectoryPath = "src/test/screenshots/login/",
                    outputFileProvider = { description, outputDirectory, fileExtension ->
                        File(
                            outputDirectory,
                            "${description.methodName}.$fileExtension",
                        )
                    },
                ),
        )

    private fun loginScreen(
        state: LoginState = LoginState.initial(),
    ) {
        composeTestRule.setContent {
            PreviewContainer {
                LoginScreen(
                    state = state,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun loginScreen_defaultState() {
        loginScreen()
    }

    @Test
    fun loginScreen_filledState() {
        loginScreen(state = loginValidState)
    }

    // password invisible by default
    @Test
    fun loginScreen_passwordVisible() {
        val visiblePasswordState =
            LoginState.initial().copy(
                password =
                    PasswordState(
                        value = VALID_PASSWORD,
                        visibility = true,
                    ),
            )

        loginScreen(state = visiblePasswordState)
    }

    @Test
    fun loginScreen_loadingState() {
        loginScreen(state = loginLoadingState)
    }

    @Test
    fun loginScreen_invalidState() {
        loginScreen(state = loginInvalidState)
    }
}
