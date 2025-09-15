package com.example.moco2025team1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.stores.ProfileStore
import kotlinx.coroutines.flow.*

class HomeViewModel private constructor(
    app: Application,
    private val currentUser: StateFlow<User?>
) : AndroidViewModel(app) {

    private val store = ProfileStore(AppDatabase.getInstance(app).userDao())

    val friends: StateFlow<List<User>> =
        currentUser
            .flatMapLatest { u ->
                if (u == null) flowOf(emptyList())
                else store.getFriendsFlow(u.id)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    companion object {
        fun factory(app: Application, session: SessionViewModel) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    HomeViewModel(app, session.currentUser) as T
            }
    }
}
