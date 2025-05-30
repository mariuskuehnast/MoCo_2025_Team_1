package com.example.moco2025team1.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "prompts")
data class Prompt(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val content: String
)