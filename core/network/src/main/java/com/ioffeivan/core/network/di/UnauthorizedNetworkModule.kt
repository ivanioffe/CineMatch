package com.ioffeivan.core.network.di

import com.ioffeivan.core.network.api.TokenApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UnauthorizedNetworkModule {
    @Provides
    @Singleton
    @Unauthorized
    fun provideUnauthorizedOkHttpClient(
        baseOkHttpClientBuilder: OkHttpClient.Builder,
    ): OkHttpClient {
        return baseOkHttpClientBuilder
            .build()
    }

    @Provides
    @Singleton
    @Unauthorized
    fun provideUnauthorizedRetrofit(
        baseRetrofitBuilder: Retrofit.Builder,
        @Unauthorized client: OkHttpClient,
    ): Retrofit {
        return baseRetrofitBuilder
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenApiService(
        @Unauthorized retrofit: Retrofit,
    ): TokenApiService {
        return retrofit.create()
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Unauthorized
