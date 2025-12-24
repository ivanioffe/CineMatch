package com.ioffeivan.core.network.utils

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.datastore_auth.AuthManager
import com.ioffeivan.core.network.api.TokenApiService
import com.ioffeivan.core.network.model.RefreshTokensRequestDto
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val authManager: AuthManager,
    private val tokenApiService: TokenApiService,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken =
            runBlocking {
                authManager.getRefreshToken()
            } ?: return null

        return runBlocking {
            val result =
                tokenApiService.refreshTokens(
                    RefreshTokensRequestDto(refreshToken),
                )

            when (result) {
                is Result.Success -> {
                    authManager.saveAccessToken(result.data.accessToken)
                    authManager.saveRefreshToken(result.data.refreshToken)

                    response.request.newBuilder()
                        .authorizationHeader(result.data.accessToken)
                        .build()
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
}
