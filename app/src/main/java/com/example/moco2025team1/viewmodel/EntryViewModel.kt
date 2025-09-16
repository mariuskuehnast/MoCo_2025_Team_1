package com.example.moco2025team1.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.Entry
import com.example.moco2025team1.model.stores.EntryStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EntryViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app.applicationContext)
    private val entryDao = db.entryDao()
    private val entryStore = EntryStore(entryDao)

    fun insertEntry(content: String, imageUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            entryStore.insertEntry(Entry(content = content, imageUri = imageUri?.toString()))
        }
    }

    fun getEntryById(id: Long): Flow<Entry?> {
        return entryStore.getEntryById(id)
    }
}