package com.example.moco2025team1.ui.screens

import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.moco2025team1.model.entities.Entry
import com.example.moco2025team1.ui.composables.Avatar
import com.example.moco2025team1.viewmodel.EntryViewModel
import com.example.moco2025team1.viewmodel.SessionViewModel

@Composable
fun EntryViewerScreen(
    entryId: Long,
    sessionViewModel: SessionViewModel,
    entryViewModel: EntryViewModel = viewModel(),
    onBack: () -> Unit = {}
) {
    val entry by entryViewModel.getEntryById(entryId).collectAsState(null)
    val currentUser by sessionViewModel.currentUser.collectAsState(null)

    if (entry == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LaunchedEffect(entryId) {
            currentUser?.let { entryViewModel.markViewedOnce(entryId, it.id) }
        }
        EntryViewerScreen(entry = entry!!, onBack = onBack)
    }
}

fun shortenContent(content: String): String {
    val words = content.split(Regex("\\s+"))
    var newContent = words.take(20).joinToString(" ")
    if (words.size > 20) {
        newContent += "..."
    }
    return newContent
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryViewerScreen(entry: Entry, onBack: () -> Unit = {}) {
    val bottomSheetState = rememberModalBottomSheetState()
    var showMore by remember { mutableStateOf(false) }

    val shortenedContent = shortenContent(entry.content)
    val context = LocalContext.current
    val dateText = remember(entry.createdAt) {
        val dateFormatted = DateFormat.getMediumDateFormat(context).format(entry.createdAt)
        val timeFormatted = DateFormat.getTimeFormat(context).format(entry.createdAt)
        "$dateFormatted • $timeFormatted"
    }

    Box {
        Image(
            painter = rememberAsyncImagePainter(entry.imageUri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
            Avatar((entry.senderUserName?.firstOrNull() ?: '?'))

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    entry.senderUserName ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                val moodText = entry.mood?.takeIf { it.isNotBlank() } ?: "—"
                Text(
                    "Current mood: $moodText · $dateText",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color(0xBB000000), Color(0xFF000000)
                        )
                    )
                )
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp, alignment = Alignment.Bottom)
        ) {
            Text(
                entry.prompt ?: "",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(shortenedContent, color = Color.White)
            if (shortenedContent != entry.content) {
                Text(
                    "Show more",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { showMore = true }
                )
            }
        }
    }
    if (showMore) {
        ModalBottomSheet(onDismissRequest = { showMore = false }, sheetState = bottomSheetState) {
            Text(entry.content, modifier = Modifier.padding(20.dp, 0.dp))
        }
    }
}

@Preview
@Composable
fun EntryViewerScreenPreview() {
    EntryViewerScreen(entry = Entry(content = "Hello World!"))
}