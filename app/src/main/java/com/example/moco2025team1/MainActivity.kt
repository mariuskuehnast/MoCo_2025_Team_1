package com.example.moco2025team1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moco2025team1.model.entities.Contact
import com.example.moco2025team1.ui.composables.ContactCard
import com.example.moco2025team1.ui.composables.OurScaffold
import com.example.moco2025team1.ui.screens.HomeScreen
import com.example.moco2025team1.ui.screens.ProfileScreen
import com.example.moco2025team1.ui.theme.MOCO2025Team1Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moco2025team1.ui.screens.ContactSelectionScreen
import com.example.moco2025team1.ui.screens.LoginScreen
import com.example.moco2025team1.viewmodel.SessionViewModel
import com.example.moco2025team1.viewmodel.PromptViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val sessionViewModel     = viewModel<SessionViewModel>()

            MOCO2025Team1Theme {
                OurScaffold(
                    showBottomBar = sessionViewModel.currentUser.collectAsState().value != null,
                    onNavigate   = { navController.navigate(it) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = LoginRoute
                    ) {

                        composable<LoginRoute> {
                            LoginScreen(
                                sessionViewModel = sessionViewModel,
                                navController    = navController
                            )
                        }

                        composable<HomeRoute> {
                            HomeScreen(
                                sessionViewModel = sessionViewModel,
                                onContactClick = { index ->
                                }
                            )
                        }

                        composable<ProfileRoute> {
//                            backStack ->
//                            val name = backStack.toRoute<HomeRoute>().name
                            ProfileScreen(sessionViewModel, navController)
                        }
                        composable<PromptSelectionRoute> {
//                            PromptSelectionScreen(navController)
                        }
                        composable<NewEntryRoute> {
//                            NewEntryScreen()
                        }
                        composable<ContactSelectionRoute> {
                            ContactSelectionScreen()
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
data object PromptSelectionRoute : Route()

@Serializable
data object ProfileRoute : Route()

@Serializable
data object ContactSelectionRoute : Route()

@Serializable
data object LoginRoute : Route()


