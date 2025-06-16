package com.example.moco2025team1.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.moco2025team1.model.entities.Prompt
import com.example.moco2025team1.ui.screens.PromptSelectionScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryModal(onDismissRequest: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var modalStage by remember { mutableStateOf(NewEntryModalStage.PromptSelection) }
    var prompt by remember { mutableStateOf<Prompt?>(null) }

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismissRequest) {
        when (modalStage) {
            NewEntryModalStage.PromptSelection -> PromptSelectionScreen(onPromptSelection = {
                modalStage = NewEntryModalStage.EntryComposer
                prompt = it
            })

            NewEntryModalStage.EntryComposer -> EntryComposer(
                prompt = prompt,
                onBack = {
                    modalStage =
                        NewEntryModalStage.PromptSelection
                },
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