package seneca.pmugisha3.cosmotracker.ui.navigation

sealed class Routes(val route: String) {
  object Home : Routes("home")
  object Events : Routes("events")
  object EventDetail : Routes("event_detail/{eventId}") {
    fun createRoute(eventId: String): String = "event_detail/$eventId"
  }
  object Favorites : Routes("favorites")
  object HazardNearMe : Routes("hazard_near_me")
}