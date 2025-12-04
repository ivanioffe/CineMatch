package com.ioffeivan.feature.auth.data.repository

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.common.result.onSuccess
import com.ioffeivan.feature.auth.data.mapper.toDto
import com.ioffeivan.feature.auth.data.source.local.AuthLocalDataSource
import com.ioffeivan.feature.auth.data.source.remote.data_source.AuthRemoteDataSource
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
) : AuthRepository {
    override val isLoggedIn: Flow<Boolean>
        get() = authLocalDataSource.isLoggedIn

    override fun signUp(signUpCredentials: SignUpCredentials): Flow<Result<Unit>> {
        return authRemoteDataSource.signUp(signUpCredentials.toDto())
    }

    override fun login(loginCredentials: LoginCredentials): Flow<Result<Unit>> {
        return authRemoteDataSource.login(loginCredentials.toDto())
            .onEach { result ->
                result.onSuccess {
                    authLocalDataSource.saveAccessToken(it.accessToken)
                    authLocalDataSource.saveRefreshToken(it.refreshToken)
                }
            }
            .map { result ->
                when (result) {
                    is Result.Success -> Result.Success(Unit)
                    is Result.Error -> Result.Error(result.message)
                    is Result.Exception -> Result.Exception(result.exception)
                }
            }
    }
}
