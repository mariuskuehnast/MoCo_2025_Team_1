package com.example.moco2025team1.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.automirrored.outlined.NoteAdd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.moco2025team1.HomeRoute
import com.example.moco2025team1.ProfileRoute
import com.example.moco2025team1.PromptSelectionRoute
import com.example.moco2025team1.Route

@Composable
@Preview
fun OurNavigationBar(onNavigate: (route: Route) -> Unit = {}) {
    var currentRoute: Route by remember { mutableStateOf(HomeRoute) }
    val items = listOf(Home, NewEntry, Profile)
    var isSheetOpen by remember { mutableStateOf(false) }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(icon = {
                Icon(
                    if (currentRoute == item.route) item.selectedIcon else item.unselected,
                    contentDescription = item.name
                )
            }, label = { Text(item.name) }, selected = currentRoute == item.route, onClick = {
                if (item.route == PromptSelectionRoute) {
                    isSheetOpen = true
                } else {
                    currentRoute = item.route
                    onNavigate(item.route)
                }
            })
        }
    }

    if (isSheetOpen) {
        NewEntryModal(onDismissRequest = { isSheetOpen = false })
    }
}


sealed class BottomBarItem(
    val name: String,
    val route: Route,
    val selectedIcon: ImageVector,
    val unselected: ImageVector,
)

data object Home :
    BottomBarItem("Home", HomeRoute, Icons.Filled.Home, Icons.Outlined.Home)

data object NewEntry :
    BottomBarItem("New Entry", PromptSelectionRoute, Icons.AutoMirrored.Filled.NoteAdd, Icons.AutoMirrored.Outlined.NoteAdd)

data object Profile :
    BottomBarItem("Profile", ProfileRoute, Icons.Filled.Person, Icons.Outlined.Person)