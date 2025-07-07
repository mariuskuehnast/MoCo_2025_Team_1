package com.example.moco2025team1.ui.composables

import android.net.Uri
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moco2025team1.model.entities.Prompt
import com.example.moco2025team1.ui.screens.EntryComposer
import com.example.moco2025team1.ui.screens.PromptSelectionScreen
import com.example.moco2025team1.viewmodel.EntryViewModel
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryModal(onDismissRequest: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var prompt by remember { mutableStateOf<Prompt?>(null) }
    val navController = rememberNavController()

    var content by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val entryViewModel = viewModel<EntryViewModel>()

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismissRequest) {
        NavHost(navController, startDestination = PromptSelectionRoute) {
            composable<PromptSelectionRoute> {
                PromptSelectionScreen(onPromptSelection = {
                    navController.navigate(EntryComposerRoute)
                    prompt = it
                })
            }
            composable<EntryComposerRoute>(enterTransition = {
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
                CameraPermissionWrapper {
                    EntryComposer(
                        prompt = prompt,
                        onBack = {
                            navController.navigate(PromptSelectionRoute)
                        },
                        onConfirm = { newContent, newImageUri ->
                            content = newContent
                            imageUri = newImageUri

                            entryViewModel.insertEntry(content, imageUri)

                            navController.navigate(
                                ContactSelectionRoute
                            )
                        })
                }
            }
            composable<ContactSelectionRoute> {

            }
        }
    }
}

sealed class NewEntryModalRoute

@Serializable
data object PromptSelectionRoute : NewEntryModalRoute()

@Serializable
data object EntryComposerRoute : NewEntryModalRoute()

@Serializable
data object ContactSelectionRoute : NewEntryModalRoute()
