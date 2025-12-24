package com.ioffeivan.core.network.di

import com.ioffeivan.core.datastore_auth.AuthManager
import com.ioffeivan.core.network.interceptor.AuthInterceptor
import com.ioffeivan.core.network.utils.AuthAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AuthorizedNetworkModule {
    @Provides
    @Singleton
    @Authorized
    fun provideAuthorizedOkHttpClient(
        baseOkHttpClientBuilder: OkHttpClient.Builder,
        @Authorized interceptors: Set<@JvmSuppressWildcards Interceptor>,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val builder = baseOkHttpClientBuilder

        interceptors.forEach {
            builder.addInterceptor(it)
        }

        return builder
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    @Authorized
    fun provideAuthorizedRetrofit(
        baseRetrofitBuilder: Retrofit.Builder,
        @Authorized client: OkHttpClient,
    ): Retrofit {
        return baseRetrofitBuilder
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @IntoSet
    @Authorized
    fun provideAuthInterceptor(authManager: AuthManager): Interceptor {
        return AuthInterceptor(authManager)
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Authorized
