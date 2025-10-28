package com.ioffeivan.core.common.result

suspend fun <T> Result<T>.onSuccess(
    block: suspend (data: T) -> Unit,
): Result<T> =
    apply {
        if (this is Result.Success<T>) {
            block(data)
        }
    }

suspend fun <T> Result<T>.onError(
    block: suspend (message: String?) -> Unit,
): Result<T> =
    apply {
        if (this is Result.Error) {
            block(message)
        }
    }

suspend fun <T> Result<T>.onException(
    block: suspend (exception: Throwable) -> Unit,
): Result<T> =
    apply {
        if (this is Result.Exception) {
            block(exception)
        }
    }
