package com.example.moco2025team1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.stores.SessionStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SessionViewModel(app: Application) : AndroidViewModel(app) {
    private val sessionStore = SessionStore(app.applicationContext)
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun login(user: User) {
        _currentUser.value = user
        viewModelScope.launch(Dispatchers.IO) {
            sessionStore.login(user.id)
        }
    }

    fun logout() {
        _currentUser.value = null
        viewModelScope.launch(Dispatchers.IO) {
            sessionStore.logout()
        }
    }
}

