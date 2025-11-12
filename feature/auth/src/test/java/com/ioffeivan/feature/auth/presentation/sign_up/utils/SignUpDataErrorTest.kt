package com.ioffeivan.feature.auth.presentation.sign_up.utils

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import org.junit.jupiter.api.Test

class SignUpDataErrorTest {
    companion object {
        private const val DUPLICATE_EMAIL_MESSAGE = "duplicate email"
    }

    @Test
    fun getError_whenKnownMessageIsProvided_shouldReturnSpecificUiText() {
        val expected = UiText.StringResource(R.string.error_email_duplicate)

        val actual = SignUpDataError.getError(DUPLICATE_EMAIL_MESSAGE)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getError_whenUnknownMessageIsProvided_shouldReturnDefaultError() {
        val unknownMessage = "message"
        val expected = UiText.StringResource(R.string.error_something_went_wrong)

        val actual = SignUpDataError.getError(unknownMessage)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getError_whenMessageIsNull_shouldReturnDefaultError() {
        val expected = UiText.StringResource(R.string.error_something_went_wrong)

        val actual = SignUpDataError.getError(null)

        assertThat(actual).isEqualTo(expected)
    }
}
