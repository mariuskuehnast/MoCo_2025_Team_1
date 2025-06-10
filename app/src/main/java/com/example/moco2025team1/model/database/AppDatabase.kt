package com.example.moco2025team1.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moco2025team1.model.daos.PromptDao
import com.example.moco2025team1.model.entities.Entry
import com.example.moco2025team1.model.entities.Prompt

@Database(entities = [Prompt::class, Entry::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun promptDao(): PromptDao

    companion object {
        private const val DB_NAME = "appstore"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration(false).build().also { INSTANCE = it }
            }
        }
    }
}