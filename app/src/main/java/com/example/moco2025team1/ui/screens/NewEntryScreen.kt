package com.example.moco2025team1.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.example.moco2025team1.ui.composables.EntryComposer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryScreen(onDismissRequest: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismissRequest) {
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
        EntryComposer(onClose = { onDismissRequest() })
    }
}
