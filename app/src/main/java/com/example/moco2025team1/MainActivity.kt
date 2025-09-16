package com.example.moco2025team1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.moco2025team1.ui.composables.OurScaffold
import com.example.moco2025team1.ui.screens.ContactSelectionScreen
import com.example.moco2025team1.ui.screens.EntryViewerScreen
import com.example.moco2025team1.ui.screens.HomeScreen
import com.example.moco2025team1.ui.screens.LoginScreen
import com.example.moco2025team1.ui.screens.ProfileScreen
import com.example.moco2025team1.ui.theme.MOCO2025Team1Theme
import com.example.moco2025team1.viewmodel.SessionViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val sessionViewModel     = viewModel<SessionViewModel>()

            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            val titles = mapOf(
                HomeRoute::class.qualifiedName!! to "Home",
                ProfileRoute::class.qualifiedName!! to "Profile",
            )
            val currentTitle = titles[currentRoute]

            MOCO2025Team1Theme {
                OurScaffold(
                    title = currentTitle,
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
                                    navController.navigate(EntryViewerRoute(2))
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
                        composable<EntryViewerRoute> { backstack ->
                            val entryId = backstack.toRoute<EntryViewerRoute>().entryId
                            EntryViewerScreen(entryId)
                        }
                    }

                }

            }
        }
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

@Serializable
data class EntryViewerRoute(val entryId: Long) : Route()


