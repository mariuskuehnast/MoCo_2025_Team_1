package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.EmojiEmotions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.ui.composables.ContactPicker
import com.example.moco2025team1.viewmodel.LoginViewModel
import com.example.moco2025team1.viewmodel.SessionViewModel
import com.example.moco2025team1.HomeRoute
import com.example.moco2025team1.LoginRoute
import com.example.moco2025team1.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    sessionViewModel: SessionViewModel,
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel(),
    appTitle: String = "JournalBuddy"
) {
    val allUsers by loginViewModel.allUsers.collectAsState()
    val scope     = rememberCoroutineScope()
    val sheet     = rememberBottomSheetScaffoldState()
    var showSignUp by remember { mutableStateOf(false) }

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
                .offset(y = (-50).dp)
                .padding(inner)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(180.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = appTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(32.dp))

            val buttonShape = RoundedCornerShape(12.dp)
            Button(
                onClick = { scope.launch { sheet.bottomSheetState.expand() } },
                shape = buttonShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 6.dp)
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { showSignUp = true },
                shape = buttonShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 6.dp)
            ) {
                Text("Sign Up")
            }


            if (showSignUp) {
                var name by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = { showSignUp = false },
                    confirmButton = {
                        TextButton(
                            enabled = name.isNotBlank(),
                            onClick = {
                                scope.launch {
                                    val newUser = loginViewModel.createUser(name.trim())
                                    completeLogin(newUser)
                                }
                                showSignUp = false
                            }
                        ) { Text("Create") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showSignUp = false }) { Text("Cancel") }
                    },
                    title = { Text("Sign Up") },
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
    }
}