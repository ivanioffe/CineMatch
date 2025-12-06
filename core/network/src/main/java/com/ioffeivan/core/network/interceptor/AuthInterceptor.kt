package com.ioffeivan.core.network.interceptor

import com.ioffeivan.core.datastore_auth.AuthManager
import com.ioffeivan.core.network.utils.authorizationHeader
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor(
    private val authManager: AuthManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken =
            runBlocking {
                authManager.getAccessToken()
            } ?: ""

        val newRequest =
            chain.request().newBuilder()
                .authorizationHeader(accessToken)
                .build()

        return chain.proceed(newRequest)
    }
}
