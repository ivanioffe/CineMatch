package com.ioffeivan.feature.movie_details.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val genres: List<String>,
    val overview: String,
    val year: Int,
    @SerialName("backdrop_path") val imageUrl: String,
)
