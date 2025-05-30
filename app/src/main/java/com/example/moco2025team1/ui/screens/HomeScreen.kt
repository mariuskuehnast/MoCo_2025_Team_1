package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.moco2025team1.model.entities.Contact
import com.example.moco2025team1.ui.composables.ContactCard
import com.example.moco2025team1.ui.composables.OurScaffold


@Composable
fun HomeScreen(contacts: List<Contact>, onContactClick: (Int) -> Unit = {}) {
    LazyColumn {
        itemsIndexed(contacts) { index, contact ->
            ContactCard(contact.username, callClick = { onContactClick(index) })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        listOf(
            Contact("Hans", "Hansen"),
            Contact("Peter", "Petersen"),
        )
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenScaffoldPreview() {
    OurScaffold() {
        HomeScreen(
            listOf(
                Contact("Hans", "Hansen"),
                Contact("Peter", "Petersen"),
            )
        )
    }
}