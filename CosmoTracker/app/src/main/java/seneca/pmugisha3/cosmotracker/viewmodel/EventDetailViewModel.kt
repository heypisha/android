package seneca.pmugisha3.cosmotracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepository

sealed interface EventDetailUiState {
    object Loading : EventDetailUiState
    data class Success(val event: EventDto) : EventDetailUiState
    data class Error(val message: String) : EventDetailUiState
}

class EventDetailViewModel(
    private val repository: SpaceRepository,
    private val eventId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    init {
        getEventDetail()
    }

    fun getEventDetail() {
        viewModelScope.launch {
            _uiState.value = EventDetailUiState.Loading
            // Since EONET API usually returns a list, we fetch the list and find our event
            // Or if there's a specific endpoint for single event, we'd use that.
            // For now, we'll fetch the open events and find the ID.
            repository.getEvents()
                .onSuccess { response ->
                    val event = response.events.find { it.id == eventId }
                    if (event != null) {
                        _uiState.value = EventDetailUiState.Success(event)
                    } else {
                        _uiState.value = EventDetailUiState.Error("Event not found")
                    }
                }
                .onFailure { exception ->
                    _uiState.value = EventDetailUiState.Error(exception.message ?: "An unknown error occurred")
                }
        }
    }
}
