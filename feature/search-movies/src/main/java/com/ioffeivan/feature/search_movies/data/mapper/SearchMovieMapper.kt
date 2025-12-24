package com.ioffeivan.feature.search_movies.data.mapper

import com.ioffeivan.core.database.model.SearchMovieEntity
import com.ioffeivan.core.network.BuildConfig
import com.ioffeivan.feature.search_movies.data.source.remote.model.SearchMovieDto
import com.ioffeivan.feature.search_movies.domain.model.SearchMovie

fun SearchMovieDto.toEntity(): SearchMovieEntity {
    return SearchMovieEntity(
        id = id,
        title = title,
        genres = genres,
        year = year,
        imageUrl = "${BuildConfig.BACKEND_BASE_URL}images" + imageUrl,
    )
}

fun SearchMovieEntity.toDomain(): SearchMovie {
    return SearchMovie(
        id = id,
        title = title,
        genres = genres,
        year = year,
        imageUrl = imageUrl,
    )
}
