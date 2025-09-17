package com.example.moco2025team1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.stores.EntryStore
import com.example.moco2025team1.model.stores.ProfileStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel private constructor(
    app: Application,
    currentUser: StateFlow<User?>
) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app)
    private val store = ProfileStore(db.userDao())
    private val entryStore = EntryStore(db.entryDao())

    val friends: StateFlow<List<User>> =
        currentUser
            .flatMapLatest { user ->
                if (user == null) flowOf(emptyList())
                else store.getFriendsFlow(user.id)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val pendingBySender: StateFlow<Map<Long, Long>> =
        currentUser
            .flatMapLatest { user ->
                if (user == null) flowOf(emptyList())
                else entryStore.getPendingEntriesFromFriends(user.id)
            }
            .map { pendingEntries -> pendingEntries.associate { it.senderId to it.latestEntryId } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyMap())

    companion object {
        fun factory(app: Application, session: SessionViewModel) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    HomeViewModel(app, session.currentUser) as T
            }
    }
}
