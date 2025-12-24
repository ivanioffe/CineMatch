package com.ioffeivan.feature.movie_details.presentation.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.feature.movie_details.movieDetailsStateTest
import com.ioffeivan.feature.movie_details.presentation.MovieDetailsState
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
class MovieDetailsScreenScreenshotTest {
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
                    outputDirectoryPath = "src/test/screenshots/movie_details/",
                    outputFileProvider = { description, outputDirectory, fileExtension ->
                        File(
                            outputDirectory,
                            "${description.methodName}.$fileExtension",
                        )
                    },
                ),
        )

    private fun movieDetailsScreen(
        state: MovieDetailsState = MovieDetailsState.initial(),
    ) {
        composeTestRule.setContent {
            PreviewContainer {
                MovieDetailsScreen(
                    state = state,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun movieDetailsScreen_content() {
        movieDetailsScreen(
            state = movieDetailsStateTest,
        )
    }

    @Test
    fun movieDetailsScreen_loadingState() {
        movieDetailsScreen()
    }

    @Test
    fun movieDetailsScreen_errorState() {
        movieDetailsScreen(
            state =
                MovieDetailsState.initial().copy(
                    isLoading = false,
                    isError = true,
                ),
        )
    }
}
