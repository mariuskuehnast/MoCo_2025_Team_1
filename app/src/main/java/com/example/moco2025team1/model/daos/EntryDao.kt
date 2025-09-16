package com.example.moco2025team1.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moco2025team1.model.entities.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Insert
    suspend fun insertEntry(vararg entry: Entry): List<Long>

    @Query("SELECT * FROM entries WHERE id = :id")
    fun getEntryById(id: Long): Flow<Entry?>
}