package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moco2025team1.ui.composables.PromptCard

//should be replaced by a database eventually
val DAILY_PROMPTS = listOf(
    "What made you smile today?",
    "Describe a challenge you overcame.",
    "What are you grateful for right now?",
    "Write about a moment you felt calm.",
    "What did you learn today?",
    "Who helped you today and how?",
    "One goal you have for tomorrow is…",
    "How are you feeling in this moment?"
)

@Composable
fun PromptSelectionScreen(navController: NavController) {

    val todaysPrompts = remember { DAILY_PROMPTS.shuffled().take(3) }

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

        todaysPrompts.forEachIndexed { index, prompt ->
            PromptCard(
                prompt       = prompt,
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