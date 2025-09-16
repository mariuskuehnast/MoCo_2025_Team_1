package com.example.moco2025team1.model.stores

import com.example.moco2025team1.model.daos.EntryDao
import com.example.moco2025team1.model.entities.Entry
import kotlinx.coroutines.flow.Flow

class EntryStore(
    private val entryDao: EntryDao
) {
    suspend fun insertEntry(entry: Entry) {
        entryDao.insertEntry(entry)
    }

    fun getEntryById(id: Long): Flow<Entry?> {
        return entryDao.getEntryById(id)
    }
}