package com.example.moco2025team1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.stores.ProfileStore
import com.example.moco2025team1.model.stores.SessionStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModel(
    app: Application
) : AndroidViewModel(app) {
    private val sessionStore = SessionStore(app.applicationContext)

    private val store = ProfileStore(AppDatabase.getInstance(app).userDao())

    val user: StateFlow<User?> =
        sessionStore.userId.flatMapLatest { userId ->
            if (userId == null) flowOf(null)
            else store.getUserWithFriendsFlow(userId).map { it.user }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val friends: StateFlow<List<User>> =
        sessionStore.userId.flatMapLatest { userId ->
            if (userId == null) flowOf(emptyList())
            else store.getFriendsFlow(userId)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val allUsers: StateFlow<List<User>> =
        store.getAllUsersFlow()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _nameInput = MutableStateFlow("")
    val nameInput: StateFlow<String> = _nameInput.asStateFlow()

    val canSaveName: StateFlow<Boolean> =
        nameInput.map { it.isNotBlank() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    init {
        viewModelScope.launch {
            user.filterNotNull().collect { _nameInput.value = it.userName }
        }
    }

    fun onNameInputChange(newText: String) {
        _nameInput.value = newText
    }

    fun saveName() {
        if (canSaveName.value) {
            viewModelScope.launch(Dispatchers.IO) {
                val userId = sessionStore.userId.first()
                if (userId != null) {
                    store.updateUserName(userId, _nameInput.value)
                }
            }
        }
    }

    fun updateUserName(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = sessionStore.userId.first()
            if (userId != null) {
                store.updateUserName(userId, newName)
            }
        }
    }

    fun addFriend(friendId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = sessionStore.userId.first()
            if (userId != null) {
                store.addFriend(userId, friendId)
            }
        }
    }

    fun removeFriend(friendId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = sessionStore.userId.first()
            if (userId != null) {
                store.removeFriend(userId, friendId)
            }
        }
    }
}
