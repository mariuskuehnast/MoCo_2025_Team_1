package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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
    entryViewModel: EntryViewModel = viewModel()
) {
    val entry by entryViewModel.getEntryById(entryId).collectAsState(null)

    if (entry == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LaunchedEffect(entryId) {
            entryViewModel.markViewedOnce(entryId)
        }
        EntryViewerScreen(entry = entry!!)
    }
}


@Composable
fun EntryViewerScreen(entry: Entry) {
    Box {
        Image(
            painter = rememberAsyncImagePainter(entry.imageUri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.TopStart)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .padding(30.dp, 10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Avatar((entry.senderUserName?.firstOrNull() ?: '?'))
            Text(entry.senderUserName ?: "", style = MaterialTheme.typography.titleMedium)
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
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(entry.content, color = Color.White)
            Text("Show more", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
fun EntryViewerScreenPreview() {
    EntryViewerScreen(entry = Entry(content = "Hello World!"))
}