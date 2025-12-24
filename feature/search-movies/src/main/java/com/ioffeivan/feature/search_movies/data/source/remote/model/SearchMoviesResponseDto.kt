package com.ioffeivan.feature.search_movies.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMoviesResponseDto(
    val metadata: PagingMetadata,
    val movies: List<SearchMovieDto>?,
)

@Serializable
data class PagingMetadata(
    @SerialName("current_page") val currentPage: Int = 1,
    @SerialName("first_page") val firstPage: Int = 1,
    @SerialName("last_page") val lastPage: Int = 1,
)
