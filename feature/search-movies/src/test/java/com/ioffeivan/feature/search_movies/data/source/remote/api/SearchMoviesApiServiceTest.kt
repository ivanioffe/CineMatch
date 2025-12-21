package com.ioffeivan.feature.search_movies.data.source.remote.api

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.network.utils.NetworkJson
import com.ioffeivan.feature.search_movies.data.source.remote.model.PagingMetadata
import com.ioffeivan.feature.search_movies.data.source.remote.model.SearchMovieDto
import com.ioffeivan.feature.search_movies.data.source.remote.model.SearchMoviesResponseDto
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.create

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchMoviesApiServiceTest {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val searchMoviesApiService: SearchMoviesApiService =
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(NetworkJson.converterFactory)
            .build()
            .create()

    private val searchMoviesResponseDto =
        SearchMoviesResponseDto(
            metadata = PagingMetadata(),
            movies =
                listOf(
                    SearchMovieDto(
                        id = 1,
                        title = "Title",
                        genres = listOf("genre1", "genre2"),
                        year = 2000,
                        imageUrl = "url",
                    ),
                ),
        )

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun whenCalled_shouldSendCorrectRequest() =
        runTest {
            val mockResponse =
                MockResponse()
                    .setBody(
                        NetworkJson.json.encodeToString(searchMoviesResponseDto),
                    )
            mockWebServer.enqueue(mockResponse)

            search()
            val request = mockWebServer.takeRequest()

            assertThat(request.method).isEqualTo("GET")
            assertThat(request.path).isEqualTo("/movie?title=Title&page=1&page_size=10")
        }

    @Test
    fun whenApiReturnsSuccess_shouldReturnsCorrectData() =
        runTest {
            val mockResponse =
                MockResponse()
                    .setBody(
                        NetworkJson.json.encodeToString(searchMoviesResponseDto),
                    )
            mockWebServer.enqueue(mockResponse)

            val result = search()
            mockWebServer.takeRequest()

            assertThat(result.metadata).isEqualTo(searchMoviesResponseDto.metadata)
            assertThat(result.movies).isEqualTo(searchMoviesResponseDto.movies)
        }

    @Test
    fun whenApiReturnsError_shouldThrowsException() =
        runTest {
            val mockResponse =
                MockResponse()
                    .setResponseCode(404)
                    .setBody("{\"error\": \"Not Found\"}")
            mockWebServer.enqueue(mockResponse)

            assertThrows<HttpException> { search() }

            mockWebServer.takeRequest()
        }

    @Test
    fun whenApiReturnsServerError_shouldThrowsException() =
        runTest {
            val mockResponse =
                MockResponse()
                    .setResponseCode(500)
                    .setBody("{\"error\": \"Internal Server Error\"}")
            mockWebServer.enqueue(mockResponse)

            assertThrows<HttpException> { search() }

            mockWebServer.takeRequest()
        }

    private suspend fun search(): SearchMoviesResponseDto {
        return searchMoviesApiService.search(
            query = "Title",
            page = 1,
            pageSize = 10,
        )
    }
}
