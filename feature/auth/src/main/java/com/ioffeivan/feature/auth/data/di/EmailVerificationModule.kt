package com.ioffeivan.feature.auth.data.di

import com.ioffeivan.feature.auth.data.repository.EmailVerificationRepositoryImpl
import com.ioffeivan.feature.auth.domain.repository.EmailVerificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface EmailVerificationDataModule {
    @Binds
    fun bindEmailVerificationRepository(
        impl: EmailVerificationRepositoryImpl,
    ): EmailVerificationRepository
}
