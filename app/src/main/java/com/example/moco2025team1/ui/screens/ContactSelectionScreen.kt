package com.example.moco2025team1.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moco2025team1.model.entities.Entry
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.ui.composables.ContactRow
import com.example.moco2025team1.ui.composables.ContactSearchBar
import com.example.moco2025team1.viewmodel.ProfileViewModel

@Composable
fun ContactSelectionScreen(
    viewModel: ProfileViewModel = viewModel(),
    entry: Entry? = null,
    onSendSelected: (Entry, List<User>) -> Unit = { _, _ -> /* TODO */ }
) {
    val friendList by viewModel.friends.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    val selectedUsers = remember { mutableStateListOf<User>() }

    val filteredUsers = remember(searchQuery, friendList) {
        if (searchQuery.isBlank()) friendList
        else friendList.filter {
            it.userName.contains(searchQuery, ignoreCase = true)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {
            ContactSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            filteredUsers.forEach { user ->
                val isChecked = user in selectedUsers
                ContactRow(
                    user = user,
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        if (checked) selectedUsers.add(user)
                        else selectedUsers.remove(user)
                    }
                )
            }
        }

        Button(
            onClick = {
                entry?.let {
                    onSendSelected(it, selectedUsers.toList())
                }
            },
            enabled = selectedUsers.isNotEmpty(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Send")
        }
    }
}