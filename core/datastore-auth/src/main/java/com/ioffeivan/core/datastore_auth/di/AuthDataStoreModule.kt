package com.ioffeivan.core.datastore_auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val AUTH_DATA_STORE_NAME = "auth_data_store"

internal val Context.authDataStore by preferencesDataStore(name = AUTH_DATA_STORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
internal class AuthDataStoreModule {
    @Provides
    @Singleton
    fun provideAuthDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.authDataStore
}
