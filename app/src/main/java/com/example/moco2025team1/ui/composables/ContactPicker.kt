package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    showCheckboxes: Boolean = true,
    currentUserIdToHide: Long? = null,
    onConfirm: (List<User>) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val baseList = remember(candidates, currentUserIdToHide) {
        if (currentUserIdToHide != null) candidates.filter { it.id != currentUserIdToHide } else candidates
    }

    val filtered = remember(baseList, searchQuery) {
        if (searchQuery.isBlank()) baseList
        else baseList.filter { it.userName.contains(searchQuery, ignoreCase = true) }
    }

    val selectedIds = remember(initiallySelected) { initiallySelected.toMutableStateList() }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        ContactSearchBar(query = searchQuery, onQueryChange = { searchQuery = it })
        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            itemsIndexed(
                items = filtered,
                key = { _, user -> user.id }
            ) { _, user ->
                val checked = user.id in selectedIds
                ContactRow(
                    user = user,
                    checked = checked,
                    showCheckbox = showCheckboxes,
                    onCheckedChange = { isChecked ->
                        if (!showCheckboxes) return@ContactRow
                        if (multipleSelection) {
                            if (isChecked) selectedIds.add(user.id) else selectedIds.remove(user.id)
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
            if (showCheckboxes) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    val picked = baseList.filter { it.id in selectedIds }
                    onConfirm(picked)
                }) { Text("Confirm") }
            } else {
                TextButton(onClick = onDismiss) { Text("Close") }
            }
        }
    }
}