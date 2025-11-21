package com.ioffeivan.feature.auth.presentation.utils

import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R

internal object DataError {
    object Message {
        const val INVALID_EMAIL = "email: must be a valid email address."
    }

    val somethingWentWrong = UiText.StringResource(R.string.error_something_went_wrong)
}
