package com.example.moco2025team1.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.Prompt
import com.example.moco2025team1.model.stores.PromptStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

// you will not only have one single viewmodel, but maybe as many as you have screens
class PromptViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app.applicationContext)
    private val promptDao = db.promptDao()
    private val preferences = app.getSharedPreferences("prompt_preferences", Context.MODE_PRIVATE)
    private val promptStore = PromptStore(promptDao, preferences)

    //only viewmodel should update it
    private val _dailyPrompts = MutableStateFlow<List<Prompt>>(emptyList())
    // public read-only for UI
    val dailyPrompts: StateFlow<List<Prompt>> = _dailyPrompts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            promptStore.init()
            _dailyPrompts.value = promptStore.getDailyPrompts()
        }
    }

    //for testing
    fun refreshPrompts(fakeDate: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            _dailyPrompts.value = promptStore.getDailyPrompts(fakeDate)
        }
    }

    fun refreshPrompts() {
        viewModelScope.launch(Dispatchers.IO) {
            _dailyPrompts.value = promptStore.getDailyPrompts()
        }
    }

    fun insertPrompt(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            promptStore.insertPrompt(Prompt(content = content))
        }
    }

    fun resetPrompts() {
        viewModelScope.launch(Dispatchers.IO) {
            promptDao.clearPrompts()
            promptStore.init()
            _dailyPrompts.value = promptStore.getDailyPrompts()
        }
    }
}

