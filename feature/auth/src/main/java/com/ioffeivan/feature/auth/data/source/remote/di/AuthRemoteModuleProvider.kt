package com.ioffeivan.feature.auth.data.source.remote.di

import com.ioffeivan.core.network.di.Unauthorized
import com.ioffeivan.feature.auth.data.source.remote.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AuthRemoteModuleProvider {
    @Provides
    @Singleton
    fun provideAuthApiService(
        @Unauthorized retrofit: Retrofit,
    ): AuthApiService {
        return retrofit.create()
    }
}
