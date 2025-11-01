package com.ioffeivan.core.network.call.adapter

import com.ioffeivan.core.network.utils.registerOnCancellation
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resumeWithException

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
            emit(
                suspendCancellableCoroutine { continuation ->
                    call.registerCallback(continuation)
                    call.registerOnCancellation(continuation)
                },
            )
        }
}

/**
 * Registers Retrofit's asynchronous [Callback] handlers `onResponse` and [onFailure]
 * to a [CancellableContinuation].
 * This function is responsible for:
 * 1. Executing the API call via `enqueue`.
 * 2. Converting a successful HTTP response into the body data [T].
 * 3. Converting an unsuccessful HTTP response (4xx/5xx) or a null body into an exception
 * protruding from the coroutine via `continuation.resumeWithException`.
 * @param continuation The coroutine continuation, which is resumed with the final data [T]
 * or an exception.
 */
private fun <T> Call<T>.registerCallback(
    continuation: CancellableContinuation<T>,
) {
    enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                continuation.resumeWith(
                    runCatching {
                        if (response.isSuccessful) {
                            response.body()
                                ?: throw NullPointerException("Response body is null: $response")
                        } else {
                            throw HttpException(response)
                        }
                    },
                )
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                continuation.resumeWithException(throwable)
            }
        },
    )
}
