package com.ioffeivan.core.database.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.database.AppDatabase
import com.ioffeivan.core.database.model.SearchMovieEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SearchMoviesDaoTest {
    private lateinit var searchMoviesDao: SearchMoviesDao
    private lateinit var db: AppDatabase

    private val searchMovieEntityTest1 =
        SearchMovieEntity(
            1,
            "Inception",
            listOf("Sci-Fi"),
            2010,
            "url1",
        )

    private val searchMovieEntityTest2 =
        SearchMovieEntity(
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
        searchMoviesDao = db.searchMoviesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun pagingSource_handlesNoMatchQuery() =
        runTest {
            searchMoviesDao.upsertAll(listOf(searchMovieEntityTest1))

            val result = loadPagingData("Nonexistent")

            assertThat(result).isEmpty()
        }

    @Test
    fun pagingSource_handlesPartialMatch() =
        runTest {
            searchMoviesDao.upsertAll(listOf(searchMovieEntityTest1))

            val result = loadPagingData("cep")
            assertThat(result).containsExactly(searchMovieEntityTest1)
        }

    @Test
    fun pagingSource_handlesCaseInsensitivity() =
        runTest {
            searchMoviesDao.upsertAll(listOf(searchMovieEntityTest1))

            val result = loadPagingData("inception")
            assertThat(result).containsExactly(searchMovieEntityTest1)
        }

    @Test
    fun upsertAll_insertsSingleItemCorrectly() =
        runTest {
            searchMoviesDao.upsertAll(
                listOf(searchMovieEntityTest1),
            )

            val result = loadPagingData("Inception")

            assertThat(result).containsExactly(searchMovieEntityTest1)
        }

    @Test
    fun upsertAll_insertsMultipleItemsCorrectly() =
        runTest {
            searchMoviesDao.upsertAll(listOf(searchMovieEntityTest1, searchMovieEntityTest2))

            val result = loadPagingData("In")

            assertThat(result).containsExactly(searchMovieEntityTest1, searchMovieEntityTest2)
        }

    @Test
    fun upsertAll_updatesExistingItem() =
        runTest {
            val original = searchMovieEntityTest1
            searchMoviesDao.upsertAll(listOf(original))

            val updated = original.copy(genres = listOf("Sci-Fi", "Thriller"))
            searchMoviesDao.upsertAll(listOf(updated))

            val result = loadPagingData("Inception")

            assertThat(result).containsExactly(updated)
        }

    @Test
    fun upsertAll_handlesEmptyList() =
        runTest {
            searchMoviesDao.upsertAll(emptyList())

            val result = loadPagingData("anything")

            assertThat(result).isEmpty()
        }

    @Test
    fun clearAll_removesAllData() =
        runTest {
            searchMoviesDao.upsertAll(listOf(searchMovieEntityTest1))
            searchMoviesDao.clearAll()

            val result = loadPagingData("Inception")

            assertThat(result).isEmpty()
        }

    private suspend fun loadPagingData(query: String): List<SearchMovieEntity> {
        val pagingSource = searchMoviesDao.pagingSource(query)
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
