package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ContactCard(
    username: String, modifier: Modifier = Modifier, callClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .padding(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.size(10.dp))
        Avatar(username.first())
        Spacer(Modifier.size(10.dp))
        Column(Modifier.weight(3f)) {
            Text(username)
        }
        IconButton(onClick = callClick) {
            Icon(
                Icons.Filled.Lock, contentDescription = "", tint = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun ContactCardPreview() {
    ContactCard("Hans", modifier = Modifier.fillMaxWidth()) {}
}