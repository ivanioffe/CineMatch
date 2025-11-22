package com.ioffeivan.feature.auth.data.source.local

import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    val isLoggedIn: Flow<Boolean>

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)
}
