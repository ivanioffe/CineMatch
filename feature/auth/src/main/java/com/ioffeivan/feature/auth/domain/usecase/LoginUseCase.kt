package com.ioffeivan.feature.auth.domain.usecase

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.model.LoginCredentials
import com.ioffeivan.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(loginCredentials: LoginCredentials): Flow<Result<Unit>> {
        return authRepository.login(loginCredentials)
    }
}
