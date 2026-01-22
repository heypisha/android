package seneca.pmugisha3.cosmotracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import seneca.pmugisha3.cosmotracker.ui.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        modifier = modifier
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen(
                onNavigateToEvents = {
                    navController.navigate(Routes.Events.route)
                },
                onNavigateToHazardNearMe = {
                    navController.navigate(Routes.HazardNearMe.route)
                }
            )
        }

        composable(route = Routes.Events.route) {
            EventsScreen(
                onEventClick = { eventId ->
                    navController.navigate(Routes.EventDetail.createRoute(eventId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.EventDetail.routeWithArgs,
            arguments = listOf(
                navArgument(Routes.EventDetail.argName) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString(Routes.EventDetail.argName) ?: ""
            EventDetailScreen(
                eventId = eventId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Routes.HazardNearMe.route) {
            HazardNearMeScreen(
                onEventClick = { eventId ->
                    navController.navigate(Routes.EventDetail.createRoute(eventId))
                }
            )
        }

        composable(route = Routes.Favorites.route) {
            FavoritesScreen(
                onEventClick = { eventId ->
                    navController.navigate(Routes.EventDetail.createRoute(eventId))
                }
            )
        }

        composable(route = Routes.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}