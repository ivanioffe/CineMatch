package com.ioffeivan.feature.movie_details.data.source.remote.api

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.call.adapter.factory.ResultCallAdapterFactory
import com.ioffeivan.core.network.utils.NetworkJson
import com.ioffeivan.feature.movie_details.movieDetailsDtoTest
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
class MovieDetailsApiServiceTest {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val movieDetailsApiService: MovieDetailsApiService =
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
    inner class GetMovieDetails {
        @Test
        fun whenCalled_shouldSendCorrectRequest() =
            runTest {
                val mockResponse =
                    MockResponse()
                        .setBody("{}")
                mockWebServer.enqueue(mockResponse)

                movieDetailsApiService.getMovieDetails(1)
                val request = mockWebServer.takeRequest()

                assertThat(request.method).isEqualTo("GET")
                assertThat(request.path).isEqualTo("/movie?id=1")
            }

        @Test
        fun whenApiReturnsSuccess_shouldReturnsSuccess() =
            runTest {
                val expected = Result.Success(movieDetailsDtoTest)
                val mockResponse =
                    MockResponse()
                        .setBody(NetworkJson.json.encodeToString(movieDetailsDtoTest))
                mockWebServer.enqueue(mockResponse)

                val actual =
                    movieDetailsApiService.getMovieDetails(1)
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
                    movieDetailsApiService.getMovieDetails(1)
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
                    movieDetailsApiService.getMovieDetails(1)
                mockWebServer.takeRequest()

                assertThat(actual).isInstanceOf(Result.Exception::class.java)
            }
    }
}
