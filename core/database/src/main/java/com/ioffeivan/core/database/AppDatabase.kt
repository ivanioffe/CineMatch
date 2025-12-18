package com.ioffeivan.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ioffeivan.core.database.converter.StringListConverter
import com.ioffeivan.core.database.dao.MovieSearchDao
import com.ioffeivan.core.database.model.MovieSearchEntity

@Database(
    entities = [
        MovieSearchEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    StringListConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieSearchDao(): MovieSearchDao
}
