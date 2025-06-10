package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextInput() {
    var value by remember { mutableStateOf("") }

    val textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)


    BasicTextField(
        value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxSize(),
//        modifier = Modifier.weight(1f),
//        colors = TextFieldDefaults.colors(
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent
//        )
        textStyle = textStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.padding(12.dp)) {
                if (value.isEmpty()) {
                    Text(
                        "Start typing...",
                        style = textStyle.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    )
                }
                innerTextField()
            }
        }
    )

}

@Preview
@Composable
fun TextInputPreview() {
    TextInput()
}