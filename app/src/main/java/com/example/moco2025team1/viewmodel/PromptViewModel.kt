package com.example.moco2025team1.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.Prompt
import com.example.moco2025team1.model.stores.PromptStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// you will not only have one single viewmodel, but maybe as many as you have screens
class PromptViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app.applicationContext)
    private val promptDao = db.promptDao()
    private val promptStore = PromptStore(promptDao).apply {
        viewModelScope.launch(Dispatchers.IO) { }
    }

    suspend fun getTodaysPrompts(): List<Prompt> = promptStore.getTodaysPrompts()

    fun insertPrompt(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            promptStore.insertPrompt(Prompt(content = content))
        }
    }
}