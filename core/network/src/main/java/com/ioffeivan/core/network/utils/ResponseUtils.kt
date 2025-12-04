package com.ioffeivan.core.network.utils

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.model.ErrorDto
import retrofit2.Response

/**
 * Converts this [Response] to a [Result] object.
 *
 * @param T The type of the response body.
 * @return A [Result] representing success, error, or exception.
 */
fun <T> Response<T>.toResult(): Result<T> {
    return try {
        if (this.isSuccessful) {
            val body = this.body()

            if (body != null) {
                Result.Success(data = body)
            } else {
                Result.Error(message = "Response body is null")
            }
        } else {
            val errorDto =
                this.errorBody()?.let { error ->
                    NetworkJson.json.decodeFromString<ErrorDto>(error.string())
                }

            Result.Error(message = errorDto?.message)
        }
    } catch (e: Throwable) {
        Result.Exception(e)
    }
}
