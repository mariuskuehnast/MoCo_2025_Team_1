package com.example.moco2025team1.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "entry_recipients",
    primaryKeys = ["entryId", "recipientId"], foreignKeys = [
        ForeignKey(entity = Entry::class, parentColumns = ["id"], childColumns = ["entryId"]),
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["recipientId"]),
    ]
)
data class EntryRecipientCrossRef(
    val entryId: Long,
    val recipientId: Long
)