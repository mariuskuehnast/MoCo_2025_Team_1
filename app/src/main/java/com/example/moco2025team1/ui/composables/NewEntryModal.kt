package com.example.moco2025team1.ui.composables

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moco2025team1.model.entities.Prompt
import com.example.moco2025team1.ui.screens.PromptSelectionScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryModal(onDismissRequest: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var prompt by remember { mutableStateOf<Prompt?>(null) }
    val navController = rememberNavController()

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismissRequest) {
        NavHost(navController, startDestination = PromptSelection) {
            composable<PromptSelection> {
                PromptSelectionScreen(onPromptSelection = {
                    navController.navigate(EntryComposer)
                    prompt = it
                })
            }
            composable<EntryComposer>(enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(200, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            }, exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(200, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }) {
                EntryComposer(
                    prompt = prompt,
                    onBack = {
                        navController.navigate(PromptSelection)
                    },
                    onConfirm = {
                        navController.navigate(
                            ContactSelection
                        )
                    })
            }
            composable<ContactSelection> {

            }
        }
    }
}

sealed class NewEntryModalStage

@Serializable
data object PromptSelection : NewEntryModalStage()

@Serializable
data object EntryComposer : NewEntryModalStage()

@Serializable
data object ContactSelection : NewEntryModalStage()
