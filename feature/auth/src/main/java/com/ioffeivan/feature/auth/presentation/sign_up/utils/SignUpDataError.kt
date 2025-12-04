package com.ioffeivan.feature.auth.presentation.sign_up.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.presentation.utils.DataError
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors
import kotlin.collections.get

internal const val DUPLICATE_EMAIL_MESSAGE = "duplicate email"

internal val ERROR_DUPLICATE_EMAIL = UiText.StringResource(R.string.error_email_duplicate)

internal object SignUpDataError {
    private val errors: Map<String, UiText> =
        mapOf(
            DUPLICATE_EMAIL_MESSAGE to ERROR_DUPLICATE_EMAIL,
            DataError.Message.INVALID_EMAIL to ValidationErrors.emailInvalid,
        )

    fun getError(message: String?): UiText {
        return errors[message] ?: DataError.somethingWentWrong
    }
}
