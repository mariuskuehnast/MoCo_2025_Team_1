package com.example.moco2025team1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moco2025team1.model.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun login(user: User?) {
        _currentUser.value = user
    }

    fun logout() {
        _currentUser.value = null
    }
}

