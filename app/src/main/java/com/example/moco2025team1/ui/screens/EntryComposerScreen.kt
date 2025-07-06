package com.example.moco2025team1.ui.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil3.compose.rememberAsyncImagePainter
import com.example.moco2025team1.model.entities.Prompt
import com.example.moco2025team1.ui.composables.TextInput
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun EntryComposer(
    prompt: Prompt?,
    onBack: () -> Unit,
    onConfirm: () -> Unit,
    context: Context = LocalContext.current
) {
    val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val photoFile = remember {
        File(
            context.cacheDir,
            "camera_photo_${System.currentTimeMillis()}.jpg"
        )
    }

    val photoUri = remember(photoFile) {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            photoFile
        )
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri = photoUri
            }
        }

    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(modifier = Modifier.align(Alignment.CenterStart), onClick = { onBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Close")
            }
            IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = { onConfirm() }) {
                Icon(Icons.AutoMirrored.Filled.Send, "Send")
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    LocalDateTime.now().format(dateFormatter),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Text(
                    prompt?.content ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
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
            imageUri?.let { uri ->
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .weight(2f)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = uri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Button(
                onClick = {
                    launcher.launch(photoUri)
                },
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(20.dp),
                modifier = Modifier.weight(if (imageUri != null) 1f else 2f)
            ) {
                if (imageUri == null) {
                    Text("Add an Image")
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Icon(Icons.Filled.PhotoCamera, "Camera", Modifier.size(18.dp))
            }
        }
        HorizontalDivider()
        TextInput()
    }
}

@Preview(showBackground = true)
@Composable
fun EntryComposerPreview() {
    EntryComposer(Prompt(content = "Test Prompt"), onBack = {}, onConfirm = {})
}
