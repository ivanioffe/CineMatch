package com.ioffeivan.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ioffeivan.core.database.converter.StringListConverter
import com.ioffeivan.core.database.dao.RemoteKeyDao
import com.ioffeivan.core.database.dao.SearchMoviesDao
import com.ioffeivan.core.database.model.RemoteKeyEntity
import com.ioffeivan.core.database.model.SearchMovieEntity

@Database(
    entities = [
        SearchMovieEntity::class,
        RemoteKeyEntity::class,
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class),
    ],
)
@TypeConverters(
    StringListConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchMoviesDao(): SearchMoviesDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}
