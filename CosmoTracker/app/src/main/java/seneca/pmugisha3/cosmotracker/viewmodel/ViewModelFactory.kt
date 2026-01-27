package seneca.pmugisha3.cosmotracker.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
  private val application: Application,
  private val eventId: String? = null
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return when {
      modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
        @Suppress("UNCHECKED_CAST")
        HomeViewModel(application) as T
      }

      modelClass.isAssignableFrom(EventsViewModel::class.java) -> {
        @Suppress("UNCHECKED_CAST")
        EventsViewModel(application) as T
      }

      modelClass.isAssignableFrom(FavoriteEventViewModel::class.java) -> {
        @Suppress("UNCHECKED_CAST")
        FavoriteEventViewModel(application) as T
      }

      modelClass.isAssignableFrom(EventDetailViewModel::class.java) -> {
        @Suppress("UNCHECKED_CAST")
        EventDetailViewModel(application, eventId ?: "") as T
      }

      else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
  }
}