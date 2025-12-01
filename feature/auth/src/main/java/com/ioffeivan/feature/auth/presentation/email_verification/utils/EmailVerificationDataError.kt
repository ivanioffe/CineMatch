package com.ioffeivan.feature.auth.presentation.email_verification.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.presentation.utils.DataError

internal const val INVALID_OTP_MESSAGE = "invalid or expired activation token"

internal val ERROR_INVALID_OTP = UiText.StringResource(R.string.invalid_otp)

internal object EmailVerificationDataError {
    private val defaultError = DataError.somethingWentWrong

    private val errors: Map<String, UiText> =
        mapOf(
            INVALID_OTP_MESSAGE to ERROR_INVALID_OTP,
        )

    fun getError(message: String?): UiText {
        return errors[message] ?: defaultError
    }
}
