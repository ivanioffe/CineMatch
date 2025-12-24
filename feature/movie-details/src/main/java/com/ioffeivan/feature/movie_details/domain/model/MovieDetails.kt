package com.ioffeivan.feature.movie_details.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val genres: List<String>,
    val overview: String,
    val year: Int,
    val imageUrl: String,
)
