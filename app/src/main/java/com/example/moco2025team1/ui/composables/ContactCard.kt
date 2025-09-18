package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Markunread
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*


@Composable
fun ContactCard(
    userName: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    isUnlockAvailable: Boolean = false,
    callClick: () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val roundedCornerShape = RoundedCornerShape(16.dp)

    Surface(
        color = backgroundColor,
        shape = roundedCornerShape,
        tonalElevation = 1.dp,
        modifier = modifier.clip(roundedCornerShape)
    ) {
        ListItem(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .heightIn(min = 72.dp),
            leadingContent = { Avatar(userName.first()) },
            headlineContent = {
                Text(text = userName, style = MaterialTheme.typography.titleMedium)
            },
            supportingContent = {
                val subtitleColor = if (isUnlockAvailable)
                    MaterialTheme.colorScheme.onSurfaceVariant
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                if (!subtitle.isNullOrBlank()) {
                    Text(text = subtitle, color = subtitleColor, style = MaterialTheme.typography.bodySmall)
                }
            },
            trailingContent = {
                IconButton(onClick = callClick, enabled = isUnlockAvailable) {
                    Icon(
                        imageVector = if (isUnlockAvailable) Icons.Filled.Markunread else Icons.Filled.Lock,
                        contentDescription = if (isUnlockAvailable) "View entry" else "No entry",
                        tint = if (isUnlockAvailable) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        )
    }
}