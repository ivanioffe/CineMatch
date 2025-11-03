package com.ioffeivan.feature.auth.domain.usecase

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.model.SignUpCredentials
import com.ioffeivan.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(signUpCredentials: SignUpCredentials): Flow<Result<Unit>> {
        return authRepository.signUp(signUpCredentials)
    }
}
