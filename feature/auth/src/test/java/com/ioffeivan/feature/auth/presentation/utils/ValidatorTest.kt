package com.ioffeivan.feature.auth.presentation.utils

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidatorTest {
    @Nested
    inner class EmailValidatorTest {
        @Test
        fun validate_whenValidEmail_shouldReturnNull() {
            val actual = EmailValidator.validate("test@example.com")

            assertThat(actual).isNull()
        }

        @Test
        fun validate_whenBlankEmail_shouldReturnBlankError() {
            val expected = UiText.StringResource(R.string.error_email_blank)

            val actual = EmailValidator.validate("")

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun validate_whenInvalidEmail_shouldReturnInvalidError() {
            val expected = UiText.StringResource(R.string.error_email_invalid)

            val actual = EmailValidator.validate("invalid@")

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Nested
    inner class UsernameValidatorTest {
        @Test
        fun validate_whenValidUsername_shouldReturnNull() {
            val actual = UsernameValidator.validate("User123")

            assertThat(actual).isNull()
        }

        @Test
        fun validate_whenBlankUsername_shouldReturnBlankError() {
            val expected = UiText.StringResource(R.string.error_username_blank)

            val actual = UsernameValidator.validate("")

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun validate_whenUsernameWithInvalidChars_shouldReturnInvalidCharsError() {
            val expected = UiText.StringResource(R.string.error_username_invalid_chars)

            val actual = UsernameValidator.validate("юзерname1234")

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun validate_whenUsernameTooLong_shouldReturnLengthError() {
            val longUsername = "a".repeat(501)
            val expected =
                UiText.StringResource(
                    id = R.string.error_username_invalid_length,
                    args = arrayOf(UsernameValidator.MIN_LENGTH, UsernameValidator.MAX_LENGTH),
                )

            val actual = UsernameValidator.validate(longUsername)

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Nested
    inner class PasswordValidatorTest {
        @Test
        fun validate_whenValidPassword_shouldReturnNull() {
            val actual = PasswordValidator.validate("password123")

            assertThat(actual).isNull()
        }

        @Test
        fun validate_whenBlankPassword_shouldReturnBlankError() {
            val expected = UiText.StringResource(R.string.error_password_blank)

            val actual = PasswordValidator.validate("")

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun validate_whenPasswordWithSpaces_shouldReturnInvalidCharsError() {
            val expected = UiText.StringResource(R.string.error_invalid_characters)

            val actual = PasswordValidator.validate("pass word")

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun validate_whenPasswordTooShort_shouldReturnLengthError() {
            val expected =
                UiText.StringResource(
                    id = R.string.error_password_invalid_length,
                    args = arrayOf(PasswordValidator.MIN_LENGTH, PasswordValidator.MAX_LENGTH),
                )

            val actual = PasswordValidator.validate("short")

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun validate_whenPasswordTooLong_shouldReturnLengthError() {
            val longPassword = "a".repeat(73)
            val expected =
                UiText.StringResource(
                    id = R.string.error_password_invalid_length,
                    args = arrayOf(PasswordValidator.MIN_LENGTH, PasswordValidator.MAX_LENGTH),
                )

            val actual = PasswordValidator.validate(longPassword)

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun validateMismatch_whenMatchingPasswords_shouldReturnNull() {
            val actual = PasswordValidator.validateMismatch("pass123", "pass123")

            assertThat(actual).isNull()
        }

        @Test
        fun validateMismatch_whenNonMatchingPasswords_shouldReturnMismatchError() {
            val expected = UiText.StringResource(R.string.error_password_mismatch)

            val actual = PasswordValidator.validateMismatch("pass123", "different")

            assertThat(actual).isEqualTo(expected)
        }
    }
}
