package com.example.moco2025team1.ui.screens

import android.app.Application import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.moco2025team1.LoginRoute
import com.example.moco2025team1.ui.composables.ContactCard
import com.example.moco2025team1.viewmodel.HomeViewModel
import com.example.moco2025team1.viewmodel.SessionViewModel

@Composable
fun HomeScreen(
    sessionViewModel: SessionViewModel,
    navController: NavHostController,
    onOpenEntry: (Long) -> Unit = {}
) {
    val app = LocalContext.current.applicationContext as Application
    val vm: HomeViewModel = viewModel(factory = HomeViewModel.factory(app, sessionViewModel))
    val friends by vm.friends.collectAsState()
    val pendingBySender by vm.pendingBySender.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.get<String>("login_toast")
            ?.let { message ->
                android.widget.Toast
                    .makeText(context, message, android.widget.Toast.LENGTH_SHORT)
                    .show()
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.remove<String>("login_toast")
            }
    }

    if (friends.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Outlined.SentimentDissatisfied,
                    contentDescription = "No friends",
                    modifier = Modifier.size(96.dp),
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "You have no friends yet",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Try adding new friends!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                )
            }
        }
        return
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(friends) { index, user ->
            val latestId = pendingBySender[user.id]
            val hasPending = latestId != null

            ContactCard(
                userName = user.userName,
                isUnlockAvailable = hasPending
            ) {
                if (hasPending) onOpenEntry(latestId)
            }
        }
    }
}
