package com.ioffeivan.core.network.api

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.model.RefreshTokensRequestDto
import com.ioffeivan.core.network.model.RefreshTokensResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApiService {
    @POST("tokens/refresh")
    suspend fun refreshTokens(
        @Body refreshTokensRequestDto: RefreshTokensRequestDto,
    ): Result<RefreshTokensResponseDto>
}
