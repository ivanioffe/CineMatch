package com.ioffeivan.core.network.call.adapter

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.model.ErrorDto
import com.ioffeivan.core.network.utils.registerOnCancellation
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resume

/**
 * A custom Retrofit [CallAdapter] that converts a standard Retrofit API method
 * (which would typically return a `Call<T>`) into a Kotlin [Flow] that emits a sealed
 * [Result] class ([Result.Success], [Result.Error], or [Result.Exception]).
 *
 * This adapter encapsulates all network response handling (success, HTTP errors, and exceptions)
 * directly at the network layer.
 *
 * @param responseType The type of the final data ([T]) expected in the response body.
 */
internal class ResultCallAdapter<T>(
    private val responseType: Type,
) : CallAdapter<T, Flow<Result<T>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Flow<Result<T>> =
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
 * 2. Converting a successful/unsuccessful HTTP response into [Result.Success] or [Result.Error].
 * 3. Converting a network/runtime failure into [Result.Exception].
 * @param continuation The coroutine continuation, which is resumed with the final `Result<T>`.
 */
private fun <T> Call<T>.registerCallback(
    continuation: CancellableContinuation<Result<T>>,
) {
    enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                continuation.resumeWith(
                    runCatching {
                        val body = response.body()
                        if (response.isSuccessful && body != null) {
                            Result.Success(data = body)
                        } else {
                            val errorDto =
                                response.errorBody()?.let { error ->
                                    val json = Json { ignoreUnknownKeys = true }
                                    json.decodeFromString<ErrorDto>(error.string())
                                }

                            Result.Error(message = errorDto?.message)
                        }
                    },
                )
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                continuation.resume(Result.Exception(throwable))
            }
        },
    )
}
