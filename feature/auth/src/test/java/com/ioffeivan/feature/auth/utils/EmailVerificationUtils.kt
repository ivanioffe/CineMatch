package com.ioffeivan.feature.auth.utils

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.auth.domain.model.EmailVerificationRequest
import com.ioffeivan.feature.auth.domain.model.ResendOtpRequest
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.support.ParameterDeclarations
import java.io.IOException
import java.util.stream.Stream

val testResendOtpRequest =
    ResendOtpRequest(
        email = "example@example.com",
    )

val testEmailVerificationRequest =
    EmailVerificationRequest(
        otp = "12345",
    )

class VerifyWithOtpResultArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(
        parameters: ParameterDeclarations?,
        context: ExtensionContext?,
    ): Stream<out Arguments?>? {
        return Stream.of(
            Arguments.of(Result.Success(Unit)),
            Arguments.of(Result.Error("Invalid one time password")),
            Arguments.of(Result.Exception(IOException())),
        )
    }
}

class ResendOtpResultArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(
        parameters: ParameterDeclarations?,
        context: ExtensionContext?,
    ): Stream<out Arguments?>? {
        return Stream.of(
            Arguments.of(Result.Success(Unit)),
            Arguments.of(Result.Error("Rate limit exceeded")),
            Arguments.of(Result.Exception(IOException())),
        )
    }
}
