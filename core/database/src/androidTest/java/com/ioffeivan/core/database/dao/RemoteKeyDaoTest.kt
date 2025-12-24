package com.ioffeivan.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.database.AppDatabase
import com.ioffeivan.core.database.model.RemoteKeyEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RemoteKeyDaoTest {
    private lateinit var remoteKeyDao: RemoteKeyDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
            ).build()
        remoteKeyDao = db.remoteKeyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getByLabel_whenExists_shouldReturnsEntity() =
        runTest {
            val entity = RemoteKeyEntity(label = "test_label", nextKey = 2)
            remoteKeyDao.upsert(entity)

            val result = remoteKeyDao.getByLabel("test_label")

            assertThat(result).isEqualTo(entity)
        }

    @Test
    fun getByLabel_whenNotExists_shouldReturnsNull() =
        runTest {
            val result = remoteKeyDao.getByLabel("non_existent_label")

            assertThat(result).isNull()
        }

    @Test
    fun upsert_insertsNewEntity() =
        runTest {
            val entity = RemoteKeyEntity(label = "new_label", nextKey = 3)
            remoteKeyDao.upsert(entity)

            val result = remoteKeyDao.getByLabel("new_label")

            assertThat(result).isEqualTo(entity)
        }

    @Test
    fun upsert_updatesExistingEntity() =
        runTest {
            val original = RemoteKeyEntity(label = "update_label", nextKey = 1)
            remoteKeyDao.upsert(original)

            val updated = original.copy(nextKey = 5)
            remoteKeyDao.upsert(updated)

            val result = remoteKeyDao.getByLabel("update_label")

            assertThat(result).isEqualTo(updated)
        }

    @Test
    fun deleteByLabel_whenExists_shouldRemovesEntity() =
        runTest {
            val entity = RemoteKeyEntity(label = "delete_label", nextKey = 4)
            remoteKeyDao.upsert(entity)

            remoteKeyDao.deleteByLabel("delete_label")

            val result = remoteKeyDao.getByLabel("delete_label")

            assertThat(result).isNull()
        }

    @Test
    fun deleteByLabel_whenNotExists_shouldDoesNothing() =
        runTest {
            remoteKeyDao.deleteByLabel("non_existent")

            val result = remoteKeyDao.getByLabel("non_existent")

            assertThat(result).isNull()
        }
}
