package com.ioffeivan.feature.auth.data.source.remote.di

import com.ioffeivan.feature.auth.data.source.remote.data_source.EmailVerificationRemoteDataSource
import com.ioffeivan.feature.auth.data.source.remote.data_source.RetrofitEmailVerificationRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface EmailVerificationRemoteModuleBinder {
    @Binds
    fun bindEmailVerificationRemoteDataSource(
        impl: RetrofitEmailVerificationRemoteDataSource,
    ): EmailVerificationRemoteDataSource
}
