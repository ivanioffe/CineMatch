package com.ioffeivan.feature.auth.presentation.login.utils

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.feature.auth.presentation.utils.DataError
import org.junit.jupiter.api.Test

class LoginDataErrorTest {
    @Test
    fun getError_whenKnownMessageIsProvided_shouldReturnSpecificUiText() {
        val expected = ERROR_INVALID_AUTHENTICATION_CREDENTIALS

        val actual = LoginDataError.getError(INVALID_AUTHENTICATION_CREDENTIALS_MESSAGE)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getError_whenUnknownMessageIsProvided_shouldReturnDefaultError() {
        val unknownMessage = "message"
        val expected = DataError.somethingWentWrong

        val actual = LoginDataError.getError(unknownMessage)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getError_whenMessageIsNull_shouldReturnDefaultError() {
        val expected = DataError.somethingWentWrong

        val actual = LoginDataError.getError(null)

        assertThat(actual).isEqualTo(expected)
    }
}
