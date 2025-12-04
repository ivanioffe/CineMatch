package com.ioffeivan.core.network.call.adapter

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.utils.toResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

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
internal class FlowResultCallAdapter<T>(
    private val responseType: Type,
) : CallAdapter<T, Flow<Result<T>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Flow<Result<T>> =
        flow {
            emit(call.awaitResult())
        }
}

/**
 * Executes the Retrofit [Call] asynchronously and transforms the result into a
 * custom domain-specific [Result] type, ensuring that all network,
 * API, and serialization exceptions are safely handled.
 *
 * @param T The expected type of the successful response body.
 * @return A [Result] object containing either the successful data, an API error message, or an exception.
 */
private suspend fun <T> Call<T>.awaitResult(): Result<T> {
    return try {
        this.awaitResponse().toResult()
    } catch (throwable: Throwable) {
        Result.Exception(throwable)
    }
}
