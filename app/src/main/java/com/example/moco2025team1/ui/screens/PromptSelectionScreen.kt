package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moco2025team1.ui.composables.PromptCard
import com.example.moco2025team1.viewmodel.PromptViewModel
import java.time.LocalDate


@Composable
fun PromptSelectionScreen(
    navController: NavController,
    viewModel: PromptViewModel = viewModel()
) {
    val dailyPrompts by viewModel.dailyPrompts.collectAsState(initial = emptyList())
    //viewModel.refreshPrompts(LocalDate.now().plusDays(1))

    var selectedIndex by remember { mutableStateOf<Int?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Choose Your Daily Prompt",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        dailyPrompts.forEachIndexed { index, prompt ->
            PromptCard(
                prompt       = prompt.content,
                isSelected   = (selectedIndex == index),
                onCardClick  = {
                    if (selectedIndex == index) {
                        //placeholder for journalentry route
                        navController.popBackStack()
                    } else {
                        selectedIndex = index
                    }
                }
            )

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}