package com.ioffeivan.feature.auth.data.source.remote

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.model.LoginCredentialsDto
import com.ioffeivan.feature.auth.data.source.remote.model.LoginResponseDto
import com.ioffeivan.feature.auth.data.source.remote.model.SignUpCredentialsDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthApiService {
    @POST("users/")
    fun signUp(
        @Body signUpCredentials: SignUpCredentialsDto,
    ): Flow<Result<Unit>>

    @POST("tokens/authentication")
    fun login(
        @Body loginCredentials: LoginCredentialsDto,
    ): Flow<Result<LoginResponseDto>>
}
