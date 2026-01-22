package seneca.pmugisha3.cosmotracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepository
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepositoryImpl

sealed interface EventDetailUiState {
    object Loading : EventDetailUiState
    data class Success(val event: EventDto) : EventDetailUiState
    data class Error(val message: String) : EventDetailUiState
}

class EventDetailViewModel(
    application: Application, private  val eventId: String
) : AndroidViewModel(application) {
  private val repository: SpaceRepository = SpaceRepositoryImpl(application)

    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    init {
        getEventDetail()
    }

    fun getEventDetail() {
        viewModelScope.launch {
            _uiState.value = EventDetailUiState.Loading
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
