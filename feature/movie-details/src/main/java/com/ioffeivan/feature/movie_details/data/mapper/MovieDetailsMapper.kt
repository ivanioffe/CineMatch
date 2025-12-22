package com.ioffeivan.feature.movie_details.data.mapper

import com.ioffeivan.core.network.BuildConfig
import com.ioffeivan.feature.movie_details.data.source.remote.model.MovieDetailsDto
import com.ioffeivan.feature.movie_details.domain.model.MovieDetails

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        genres = genres,
        overview = overview,
        year = year,
        imageUrl = "${BuildConfig.BACKEND_BASE_URL}images" + imageUrl,
    )
}
