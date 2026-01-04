package com.ioffeivan.core.ui

import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnDebounceClickTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun onDebounceClick_shouldInvokeOnClickOnlyOnceWithinDebouncePeriod(): Unit =
        with(composeTestRule) {
            var clickCount = 0

            setContent {
                Button(
                    onClick =
                        onDebounceClick {
                            clickCount++
                        },
                ) {
                    Text("Click")
                }
            }

            val button = onNodeWithText("Click")

            repeat(5) {
                button.performClick()
            }

            assertThat(clickCount).isEqualTo(1)

            // Wait until the debounce period expires, adding a small buffer to ensure test stability
            SystemClock.sleep(DEBOUNCE_TIME_MILLIS_DEFAULT + 50)

            repeat(5) {
                button.performClick()
            }

            assertThat(clickCount).isEqualTo(2)
        }
}
