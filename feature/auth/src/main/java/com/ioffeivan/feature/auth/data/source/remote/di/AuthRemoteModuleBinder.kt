package com.ioffeivan.feature.auth.data.source.remote.di

import com.ioffeivan.feature.auth.data.source.remote.data_source.AuthRemoteDataSource
import com.ioffeivan.feature.auth.data.source.remote.data_source.RetrofitAuthRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthRemoteModuleBinder {
    @Binds
    fun bindAuthRemoteDataSource(
        impl: RetrofitAuthRemoteDataSource,
    ): AuthRemoteDataSource
}
