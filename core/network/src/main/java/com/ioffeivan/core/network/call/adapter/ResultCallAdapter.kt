package com.ioffeivan.core.network.call.adapter

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.utils.toResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * A custom Retrofit [CallAdapter] that converts a standard Retrofit API method
 * (which would typically return a [Call]<T>) into a [Call] that emits a sealed
 * [Result] class ([Result.Success], [Result.Error], or [Result.Exception]).
 *
 * @param responseType The type of the final data ([T]) expected in the response body.
 */
internal class ResultCallAdapter<T>(
    private val responseType: Type,
) : CallAdapter<T, Call<Result<T>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<Result<T>> {
        return ResultCall(call)
    }
}

/**
 * A wrapper around a Retrofit [Call]<T> that transforms the response into a [Result]<T>,
 * handling success, errors, and exceptions.
 *
 * @param call The original Retrofit [Call]<T> to wrap.
 */
private class ResultCall<T>(
    private val call: Call<T>,
) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val result = response.toResult()

                    callback.onResponse(this@ResultCall, Response.success(result))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    val result = Result.Exception(t)

                    callback.onResponse(this@ResultCall, Response.success(result))
                }
            },
        )
    }

    override fun execute(): Response<Result<T>> = throw NotImplementedError()

    override fun clone(): Call<Result<T>> = ResultCall(call.clone())

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    override fun isExecuted(): Boolean = call.isExecuted

    override fun isCanceled(): Boolean = call.isCanceled

    override fun cancel() = call.cancel()
}
