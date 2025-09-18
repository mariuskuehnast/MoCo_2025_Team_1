package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun EmojiRadialMenu(
    emojis: List<String> = listOf("😊", "😐", "😢", "😡", "🤩", "🤔"),
    onEmojiSelected: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    Popup(alignment = Alignment.TopEnd) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            emojis.forEachIndexed { index, emoji ->
                val angle = (360f / emojis.size) * index
                val radius = 200f

                val offsetX =
                    radius * cos(Math.toRadians(angle.toDouble())).toFloat()
                val offsetY =
                    radius * sin(Math.toRadians(angle.toDouble())).toFloat()

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .graphicsLayer {
                            translationX = offsetX
                            translationY = offsetY
                        }
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            scope.launch {
                                onEmojiSelected(emoji)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(emoji, fontSize = 26.sp)
                }
            }
        }
    }

}