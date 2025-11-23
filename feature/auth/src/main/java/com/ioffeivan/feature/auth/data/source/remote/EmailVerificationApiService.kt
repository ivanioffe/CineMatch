package com.ioffeivan.feature.auth.data.source.remote

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.data.source.remote.model.EmailVerificationRequestDto
import com.ioffeivan.feature.auth.data.source.remote.model.ResendOtpRequestDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

internal interface EmailVerificationApiService {
    @PUT("users/activate")
    suspend fun verifyWithOtp(
        @Body emailVerificationRequest: EmailVerificationRequestDto,
    ): Result<Unit>

    @POST("tokens/activation")
    suspend fun resendOtp(
        @Body resendOtpRequest: ResendOtpRequestDto,
    ): Result<Unit>
}
