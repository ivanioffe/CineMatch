package com.ioffeivan.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_search",
)
data class SearchMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val genres: List<String>,
    val year: Int,
    val imageUrl: String,
)
