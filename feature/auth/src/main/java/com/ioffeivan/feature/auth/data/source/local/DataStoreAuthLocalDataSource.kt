package com.ioffeivan.feature.auth.data.source.local

import com.ioffeivan.core.datastore_auth.AuthManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DataStoreAuthLocalDataSource @Inject constructor(
    private val authManager: AuthManager,
) : AuthLocalDataSource {
    override val isLoggedIn: Flow<Boolean>
        get() = authManager.isLoggedIn

    override suspend fun saveAccessToken(accessToken: String) {
        authManager.saveAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        authManager.saveRefreshToken(refreshToken)
    }
}
