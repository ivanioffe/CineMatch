package com.ioffeivan.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ioffeivan.core.database.model.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Query(
        value = """
            SELECT * FROM remote_keys
            WHERE label = :label
        """,
    )
    suspend fun getByLabel(label: String): RemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(remoteKey: RemoteKeyEntity)

    @Query(
        value = """
            DELETE FROM remote_keys
            WHERE label = :label
        """,
    )
    suspend fun deleteByLabel(label: String)
}
