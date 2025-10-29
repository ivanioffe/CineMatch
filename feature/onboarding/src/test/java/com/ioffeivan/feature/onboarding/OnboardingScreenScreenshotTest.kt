package com.ioffeivan.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziActivity
import com.github.takahirom.roborazzi.captureRoboImage
import com.ioffeivan.core.designsystem.theme.CineMatchTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import com.ioffeivan.feature.onboarding.R as onboardingR

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class OnboardingScreenScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<RoborazziActivity>()

    /*@get:Rule
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
        )*/

    @Test
    fun onboardingScreen() {
        with(composeTestRule) {
            setContent {
                CineMatchTheme {
                    Image(
                        painter = painterResource(onboardingR.drawable.onboarding),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier =
                            Modifier
                                .fillMaxSize(),
                    )
                }
            }

            onRoot().captureRoboImage(
                filePath = "src/test/screenshots/onboardingScreen.png",
            )
        }
    }
}
