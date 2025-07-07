// ui/screens/LoginScreen.kt
package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.ui.composables.ContactPicker
import com.example.moco2025team1.viewmodel.LoginViewModel
import com.example.moco2025team1.viewmodel.SessionViewModel
import com.example.moco2025team1.HomeRoute
import com.example.moco2025team1.LoginRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    sessionViewModel: SessionViewModel,
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val allUsers by loginViewModel.allUsers.collectAsState()
    val scope     = rememberCoroutineScope()
    val sheet     = rememberBottomSheetScaffoldState()
    var showRegister by remember { mutableStateOf(false) }

    fun completeLogin(user: User) {
        sessionViewModel.login(user)
        navController.navigate(HomeRoute) {
            popUpTo(LoginRoute) { inclusive = true }
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheet,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            ContactPicker(
                title = "Choose user",
                candidates = allUsers,
                multipleSelection = false,
                onConfirm = { picked ->
                    scope.launch { sheet.bottomSheetState.partialExpand() }
                    picked.firstOrNull()?.let(::completeLogin)
                },
                onDismiss = { scope.launch { sheet.bottomSheetState.partialExpand() } }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(64.dp))

            Button(
                onClick = { scope.launch { sheet.bottomSheetState.expand() } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) { Text("Login") }

            OutlinedButton(
                onClick = { showRegister = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) { Text("Register") }
        }
    }

    if (showRegister) {
        var name by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showRegister = false },
            confirmButton = {
                TextButton(
                    enabled = name.isNotBlank(),
                    onClick = {
                        scope.launch {
                            val newUser = loginViewModel.createUser(name.trim())
                            completeLogin(newUser)
                        }
                        showRegister = false
                    }
                ) { Text("Create") }
            },
            dismissButton = {
                TextButton(onClick = { showRegister = false }) { Text("Cancel") }
            },
            title = { Text("Create new user") },
            text = {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true
                )
            }
        )
    }
}
