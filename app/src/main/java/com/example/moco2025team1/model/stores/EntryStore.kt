package com.example.moco2025team1.model.stores

import com.example.moco2025team1.model.daos.EntryDao
import com.example.moco2025team1.model.entities.Entry
import com.example.moco2025team1.model.entities.EntryRecipientCrossRef
import com.example.moco2025team1.model.entities.User
import kotlinx.coroutines.flow.Flow
import java.util.Date

class EntryStore(
    private val entryDao: EntryDao
) {
    suspend fun insertEntry(entry: Entry) = entryDao.insertEntry(entry)
    fun getEntryById(id: Long) = entryDao.getEntryById(id)
    suspend fun send(entryId: Long, recipients: List<User>) {
        val crossRefs = recipients.map { user -> EntryRecipientCrossRef(entryId, user.id) }
        crossRefs.forEach {
            entryDao.insertEntryRecipientCrossRef(it)
        }
    }

    suspend fun markViewed(entryId: Long) =
        entryDao.markViewed(entryId, Date())

    fun getPendingEntriesFromFriends(recipientId: Long) =
        entryDao.getPendingEntriesFromFriends(recipientId)
}