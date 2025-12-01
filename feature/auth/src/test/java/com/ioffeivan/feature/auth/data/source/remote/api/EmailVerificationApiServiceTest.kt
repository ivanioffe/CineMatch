package com.ioffeivan.feature.auth.data.source.remote.api

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.call.adapter.factory.ResultCallAdapterFactory
import com.ioffeivan.core.network.utils.NetworkJson
import com.ioffeivan.feature.auth.utils.testEmailVerificationRequestDto
import com.ioffeivan.feature.auth.utils.testResendOtpRequestDto
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import retrofit2.Retrofit
import retrofit2.create

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailVerificationApiServiceTest {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val emailVerificationApiService: EmailVerificationApiService =
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(NetworkJson.converterFactory)
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
            .create()

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Nested
    inner class VerifyWithOtp {
        @Test
        fun whenCalled_shouldSendCorrectRequestAndBody() =
            runTest {
                val mockResponse =
                    MockResponse()
                        .setBody("{}")
                mockWebServer.enqueue(mockResponse)

                emailVerificationApiService.verifyWithOtp(testEmailVerificationRequestDto)
                val request = mockWebServer.takeRequest()

                assertThat(request.method).isEqualTo("PUT")
                assertThat(request.path).isEqualTo("/users/activate")
                assertThat(request.body.readUtf8()).isEqualTo(
                    NetworkJson.json.encodeToString(testEmailVerificationRequestDto),
                )
            }

        @Test
        fun whenApiReturnsSuccess_shouldReturnsSuccess() =
            runTest {
                val expected = Result.Success(Unit)
                val mockResponse =
                    MockResponse()
                        .setBody("{}")
                mockWebServer.enqueue(mockResponse)

                val actual =
                    emailVerificationApiService.verifyWithOtp(testEmailVerificationRequestDto)
                mockWebServer.takeRequest()

                assertThat(actual).isEqualTo(expected)
            }

        @Test
        fun whenApiReturnsBadRequest_shouldReturnsError() =
            runTest {
                val message = "Invalid one time password"
                val expected = Result.Error(message)
                val mockResponse =
                    MockResponse()
                        .setResponseCode(400)
                        .setBody("""{ "error": "$message" }""")
                mockWebServer.enqueue(mockResponse)

                val actual =
                    emailVerificationApiService.verifyWithOtp(testEmailVerificationRequestDto)
                mockWebServer.takeRequest()

                assertThat(actual).isEqualTo(expected)
            }

        @Test
        fun whenApiThrowException_shouldReturnsException() =
            runTest {
                val mockResponse =
                    MockResponse()
                        .setResponseCode(400)
                        .setBody("""{ "erro": "message" }""")
                mockWebServer.enqueue(mockResponse)

                val actual =
                    emailVerificationApiService.verifyWithOtp(testEmailVerificationRequestDto)
                mockWebServer.takeRequest()

                assertThat(actual).isInstanceOf(Result.Exception::class.java)
            }
    }

    @Nested
    inner class ResendOtp {
        @Test
        fun whenCalled_shouldSendCorrectRequestAndBody() =
            runTest {
                val mockResponse =
                    MockResponse()
                        .setBody("{}")
                mockWebServer.enqueue(mockResponse)

                emailVerificationApiService.resendOtp(testResendOtpRequestDto)
                val request = mockWebServer.takeRequest()

                assertThat(request.method).isEqualTo("POST")
                assertThat(request.path).isEqualTo("/tokens/activation")
            }

        @Test
        fun whenApiReturnsSuccess_shouldReturnsSuccess() =
            runTest {
                val expected = Result.Success(Unit)
                val mockResponse =
                    MockResponse()
                        .setBody("{}")
                mockWebServer.enqueue(mockResponse)

                val actual = emailVerificationApiService.resendOtp(testResendOtpRequestDto)
                mockWebServer.takeRequest()

                assertThat(actual).isEqualTo(expected)
            }

        @Test
        fun whenApiReturnsBadRequest_shouldReturnsError() =
            runTest {
                val message = "Rate limit exceeded"
                val expected = Result.Error(message)
                val mockResponse =
                    MockResponse()
                        .setResponseCode(400)
                        .setBody("""{ "error": "$message" }""")
                mockWebServer.enqueue(mockResponse)

                val actual = emailVerificationApiService.resendOtp(testResendOtpRequestDto)
                mockWebServer.takeRequest()

                assertThat(actual).isEqualTo(expected)
            }

        @Test
        fun whenApiThrowException_shouldReturnsException() =
            runTest {
                val mockResponse =
                    MockResponse()
                        .setResponseCode(400)
                        .setBody("""{ "erro": "message" }""")
                mockWebServer.enqueue(mockResponse)

                val actual = emailVerificationApiService.resendOtp(testResendOtpRequestDto)
                mockWebServer.takeRequest()

                assertThat(actual).isInstanceOf(Result.Exception::class.java)
            }
    }
}
