package com.example.moco2025team1.model.daos

import androidx.room.Dao
import androidx.room.Insert
import com.example.moco2025team1.model.entities.Entry

@Dao
interface EntryDao {
    @Insert
    suspend fun insertEntry(vararg entry: Entry): List<Long>
}