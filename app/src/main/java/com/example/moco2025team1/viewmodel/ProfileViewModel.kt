package com.example.moco2025team1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.stores.ProfileStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel
private constructor(
    app: Application,
    private val currentUser: StateFlow<User?>
) : AndroidViewModel(app) {

    private val store = ProfileStore(AppDatabase.getInstance(app).userDao())

    val user: StateFlow<User?> =
        currentUser.flatMapLatest { u ->
            if (u == null) flowOf(null)
            else store.getUserWithFriendsFlow(u.id).map { it.user }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val friends: StateFlow<List<User>> =
        currentUser.flatMapLatest { u ->
            if (u == null) flowOf(emptyList())
            else store.getFriendsFlow(u.id)
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

    fun onNameInputChange(newText: String) { _nameInput.value = newText }

    fun saveName() {
        val u = currentUser.value ?: return
        if (canSaveName.value) {
            viewModelScope.launch(Dispatchers.IO) {
                store.updateUserName(u.id, _nameInput.value)
            }
        }
    }

    fun updateUserName(newName: String) {
        val id = currentUser.value?.id ?: return
        viewModelScope.launch(Dispatchers.IO) {
            store.updateUserName(id, newName)
        }
    }

    fun addFriend(friendId: Long) {
        val id = currentUser.value?.id ?: return
        viewModelScope.launch(Dispatchers.IO) {
            store.addFriend(id, friendId)
        }
    }

    fun removeFriend(friendId: Long) {
        val id = currentUser.value?.id ?: return
        viewModelScope.launch(Dispatchers.IO) {
            store.removeFriend(id, friendId)
        }
    }

    // own factory, because viewmodel can't instantiate an object with the parameters we need
    companion object {
        fun factory(
            app: Application,
            session: SessionViewModel
        ) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                ProfileViewModel(app, session.currentUser) as T
        }
    }
}
