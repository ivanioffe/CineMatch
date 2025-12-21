package com.ioffeivan.core.database

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

internal object DatabaseMigrations {
    @RenameColumn(
        tableName = "movie_search",
        fromColumnName = "image",
        toColumnName = "imageUrl",
    )
    class Schema1to2 : AutoMigrationSpec
}
