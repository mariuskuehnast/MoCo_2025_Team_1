package com.example.moco2025team1.model.stores

import android.content.SharedPreferences
import com.example.moco2025team1.model.daos.PromptDao
import com.example.moco2025team1.model.entities.Prompt
import java.time.LocalDate
import androidx.core.content.edit

class PromptStore (
    private val promptDao: PromptDao,
    // SharedPreferences is Android’s built-in key-value storage for saving simple data (like settings or flags).
    // It persists values across app launches by writing to an XML file in your app’s private storage.
    private val preferences: SharedPreferences,
    private val todayProvider: () -> LocalDate = { LocalDate.now() }
    ) {

    private val seedContents = listOf(
        "What made you smile today?",
        "Describe a challenge you overcame.",
        "What are you grateful for right now?",
        "Write about a moment you felt calm.",
        "What did you learn today?",
        "Who helped you today and how?",
        "One goal you have for tomorrow is…",
        "How are you feeling in this moment?",
        "What inspired you today?",
        "What act of kindness did you witness or do today?",
        "What is one thing you wish you did differently?",
        "What made you feel proud of yourself?",
        "How did you take care of yourself today?",
        "What’s one worry you can let go of?",
        "What small victory did you celebrate?",
        "What question will you explore tomorrow?"
    )

    suspend fun init() {
        val count = promptDao.getAllPrompts().size

        if (count != seedContents.size) {
            promptDao.clearPrompts()
            fillWithValues()
        }
    }


    private suspend fun fillWithValues() {
        val prompts = seedContents.map { Prompt(content = it) }.toTypedArray()
        promptDao.insertPrompt(*prompts)
    }

    suspend fun insertPrompt(prompt: Prompt) {
        promptDao.insertPrompt(prompt)
    }

    suspend fun getDailyPrompts(): List<Prompt> = getDailyPrompts(todayProvider())

    //only to test if prompts actually change the next day
    suspend fun getDailyPrompts(fakeDate: LocalDate): List<Prompt> {
        val keyDate = "last_prompt_date"
        val keySelection = "last_prompt_set"

        val allPrompts = promptDao.getAllPrompts()
        //val todayAsString = todayProvider().toString()
        val todayAsString = fakeDate.toString()

        val savedDate      = preferences.getString(keyDate, null)
        val savedSelection = preferences.getStringSet(keySelection, emptySet())!!

        if (savedDate == todayAsString && savedSelection.size == 3) {
            val cached = allPrompts.filter { it.content in savedSelection }
            return cached
        }

        val pickedPrompts = allPrompts.shuffled().take(3)

        preferences.edit {
            putString(keyDate, todayAsString)
            putStringSet(keySelection, pickedPrompts.map { it.content }.toSet())
        }

        return pickedPrompts
    }
}