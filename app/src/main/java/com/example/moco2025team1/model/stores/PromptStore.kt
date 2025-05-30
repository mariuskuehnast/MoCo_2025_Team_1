package com.example.moco2025team1.model.stores

import com.example.moco2025team1.model.daos.PromptDao
import com.example.moco2025team1.model.entities.Prompt

class PromptStore (private val promptDao: PromptDao) {

    suspend fun insertPrompt(prompt: Prompt) {
        promptDao.insertPrompt(prompt)
    }

    suspend fun getTodaysPrompts() = promptDao.getTodaysPrompts()
}