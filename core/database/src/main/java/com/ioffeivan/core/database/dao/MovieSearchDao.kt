package com.ioffeivan.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ioffeivan.core.database.model.MovieSearchEntity

@Dao
interface MovieSearchDao {
    @Query(
        value = """
            SELECT * FROM movie_search
            WHERE title LIKE '%' || :query || '%'
        """,
    )
    fun pagingSource(query: String): PagingSource<Int, MovieSearchEntity>

    @Upsert
    suspend fun upsertAll(movies: List<MovieSearchEntity>)

    @Query(
        value = """
            DELETE FROM movie_search
        """,
    )
    suspend fun clearAll()
}
