package com.ioffeivan.feature.auth.data.source.remote.api

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.call.adapter.factory.FlowCallAdapterFactory
import com.ioffeivan.core.network.utils.NetworkJson
import com.ioffeivan.feature.auth.data.source.remote.model.LoginCredentialsDto
import com.ioffeivan.feature.auth.data.source.remote.model.LoginResponseDto
import com.ioffeivan.feature.auth.data.source.remote.model.SignUpCredentialsDto
import kotlinx.coroutines.flow.first
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
class AuthApiServiceTest {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val authApiService: AuthApiService =
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(NetworkJson.converterFactory)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
            .create()

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Nested
    inner class SignUp {
        private val signUpCredentialsDto =
            SignUpCredentialsDto(
                email = "example@example.com",
                username = "username",
                password = "p@SSw0rd",
            )

        @Test
        fun whenCalled_shouldSendCorrectRequestAndBody() =
            runTest {
                val mockResponse =
                    MockResponse()
                        .setBody("{}")
                mockWebServer.enqueue(mockResponse)

                authApiService.signUp(signUpCredentialsDto).first()
                val request = mockWebServer.takeRequest()

                assertThat(request.method).isEqualTo("POST")
                assertThat(request.path).isEqualTo("/users/")
                assertThat(request.body.readUtf8()).isEqualTo(
                    NetworkJson.json.encodeToString(signUpCredentialsDto),
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

                val actual = authApiService.signUp(signUpCredentialsDto).first()
                mockWebServer.takeRequest()

                assertThat(actual).isEqualTo(expected)
            }

        @Test
        fun whenApiReturnsBadRequest_shouldReturnsError() =
            runTest {
                val message = "invalid email"
                val expected = Result.Error(message)
                val mockResponse =
                    MockResponse()
                        .setResponseCode(400)
                        .setBody("""{ "error": "$message" }""")
                mockWebServer.enqueue(mockResponse)

                val actual = authApiService.signUp(signUpCredentialsDto).first()
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

                val actual = authApiService.signUp(signUpCredentialsDto).first()
                mockWebServer.takeRequest()

                assertThat(actual).isInstanceOf(Result.Exception::class.java)
            }
    }

    @Nested
    inner class Login {
        private val loginCredentialsDto =
            LoginCredentialsDto(
                email = "example@example.com",
                password = "p@SSw0rd",
            )
        private val loginResponseDto =
            LoginResponseDto(
                accessToken = "accessToken",
                refreshToken = "refreshToken",
            )

        @Test
        fun whenCalled_shouldSendCorrectRequestAndBody() =
            runTest {
                val mockResponse =
                    MockResponse()
                        .setBody("{}")
                mockWebServer.enqueue(mockResponse)

                authApiService.login(loginCredentialsDto).first()
                val request = mockWebServer.takeRequest()

                assertThat(request.method).isEqualTo("POST")
                assertThat(request.path).isEqualTo("/tokens/authentication")
                assertThat(request.body.readUtf8()).isEqualTo(
                    NetworkJson.json.encodeToString(loginCredentialsDto),
                )
            }

        @Test
        fun whenApiReturnsSuccess_shouldReturnsSuccess() =
            runTest {
                val expected = Result.Success(loginResponseDto)
                val mockResponse =
                    MockResponse()
                        .setBody(NetworkJson.json.encodeToString(loginResponseDto))
                mockWebServer.enqueue(mockResponse)

                val actual = authApiService.login(loginCredentialsDto).first()
                mockWebServer.takeRequest()

                assertThat(actual).isEqualTo(expected)
            }

        @Test
        fun whenApiReturnsBadRequest_shouldReturnsError() =
            runTest {
                val message = "invalid credentials"
                val expected = Result.Error(message)
                val mockResponse =
                    MockResponse()
                        .setResponseCode(400)
                        .setBody("""{ "error": "$message" }""")
                mockWebServer.enqueue(mockResponse)

                val actual = authApiService.login(loginCredentialsDto).first()
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

                val actual = authApiService.login(loginCredentialsDto).first()
                mockWebServer.takeRequest()

                assertThat(actual).isInstanceOf(Result.Exception::class.java)
            }
    }
}
