package com.ioffeivan.core.network.utils

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.datastore_auth.AuthManager
import com.ioffeivan.core.network.api.TokenApiService
import com.ioffeivan.core.network.model.RefreshTokensRequestDto
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

internal class AuthAuthenticator @Inject constructor(
    private val authManager: AuthManager,
    private val tokenApiService: TokenApiService,
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        val newAccessToken =
            runBlocking {
                mutex.withLock {
                    val accessTokenInRequest =
                        response.request.header(AUTHORIZATION_HEADER)?.replace("Bearer ", "")
                    val savedAccessToken = authManager.getAccessToken()

                    if (savedAccessToken != accessTokenInRequest && savedAccessToken != null) {
                        return@runBlocking savedAccessToken
                    }

                    val refreshToken = authManager.getRefreshToken() ?: return@runBlocking null
                    val result =
                        tokenApiService.refreshTokens(
                            RefreshTokensRequestDto(refreshToken),
                        )

                    return@runBlocking when (result) {
                        is Result.Success -> {
                            authManager.saveAccessToken(result.data.accessToken)
                            authManager.saveRefreshToken(result.data.refreshToken)

                            result.data.accessToken
                        }

                        is Result.Error -> {
                            authManager.deleteAccessToken()
                            authManager.deleteRefreshToken()

                            null
                        }

                        else -> {
                            null
                        }
                    }
                }
            }

        return if (newAccessToken != null) {
            response.request.newBuilder()
                .authorizationHeader(newAccessToken)
                .build()
        } else {
            null
        }
    }
}
