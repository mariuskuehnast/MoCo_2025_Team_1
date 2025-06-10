package com.example.moco2025team1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moco2025team1.ui.composables.TextInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryScreen(onDismissRequest: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismissRequest) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//        Row(
//            Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(5.dp)
//        ) {
//            IconButton(onClick = {}) {
//                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
//            }
//            Row(
//                Modifier
//                    .clip(RoundedCornerShape(5.dp))
//                    .background(Color(0xFFDDDDDD))
//                    .padding(7.dp)
//                    .weight(1f),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    "What made you smile today?",
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Medium
//                )
//            }
//            IconButton(onClick = {}) {
//                Icon(
//                    Icons.AutoMirrored.Filled.Send,
//                    "Send",
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(20.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("😭")
                    }
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(20.dp),
                        modifier = Modifier.weight(2f)
                    ) {
                        Text("Add an Image")
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(Icons.Filled.PhotoCamera, "Camera", Modifier.size(18.dp))
                    }
                }
                HorizontalDivider()
                TextInput()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewEntryScreenPreview() {
    var isSheetOpen by remember { mutableStateOf(true) }

    if (isSheetOpen) {
        NewEntryScreen(onDismissRequest = { isSheetOpen = false })
    }
}
