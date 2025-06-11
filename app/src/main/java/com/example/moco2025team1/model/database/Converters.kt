package com.example.moco2025team1.model.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    @Suppress("unused")
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    @Suppress("unused")
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}