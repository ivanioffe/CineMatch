package com.ioffeivan.core.network.call.adapter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.lang.reflect.Type

/**
 * A custom Retrofit [CallAdapter] that converts a standard Retrofit API method
 * (which would typically return a `Call<T>`) into a Kotlin [Flow] that emits the
 * raw response body data ([T]).
 *
 * This adapter encapsulates the asynchronous Retrofit call into a suspending function,
 * throwing [HttpException] for 4xx/5xx errors and [NullPointerException] for empty bodies.
 *
 * @param responseType The type of the final data ([T]) expected in the response body.
 */
internal class BodyCallAdapter<T>(
    private val responseType: Type,
) : CallAdapter<T, Flow<T>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Flow<T> =
        flow {
            emit(call.awaitBody())
        }
}

/**
 * Executes the Retrofit [Call] asynchronously and returns the successful response body [T] directly.
 *
 * This function is suitable for scenarios where the caller only cares about the successful data
 * and wants to treat any failure (network error, non-2xx HTTP code, or null body) as a thrown exception.
 *
 * @param T The expected type of the successful response body.
 * @return The deserialized response body [T].
 * @throws Throwable The exception detailing the reason for failure (network, HTTP, or null body).
 */
private suspend fun <T> Call<T>.awaitBody(): T {
    return runCatching {
        val response = this.awaitResponse()

        if (response.isSuccessful) {
            response.body()
                ?: throw NullPointerException("Response body is null: $response")
        } else {
            throw HttpException(response)
        }
    }.getOrElse { throwable ->
        throw throwable
    }
}
