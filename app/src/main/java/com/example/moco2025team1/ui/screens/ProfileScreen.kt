package com.example.moco2025team1.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moco2025team1.ui.composables.ContactPicker
import com.example.moco2025team1.viewmodel.ProfileViewModel
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.moco2025team1.LoginRoute
import com.example.moco2025team1.viewmodel.SessionViewModel
import kotlinx.coroutines.launch

private enum class PickerMode { VIEW, ADD, REMOVE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    sessionViewModel: SessionViewModel,
    navController: NavHostController
) {
    val viewModel: ProfileViewModel = viewModel()

    val user     by viewModel.user.collectAsState()
    val friends  by viewModel.friends.collectAsState()
    val allUsers by viewModel.allUsers.collectAsState()

    val nameInput  by viewModel.nameInput.collectAsState()
    val canSave    by viewModel.canSaveName.collectAsState()

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var pickerMode by remember { mutableStateOf<PickerMode?>(null) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        snackbarHost = { SnackbarHost(scaffoldState.snackbarHostState) },
        sheetContent = {
            when (pickerMode) {
                PickerMode.VIEW -> ContactPicker(
                    title      = "Your Friends",
                    candidates = friends,
                    onConfirm  = {  },
                    onDismiss  = {
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                            pickerMode = null
                        }
                    }
                )

                PickerMode.ADD -> ContactPicker(
                    title      = "Add Friend",
                    candidates = allUsers.filter { it.id !in friends.map { f -> f.id } },
                    onConfirm  = { picked ->
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                            pickerMode = null
                            picked.forEach { viewModel.addFriend(it.id) }
                        }
                    },
                    onDismiss  = {
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                            pickerMode = null
                        }
                    }
                )

                PickerMode.REMOVE -> ContactPicker(
                    title      = "Remove Friend",
                    candidates = friends,
                    onConfirm  = { picked ->
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                            pickerMode = null
                            picked.forEach { viewModel.removeFriend(it.id) }
                        }
                    },
                    onDismiss  = {
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                            pickerMode = null
                        }
                    }
                )

                else -> Spacer(Modifier.height(1.dp))
            }
        }
    ) { innerPadding ->
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector        = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier           = Modifier.size(96.dp)
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value         = nameInput,
                onValueChange = viewModel::onNameInputChange,
                label         = { Text("Username") },
                modifier      = Modifier.fillMaxWidth()
            )

            Button(
                enabled = canSave,
                onClick = {
                    scope.launch {
                        viewModel.saveName()
                        scaffoldState.snackbarHostState
                            .showSnackbar("Name successfully changed")
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            ) { Text("Save Name") }
            Spacer(Modifier.height(24.dp))

            Button(onClick = {
                pickerMode = PickerMode.VIEW
                scope.launch { scaffoldState.bottomSheetState.expand() }
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Friendlist")
            }
            Spacer(Modifier.height(8.dp))

            Button(onClick = {
                pickerMode = PickerMode.ADD
                scope.launch { scaffoldState.bottomSheetState.expand() }
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Friend")
            }
            Spacer(Modifier.height(8.dp))

            Button(onClick = {
                pickerMode = PickerMode.REMOVE
                scope.launch { scaffoldState.bottomSheetState.expand() }
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Remove Friend")
            }
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    sessionViewModel.logout()
                    navController.navigate(LoginRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },

                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}
