package com.ioffeivan.feature.search_movies.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieDto(
    val id: Int,
    val title: String,
    val genres: List<String>,
    val year: Int,
    val imageUrl: String,
)
