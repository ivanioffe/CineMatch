package com.ioffeivan.feature.auth.data.source.remote.data_source

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.api.AuthApiService
import com.ioffeivan.feature.auth.data.source.remote.model.LoginCredentialsDto
import com.ioffeivan.feature.auth.data.source.remote.model.LoginResponseDto
import com.ioffeivan.feature.auth.data.source.remote.model.SignUpCredentialsDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RetrofitAuthRemoteDataSource @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRemoteDataSource {
    override fun signUp(dto: SignUpCredentialsDto): Flow<Result<Unit>> {
        return authApiService.signUp(dto)
    }

    override fun login(dto: LoginCredentialsDto): Flow<Result<LoginResponseDto>> {
        return authApiService.login(dto)
    }
}
