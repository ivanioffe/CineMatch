package com.ioffeivan.feature.auth.presentation.sign_up.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import kotlin.collections.get

internal object SignUpDataError {
    private val defaultError = UiText.StringResource(R.string.error_something_went_wrong)

    private val errors: Map<String, UiText> =
        mapOf(
            "duplicate email" to UiText.StringResource(R.string.error_email_duplicate),
        )

    fun getError(message: String?): UiText {
        return errors[message] ?: defaultError
    }
}
