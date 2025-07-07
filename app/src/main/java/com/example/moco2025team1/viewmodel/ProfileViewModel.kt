package com.example.moco2025team1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.entities.UserWithFriends
import com.example.moco2025team1.model.stores.ProfileStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app)
    private val profileStore = ProfileStore(db.userDao())
    private val _userWithFriends = MutableStateFlow<UserWithFriends?>(null)
    val userWithFriends: StateFlow<UserWithFriends?> = _userWithFriends

    // until we have real auth, we'll just load userId = 1
    private val currentUserId: Long = 1L

    private val _nameInput = MutableStateFlow("")
    val nameInput: StateFlow<String> = _nameInput.asStateFlow()

    val canSaveName: StateFlow<Boolean> =
        nameInput.map { it.isNotBlank() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val user: StateFlow<User?> =
        profileStore
            .getUserWithFriendsFlow(currentUserId)
            .map { it.user }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            profileStore.init()
        }

        viewModelScope.launch {
            user
                .filterNotNull()
                .collect { user ->
                    _nameInput.value = user.userName
                }
        }
    }

    fun onNameInputChange(newText: String) {
        _nameInput.value = newText
    }

    fun saveName() {
        if (canSaveName.value) {
            updateUserName(_nameInput.value)
        }
    }

    val friends: StateFlow<List<User>> =
        profileStore
            .getFriendsFlow(currentUserId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    val allUsers: StateFlow<List<User>> =
        profileStore
            .getAllUsersFlow()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun updateUserName(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            profileStore.updateUserName(currentUserId, newName)
        }
    }

    fun addFriend(friendId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            profileStore.addFriend(currentUserId, friendId)
        }
    }

    fun removeFriend(friendId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            profileStore.removeFriend(currentUserId, friendId)
        }
    }
}
