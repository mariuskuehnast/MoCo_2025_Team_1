package com.example.moco2025team1.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.Date

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    val content: String,
    val imageUri: String? = null,
    val createdAt: Date = Date()
)