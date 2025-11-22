package com.ioffeivan.feature.auth.data.source.remote

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.model.LoginCredentialsDto
import com.ioffeivan.feature.auth.data.source.remote.model.LoginResponseDto
import com.ioffeivan.feature.auth.data.source.remote.model.SignUpCredentialsDto
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {
    fun signUp(dto: SignUpCredentialsDto): Flow<Result<Unit>>

    fun login(dto: LoginCredentialsDto): Flow<Result<LoginResponseDto>>
}
