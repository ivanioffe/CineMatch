package com.ioffeivan.core.database.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.database.AppDatabase
import com.ioffeivan.core.database.model.MovieSearchEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieSearchDaoTest {
    private lateinit var movieSearchDao: MovieSearchDao
    private lateinit var db: AppDatabase

    private val movieSearchEntityTest1 =
        MovieSearchEntity(
            1,
            "Inception",
            listOf("Sci-Fi"),
            2010,
            "url1",
        )

    private val movieSearchEntityTest2 =
        MovieSearchEntity(
            2,
            "Interstellar",
            listOf("Adventure"),
            2014,
            "url2",
        )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
            ).build()
        movieSearchDao = db.movieSearchDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun upsertAll_insertsSingleItemCorrectly() =
        runTest {
            movieSearchDao.upsertAll(
                listOf(movieSearchEntityTest1),
            )

            val result = loadPagingData("Inception")

            assertThat(result).containsExactly(movieSearchEntityTest1)
        }

    @Test
    fun upsertAll_insertsMultipleItemsCorrectly() =
        runTest {
            movieSearchDao.upsertAll(listOf(movieSearchEntityTest1, movieSearchEntityTest2))

            val result = loadPagingData("In")

            assertThat(result).containsExactly(movieSearchEntityTest1, movieSearchEntityTest2)
        }

    @Test
    fun upsertAll_updatesExistingItem() =
        runTest {
            val original = movieSearchEntityTest1
            movieSearchDao.upsertAll(listOf(original))

            val updated = original.copy(genres = listOf("Sci-Fi", "Thriller"))
            movieSearchDao.upsertAll(listOf(updated))

            val result = loadPagingData("Inception")

            assertThat(result).containsExactly(updated)
        }

    @Test
    fun upsertAll_handlesEmptyList() =
        runTest {
            movieSearchDao.upsertAll(emptyList())

            val result = loadPagingData("anything")

            assertThat(result).isEmpty()
        }

    @Test
    fun clearAll_removesAllData() =
        runTest {
            movieSearchDao.upsertAll(listOf(movieSearchEntityTest1))
            movieSearchDao.clearAll()

            val result = loadPagingData("Inception")

            assertThat(result).isEmpty()
        }

    @Test
    fun pagingSource_handlesNoMatchQuery() =
        runTest {
            movieSearchDao.upsertAll(listOf(movieSearchEntityTest1))

            val result = loadPagingData("Nonexistent")

            assertThat(result).isEmpty()
        }

    @Test
    fun pagingSource_handlesPartialMatch() =
        runTest {
            movieSearchDao.upsertAll(listOf(movieSearchEntityTest1))

            val result = loadPagingData("cep")
            assertThat(result).containsExactly(movieSearchEntityTest1)
        }

    @Test
    fun pagingSource_handlesCaseInsensitivity() =
        runTest {
            movieSearchDao.upsertAll(listOf(movieSearchEntityTest1))

            val result = loadPagingData("inception")
            assertThat(result).containsExactly(movieSearchEntityTest1)
        }

    private suspend fun loadPagingData(query: String): List<MovieSearchEntity> {
        val pagingSource = movieSearchDao.pagingSource(query)
        val loadResult =
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 10,
                    placeholdersEnabled = false,
                ),
            )

        return (loadResult as? PagingSource.LoadResult.Page)?.data ?: emptyList()
    }
}
