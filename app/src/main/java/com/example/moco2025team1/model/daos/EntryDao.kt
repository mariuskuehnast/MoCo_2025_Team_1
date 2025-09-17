package com.example.moco2025team1.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moco2025team1.model.entities.Entry
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface EntryDao {
    @Insert
    suspend fun insertEntry(entry: Entry): Long

    @Query("SELECT * FROM entries WHERE id = :id")
    fun getEntryById(id: Long): Flow<Entry?>

    @Query("""
        UPDATE entries
        SET recipientId = :recipientId
        WHERE id = :entryId
    """)
    suspend fun sendEntry(entryId: Long, recipientId: Long)

    @Query("UPDATE entries SET viewedAt = :whenViewed WHERE id = :entryId AND viewedAt IS NULL")
    suspend fun markViewed(entryId: Long, whenViewed: Date)

    data class PendingEntryFromFriend(
        val senderId: Long,
        val count: Int,
        val latestEntryId: Long
    )

    @Query("""
        SELECT senderId AS senderId,
               COUNT(*)  AS count,
               MAX(id)   AS latestEntryId
        FROM entries
        WHERE recipientId = :recipientId AND viewedAt IS NULL
        GROUP BY senderId
    """)
    fun getPendingEntriesFromFriends(recipientId: Long): Flow<List<PendingEntryFromFriend>>
}
