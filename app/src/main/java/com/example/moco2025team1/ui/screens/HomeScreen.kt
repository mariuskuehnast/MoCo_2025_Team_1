package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moco2025team1.model.entities.Contact
import com.example.moco2025team1.ui.composables.OurScaffold
import com.example.moco2025team1.viewmodel.PromptViewModel


@Composable
fun HomeScreen(contacts: List<Contact>, onContactClick: (Int) -> Unit = {}) {
//    LazyColumn {
//        itemsIndexed(contacts) { index, contact ->
//            ContactCard(contact.username, callClick = { onContactClick(index) })
//        }
//    }
    val viewModel = viewModel<PromptViewModel>()
    val firebaseValue by viewModel.firebaseState.observeAsState()

    Column {
        Button(onClick = {
            viewModel.insertPrompt("Hello :)")
        }) {
            Text("Insert into Firebase")
        }

        Text("Current value: $firebaseValue")
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