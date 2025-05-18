package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp

@Composable
fun EmojiButton(emoji: String, onClick: () -> Unit) {
    Text(emoji, fontSize = 32.sp, modifier = Modifier
        .clip(CircleShape)
        .clickable { onClick() })
}