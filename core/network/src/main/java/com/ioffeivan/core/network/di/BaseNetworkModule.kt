package com.ioffeivan.core.network.di

import com.ioffeivan.core.network.BuildConfig
import com.ioffeivan.core.network.call.adapter.factory.FlowCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object BaseNetworkModule {
    @Provides
    fun provideBaseOkHttpClientBuilder(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()

        interceptors.forEach { interceptor ->
            builder.addInterceptor(interceptor)
        }

        return builder
    }

    @Provides
    fun provideBaseRetrofitBuilder(
        converterFactories: Set<@JvmSuppressWildcards Converter.Factory>,
        callAdapterFactories: Set<@JvmSuppressWildcards CallAdapter.Factory>,
    ): Retrofit.Builder {
        val builder =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_BASE_URL)

        converterFactories.forEach { factory ->
            builder.addConverterFactory(factory)
        }

        callAdapterFactories.forEach { factory ->
            builder.addCallAdapterFactory(factory)
        }

        return builder
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideJsonConverterFactory(): Converter.Factory {
        val json =
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
        val contentType = "application/json".toMediaType()

        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideFlowCallAdapterFactory(): CallAdapter.Factory {
        return FlowCallAdapterFactory.create()
    }
}
