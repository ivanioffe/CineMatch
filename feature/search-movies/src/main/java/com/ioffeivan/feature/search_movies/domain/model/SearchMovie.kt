package com.ioffeivan.feature.search_movies.domain.model

data class SearchMovie(
    val id: Int,
    val title: String,
    val genres: List<String>,
    val year: Int,
    val imageUrl: String,
)
