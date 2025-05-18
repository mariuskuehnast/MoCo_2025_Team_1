package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moco2025team1.ui.composables.Mood
import com.example.moco2025team1.ui.composables.MoodPicker
import com.example.moco2025team1.ui.composables.TextInput

@Composable
fun NewEntryScreen() {
    var mood by remember { mutableStateOf<Mood?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
            Row(
                Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0xFFDDDDDD))
                    .padding(7.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "What made you smile today?",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    "Send",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f).padding(0.dp, 5.dp),
            verticalArrangement = if (mood === null) Arrangement.Center else Arrangement.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    "How do you feel right now?",
                    style = MaterialTheme.typography.titleMedium
                )
                MoodPicker(onSelect = { mood = it })
            }
        }

        if (mood != null) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            }
            TextInput()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewEntryScreenPreview() {
    NewEntryScreen()
}
