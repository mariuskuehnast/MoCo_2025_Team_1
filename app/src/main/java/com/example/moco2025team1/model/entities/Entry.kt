package com.example.moco2025team1.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.Date

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    val senderUserName: String? = null,
    val prompt: String? = null,
    val content: String,
    val imageUri: String? = null,
    val createdAt: Date = Date(),
    val senderId: Long? = null,
    val recipientId: Long? = null,
    val mood: String? = null
)