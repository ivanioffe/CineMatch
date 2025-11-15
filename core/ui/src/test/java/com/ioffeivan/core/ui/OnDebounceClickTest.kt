package com.ioffeivan.core.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
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
    val composeTestRule = createComposeRule()

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

            repeat(5) {
                onNodeWithText("Click").performClick()
            }

            assertThat(clickCount).isEqualTo(1)

            Thread.sleep(DEBOUNCE_TIME_MILLIS_DEFAULT)

            onNodeWithText("Click").performClick()

            assertThat(clickCount).isEqualTo(2)
        }
}
