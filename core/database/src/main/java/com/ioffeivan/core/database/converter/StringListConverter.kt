package com.ioffeivan.core.database.converter

import androidx.room.TypeConverter

internal class StringListConverter {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return data.split(",")
    }
}
