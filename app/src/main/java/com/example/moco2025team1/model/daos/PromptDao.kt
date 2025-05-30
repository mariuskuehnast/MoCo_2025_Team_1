package com.example.moco2025team1.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moco2025team1.model.entities.Prompt
import java.util.UUID

@Dao
interface PromptDao {

    @Insert
    suspend fun insertPrompt(vararg prompt: Prompt): List<Long>

    @Query("SELECT * FROM prompts")
    suspend fun getTodaysPrompts(): List<Prompt>
}