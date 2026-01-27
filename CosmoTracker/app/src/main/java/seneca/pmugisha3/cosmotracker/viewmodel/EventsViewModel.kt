package seneca.pmugisha3.cosmotracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto
import seneca.pmugisha3.cosmotracker.data.repository.Resource
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepository
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepositoryImpl

sealed class EventsUiState {
    object Loading : EventsUiState()
    data class Success(val events: List<EventDto>) : EventsUiState()
    data class Error(val message: String) : EventsUiState()
}

class EventsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SpaceRepository = SpaceRepositoryImpl(application)

    private val _eventsState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val eventsState: StateFlow<EventsUiState> = _eventsState.asStateFlow()

    private val _isFavorite = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val isFavorite: StateFlow<Map<String, Boolean>> = _isFavorite.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            _eventsState.value = EventsUiState.Loading

            when (val result = repository.getEvents()) {
                is Resource.Success -> {
                    _eventsState.value = EventsUiState.Success(result.data.events)
                    checkFavorites(result.data.events)
                }
                is Resource.Error -> {
                    _eventsState.value = EventsUiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _eventsState.value = EventsUiState.Loading
                }
            }
        }
    }

    fun filterEvents(status: String = "open", limit: Int = 50) {
        viewModelScope.launch {
            _eventsState.value = EventsUiState.Loading

            when (val result = repository.getEvents(status, limit)) {
                is Resource.Success -> {
                    _eventsState.value = EventsUiState.Success(result.data.events)
                    checkFavorites(result.data.events)
                }
                is Resource.Error -> {
                    _eventsState.value = EventsUiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _eventsState.value = EventsUiState.Loading
                }
            }
        }
    }

    private fun checkFavorites(events: List<EventDto>) {
        viewModelScope.launch {
            val favoriteMap = mutableMapOf<String, Boolean>()
            events.forEach { event ->
                favoriteMap[event.id] = repository.isFavorite(event.id)
            }
            _isFavorite.value = favoriteMap
        }
    }

    fun toggleFavorite(event: EventDto) {
        viewModelScope.launch {
            val currentlyFavorite = repository.isFavorite(event.id)
            if (currentlyFavorite) {
                repository.removeFavorite(event.id)
            } else {
                repository.addFavorite(event)
            }
            // Update the map
            _isFavorite.value = _isFavorite.value.toMutableMap().apply {
                put(event.id, !currentlyFavorite)
            }
        }
    }
}
