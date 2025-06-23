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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app)
    private val profileStore = ProfileStore(db.userDao())
    private val _userWithFriends = MutableStateFlow<UserWithFriends?>(null)
    val userWithFriends: StateFlow<UserWithFriends?> = _userWithFriends

    // until we have real auth, we'll just load userId = 1
    private val currentUserId: Long = 1L

    init {
        viewModelScope.launch(Dispatchers.IO) {
            profileStore.init()
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
