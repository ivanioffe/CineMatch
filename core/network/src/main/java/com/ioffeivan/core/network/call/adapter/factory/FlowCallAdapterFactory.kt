package com.ioffeivan.core.network.call.adapter.factory

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.call.adapter.FlowBodyCallAdapter
import com.ioffeivan.core.network.call.adapter.FlowResultCallAdapter
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * A custom Retrofit [CallAdapter.Factory] that provides adapters for methods
 * whose return type is a Kotlin [Flow].
 *
 * This factory inspects the type argument of the Flow to determine which specific
 * adapter ([FlowResultCallAdapter] or [FlowBodyCallAdapter]) should be created.
 */
class FlowCallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        // Check if the return type is a Flow. If not, this factory cannot handle it.
        if (Flow::class.java != getRawType(returnType)) {
            return null
        }

        // Ensure Flow is parameterized (e.g., Flow<T> and not just Flow).
        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Flow return type must be parameterized as Flow<Foo>",
            )
        }

        // Extract the inner type (Foo) from Flow<Foo>. This is Result<T> or T.
        val responseType = getParameterUpperBound(0, returnType)

        // Extract the raw class of the inner type (i.e., Result.class or T.class).
        val rawDeferredType = getRawType(responseType)

        // Check if the inner type is 'Result' (Flow<Result<T>>).
        return if (rawDeferredType == Result::class.java) {
            // Ensure Result is also parameterized (e.g., Result<T> and not just Result).
            if (responseType !is ParameterizedType) {
                throw IllegalStateException(
                    "Result must be parameterized as Result<Foo> or Result<out Foo>",
                )
            }

            FlowResultCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {
            FlowBodyCallAdapter<Any>(responseType)
        }
    }

    /**
     * Helper method to create an instance of the factory.
     */
    companion object {
        fun create(): FlowCallAdapterFactory = FlowCallAdapterFactory()
    }
}
