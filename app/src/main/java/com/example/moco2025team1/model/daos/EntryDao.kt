package com.example.moco2025team1.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moco2025team1.model.entities.Entry
import com.example.moco2025team1.model.entities.EntryRecipientCrossRef
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface EntryDao {
    @Insert
    suspend fun insertEntry(entry: Entry): Long

    @Insert
    suspend fun insertEntryRecipientCrossRef(crossRef: EntryRecipientCrossRef)

    @Query("SELECT * FROM entries WHERE id = :id")
    fun getEntryById(id: Long): Flow<Entry?>

    @Query(
        """
        UPDATE entries
        SET recipientId = :recipientId
        WHERE id = :entryId
    """
    )
    suspend fun sendEntry(entryId: Long, recipientId: Long)

    @Query("UPDATE entry_recipients SET viewedAt = :whenViewed WHERE entryId = :entryId AND recipientId = :userId")
    suspend fun markViewed(entryId: Long, userId: Long, whenViewed: Date)

    data class PendingEntryFromFriend(
        val senderId: Long,
        val count: Int,
        val latestEntryId: Long
    )

    @Query(
        """
        SELECT entries.senderId AS senderId,
               COUNT(*)         AS count,
               MAX(entries.id)  AS latestEntryId
        FROM entries
            INNER JOIN entry_recipients ON entries.id = entry_recipients.entryId 
        WHERE
            entry_recipients.recipientId = :recipientId AND
            viewedAt IS NULL
        GROUP BY senderId
    """
    )
    fun getPendingEntriesFromFriends(recipientId: Long): Flow<List<PendingEntryFromFriend>>
}
