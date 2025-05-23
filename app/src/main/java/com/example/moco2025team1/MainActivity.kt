package com.example.moco2025team1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moco2025team1.model.Contact
import com.example.moco2025team1.ui.composables.ContactCard
import com.example.moco2025team1.ui.composables.OurScaffold
import com.example.moco2025team1.ui.screens.HomeScreen
import com.example.moco2025team1.ui.screens.NewEntryScreen
import com.example.moco2025team1.ui.screens.ProfileScreen
import com.example.moco2025team1.ui.theme.MOCO2025Team1Theme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MOCO2025Team1Theme {
                OurScaffold(
                    onNavigate = { route ->
                        navController.navigate(route = route)
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeRoute
                    ) {
                        composable<HomeRoute> {
                            val contacts = listOf(
                                Contact("Hans"),
                                Contact("Peter"),
                            )
                            HomeScreen(
                                contacts,
                                onContactClick = {
//                                    navController.navigate(HomeRoute(contacts[it].username))
                                }
                            )
                        }
                        composable<ProfileRoute> {
//                            backStack ->
//                            val name = backStack.toRoute<HomeRoute>().name
                            ProfileScreen()
                        }
                        composable<NewEntryRoute> {
                            NewEntryScreen()
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        ContactCard("Max") {}
        ContactCard("Max") {}
        ContactCard("Max") {}
        ContactCard("Max") {}
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MOCO2025Team1Theme {
        Greeting("Android")
    }
}

sealed class Route

@Serializable
data object HomeRoute : Route()

@Serializable
data object NewEntryRoute : Route()

@Serializable
data object ProfileRoute : Route()

