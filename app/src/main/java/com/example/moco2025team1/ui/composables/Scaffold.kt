package com.example.moco2025team1.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.moco2025team1.Route
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OurScaffold(
    onNavigate: (Route) -> Unit = {},
    showBottomBar: Boolean = true,
    title: String? = null,
    content: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            if (title != null) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp
                        )
                    })
            }
        },
        bottomBar = { if (showBottomBar) OurNavigationBar(onNavigate) }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) { content() }
    }
}
