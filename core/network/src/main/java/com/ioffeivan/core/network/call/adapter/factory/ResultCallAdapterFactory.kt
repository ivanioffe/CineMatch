package com.ioffeivan.core.network.call.adapter.factory

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.call.adapter.ResultCallAdapter
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * A custom Retrofit [CallAdapter.Factory] that provides adapters for methods
 * whose return type is a [Call] of [Result].
 *
 * This factory inspects the type argument of the Call to determine if it can handle the type.
 */
class ResultCallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        // Check if the return type is a Call. If not, this factory cannot handle it.
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        // Extract the inner type (Foo) from Call<Foo>. This is Result<T>.
        val callType = getParameterUpperBound(0, returnType as ParameterizedType)

        // Check if the inner type is 'Result' (Call<Result<T>>).
        if (getRawType(callType) != Result::class.java) {
            return null
        }

        // Extract the inner type T from Result<T>
        val resultType = getParameterUpperBound(0, callType as ParameterizedType)

        return ResultCallAdapter<Any>(resultType)
    }

    /**
     * Helper method to create an instance of the factory.
     */
    companion object {
        fun create(): ResultCallAdapterFactory = ResultCallAdapterFactory()
    }
}
