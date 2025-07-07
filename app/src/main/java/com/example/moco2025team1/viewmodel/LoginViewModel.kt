// app/viewmodel/LoginViewModel.kt
package com.example.moco2025team1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.stores.ProfileStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val store = ProfileStore(AppDatabase.getInstance(app).userDao())

    val allUsers: StateFlow<List<User>> =
        store.getAllUsersFlow()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    suspend fun createUser(name: String): User =
        store.createUser(name)

    init {
        viewModelScope.launch(Dispatchers.IO) { store.init() }
    }
}
