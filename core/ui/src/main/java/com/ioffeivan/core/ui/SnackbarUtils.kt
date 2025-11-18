package com.ioffeivan.core.ui

import androidx.compose.material3.SnackbarResult

/**
 * Type alias for a suspended function that displays a Snackbar.
 *
 * This function takes the message and an optional action label, and returns
 * a [SnackbarResult] indicating whether the action button was clicked or the Snackbar was dismissed.
 */
typealias ShowSnackbar = suspend (message: String, actionLabel: String?) -> SnackbarResult
