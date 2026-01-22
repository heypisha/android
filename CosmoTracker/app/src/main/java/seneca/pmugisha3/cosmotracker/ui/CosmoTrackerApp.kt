package seneca.pmugisha3.cosmotracker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import seneca.pmugisha3.cosmotracker.ui.navigation.NavGraph
import seneca.pmugisha3.cosmotracker.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CosmoTrackerApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CosmoTracker") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Open drawer in Phase 6 */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Notifications in Phase 6 */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        },
        bottomBar = {
            // Only show bottom nav on main screens (not on detail screens)
            if (currentRoute != null && !currentRoute.contains("event_detail")) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Routes.Home.route,
                        onClick = { 
                            navController.navigate(Routes.Home.route) {
                                popUpTo(Routes.Home.route) { inclusive = true }
                            }
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Routes.Events.route,
                        onClick = { 
                            navController.navigate(Routes.Events.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Events") },
                        label = { Text("Events") }
                    )
                  NavigationBarItem(
                        selected = currentRoute == Routes.Favorites.route,
                        onClick = {
                            navController.navigate(Routes.Favorites.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                        label = { Text("Favorites") }
                    )
                  NavigationBarItem(
                        selected = currentRoute == Routes.HazardNearMe.route,
                  onClick = {
                    navController.navigate(Routes.HazardNearMe.route) {
                      popUpTo(Routes.Home.route)
                    }
                  },
                  icon = { Icon(Icons.Default.LocationOn, contentDescription = "Hazard Near Me") },
                  label = { Text("Hazards") }
                  )
                    NavigationBarItem(
                        selected = currentRoute == Routes.Settings.route,
                        onClick = { 
                            navController.navigate(Routes.Settings.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}