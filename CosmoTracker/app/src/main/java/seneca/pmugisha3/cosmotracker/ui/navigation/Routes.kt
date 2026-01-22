package seneca.pmugisha3.cosmotracker.ui.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Events : Routes("events")
    object EventDetail : Routes("event_detail") {
        const val argName = "eventId"
        val routeWithArgs = "$route/{$argName}"
        fun createRoute(eventId: String): String = "$route/$eventId"
    }
    object Favorites : Routes("favorites")
    object HazardNearMe : Routes("hazard_near_me")
  object Settings : Routes("settings")
}
