package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class Mood {
    HAPPY,
    CALM,
    ANGRY,
    WORRIED,
    MISERABLE
}

@Composable
fun MoodPicker(onSelect: (mood: Mood) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        EmojiButton("\uD83D\uDE0A") { onSelect(Mood.HAPPY) }
        EmojiButton("\uD83D\uDE0C") { onSelect(Mood.CALM) }
        EmojiButton("\uD83D\uDE20") { onSelect(Mood.ANGRY) }
        EmojiButton("\uD83D\uDE30") { onSelect(Mood.WORRIED) }
        EmojiButton("\uD83D\uDE2D") { onSelect(Mood.MISERABLE) }
    }
}

@Preview
@Composable
fun MoodPickerPreview() {
    MoodPicker {}
}