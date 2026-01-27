package seneca.pmugisha3.cosmotracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import seneca.pmugisha3.cosmotracker.data.local.entity.FavoriteEventEntity
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepository
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepositoryImpl

class FavoriteEventViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: SpaceRepository = SpaceRepositoryImpl(application)

  val favorites: StateFlow<List<FavoriteEventEntity>> = repository.getAllFavorites()
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )

  init {
    // Sync favorites from Firebase when ViewModel is created
    syncFromFirebase()
  }

  fun deleteFavorite(eventId: String) {
    viewModelScope.launch {
      repository.removeFavorite(eventId)
    }
  }

  private fun syncFromFirebase() {
    viewModelScope.launch {
      repository.syncFavoritesFromFirebase()
    }
  }
}