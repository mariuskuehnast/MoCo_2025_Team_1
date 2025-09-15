package com.example.moco2025team1.ui.screens

import android.app.Application
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moco2025team1.ui.composables.ContactCard
import com.example.moco2025team1.viewmodel.HomeViewModel
import com.example.moco2025team1.viewmodel.SessionViewModel

@Composable
fun HomeScreen(
    sessionViewModel: SessionViewModel,
    onContactClick: (Int) -> Unit = {}
) {
    val app = LocalContext.current.applicationContext as Application
    val vm: HomeViewModel = viewModel(factory = HomeViewModel.factory(app, sessionViewModel))
    val friends by vm.friends.collectAsState()

    if (friends.isEmpty()) {
        Text("You have no friends yet.")
        return
    }

    LazyColumn {
        itemsIndexed(friends) { index, user ->
            ContactCard(user.userName) { onContactClick(index) }
        }
    }
}
