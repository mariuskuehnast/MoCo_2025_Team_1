package com.example.moco2025team1.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.moco2025team1.ui.screens.PromptSelectionScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryModal(onDismissRequest: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var modalStage by remember { mutableStateOf(NewEntryModalStage.PromptSelection) }

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismissRequest) {
        when (modalStage) {
            NewEntryModalStage.PromptSelection -> PromptSelectionScreen(onPromptSelection = {
                modalStage = NewEntryModalStage.EntryComposer
            })

            NewEntryModalStage.EntryComposer -> EntryComposer(
                onClose = { onDismissRequest() },
                onConfirm = {
                    modalStage =
                        NewEntryModalStage.ContactSelection
                })

            NewEntryModalStage.ContactSelection -> TODO()
        }
    }
}

enum class NewEntryModalStage {
    PromptSelection,
    EntryComposer,
    ContactSelection
}