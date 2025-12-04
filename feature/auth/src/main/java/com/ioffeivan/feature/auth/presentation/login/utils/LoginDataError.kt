package com.ioffeivan.feature.auth.presentation.login.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.presentation.utils.DataError
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors
import kotlin.collections.get

internal const val INVALID_AUTHENTICATION_CREDENTIALS_MESSAGE =
    "invalid authentication credentials"

internal val ERROR_INVALID_AUTHENTICATION_CREDENTIALS =
    UiText.StringResource(R.string.error_invalid_authentication_credentials)

internal object LoginDataError {
    private val defaultError = DataError.somethingWentWrong

    private val errors: Map<String, UiText> =
        mapOf(
            INVALID_AUTHENTICATION_CREDENTIALS_MESSAGE to ERROR_INVALID_AUTHENTICATION_CREDENTIALS,
            DataError.Message.INVALID_EMAIL to ValidationErrors.emailInvalid,
        )

    fun getError(message: String?): UiText {
        return errors[message] ?: defaultError
    }
}
