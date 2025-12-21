package com.ioffeivan.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ioffeivan.core.database.model.SearchMovieEntity

@Dao
interface SearchMoviesDao {
    @Query(
        value = """
            SELECT * FROM movie_search
            WHERE title LIKE '%' || :query || '%'
        """,
    )
    fun pagingSource(query: String): PagingSource<Int, SearchMovieEntity>

    @Upsert
    suspend fun upsertAll(movies: List<SearchMovieEntity>)

    @Query(
        value = """
            DELETE FROM movie_search
        """,
    )
    suspend fun clearAll()
}
