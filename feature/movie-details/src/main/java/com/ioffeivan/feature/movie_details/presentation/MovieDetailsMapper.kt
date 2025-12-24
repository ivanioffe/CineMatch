package com.ioffeivan.feature.movie_details.presentation

import com.ioffeivan.feature.movie_details.domain.model.MovieDetails

fun MovieDetails.toMovieDetailsState(): MovieDetailsState {
    return MovieDetailsState(
        id = id,
        title = title,
        genres = genres.joinToString(", "),
        overview = overview,
        year = year.toString(),
        imageUrl = imageUrl,
        isLoading = false,
        isError = false,
    )
}
