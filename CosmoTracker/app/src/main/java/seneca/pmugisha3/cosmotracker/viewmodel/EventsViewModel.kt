package seneca.pmugisha3.cosmotracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import seneca.pmugisha3.cosmotracker.data.remote.model.EonetResponse
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepository

sealed interface EventsUiState {
    object Loading : EventsUiState
    data class Success(val response: EonetResponse) : EventsUiState
    data class Error(val message: String) : EventsUiState
}

class EventsViewModel(private val repository: SpaceRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        getEvents()
    }

    fun getEvents() {
        viewModelScope.launch {
            _uiState.value = EventsUiState.Loading
            repository.getEvents()
                .onSuccess { response ->
                    _uiState.value = EventsUiState.Success(response)
                }
                .onFailure { exception ->
                    _uiState.value = EventsUiState.Error(exception.message ?: "An unknown error occurred")
                }
        }
    }
}
