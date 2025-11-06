package com.ioffeivan.feature.auth.data.source.local.di

import com.ioffeivan.feature.auth.data.source.local.AuthLocalDataSource
import com.ioffeivan.feature.auth.data.source.local.DataStoreAuthLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthLocalModuleBinder {
    @Binds
    fun bindAuthLocalDataSource(
        impl: DataStoreAuthLocalDataSource,
    ): AuthLocalDataSource
}
