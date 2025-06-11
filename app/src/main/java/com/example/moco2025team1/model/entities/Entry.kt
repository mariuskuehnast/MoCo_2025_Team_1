package com.example.moco2025team1.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long,

    val content: String,
    val createdAt: Date = Date()
)