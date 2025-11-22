package com.ioffeivan.feature.auth.domain.repository

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isLoggedIn: Flow<Boolean>

    fun signUp(signUpCredentials: SignUpCredentials): Flow<Result<Unit>>

    fun login(loginCredentials: LoginCredentials): Flow<Result<Unit>>
}
