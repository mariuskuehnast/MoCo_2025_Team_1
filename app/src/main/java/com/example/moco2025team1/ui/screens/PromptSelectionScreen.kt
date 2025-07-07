package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moco2025team1.model.entities.Prompt
import com.example.moco2025team1.ui.composables.PromptCard
import com.example.moco2025team1.viewmodel.PromptViewModel

@Composable
fun PromptSelectionScreen(
    onPromptSelection: (prompt: Prompt) -> Unit,
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
                        onPromptSelection(prompt)
                    } else {
                        selectedIndex = index
                    }
                }
            )

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}