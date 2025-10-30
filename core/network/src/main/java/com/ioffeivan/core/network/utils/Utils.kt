package com.ioffeivan.core.network.utils

import kotlinx.coroutines.CancellableContinuation
import retrofit2.Call

/**
 * Registers an action to be performed when the coroutine represented by the [continuation]
 * is cancelled. This ensures that the underlying Retrofit [Call] is also cancelled
 * if the coroutine consuming the API response is terminated early.
 *
 * @param continuation The coroutine continuation, which might be cancelled.
 */
internal fun Call<*>.registerOnCancellation(
    continuation: CancellableContinuation<*>,
) {
    continuation.invokeOnCancellation {
        try {
            cancel()
        } catch (e: Exception) {
        }
    }
}
