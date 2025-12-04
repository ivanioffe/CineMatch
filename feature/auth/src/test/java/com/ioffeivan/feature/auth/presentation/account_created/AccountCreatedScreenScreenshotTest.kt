package com.ioffeivan.feature.auth.presentation.account_created

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.ioffeivan.core.designsystem.preview.PreviewContainer
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
class AccountCreatedScreenScreenshotTest {
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
                    outputDirectoryPath = "src/test/screenshots/account_created/",
                    outputFileProvider = { description, outputDirectory, fileExtension ->
                        File(
                            outputDirectory,
                            "${description.methodName}.$fileExtension",
                        )
                    },
                ),
        )

    @Test
    fun accountCreated_defaultState() {
        composeTestRule.setContent {
            PreviewContainer {
                AccountCreatedScreen(
                    onLoginClick = {},
                )
            }
        }
    }
}
