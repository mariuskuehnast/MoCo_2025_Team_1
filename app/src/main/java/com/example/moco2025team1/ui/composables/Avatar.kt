package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Avatar(capitalLetter: Char, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Gray)
            .size(30.dp)
    ) {
        Text(
            capitalLetter.toString(),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
@Preview
fun AvatarPreview() {
    Avatar('C')
}