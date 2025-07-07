package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moco2025team1.model.entities.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPicker(
    title: String,
    candidates: List<User>,
    initiallySelected: Set<Long> = emptySet(),
    multipleSelection: Boolean = true,
    onConfirm: (List<User>) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filtered = remember(candidates, searchQuery) {
        if (searchQuery.isBlank()) candidates
        else candidates.filter {
            it.userName.contains(searchQuery, ignoreCase = true)
        }
    }
    val selectedIds = remember { initiallySelected.toMutableStateList() }

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        ContactSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filtered) { user ->
                val checked = user.id in selectedIds
                ContactRow(
                    user = user,
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        if (multipleSelection) {
                            if (isChecked) selectedIds.add(user.id)
                            else selectedIds.remove(user.id)
                        } else {
                            selectedIds.clear()
                            if (isChecked) selectedIds.add(user.id)
                        }
                    }
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss) { Text("Cancel") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                val picked = candidates.filter { it.id in selectedIds }
                onConfirm(picked)
            }) {
                Text("Confirm")
            }
        }
    }
}
