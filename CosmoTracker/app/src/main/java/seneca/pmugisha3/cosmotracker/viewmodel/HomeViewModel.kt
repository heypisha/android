package seneca.pmugisha3.cosmotracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import seneca.pmugisha3.cosmotracker.data.remote.model.ApodResponse
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepository

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val apod: ApodResponse) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

class HomeViewModel(private val repository: SpaceRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getApod()
    }

    fun getApod() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            repository.getApod()
                .onSuccess { apod ->
                    _uiState.value = HomeUiState.Success(apod)
                }
                .onFailure { exception ->
                    _uiState.value = HomeUiState.Error(exception.message ?: "An unknown error occurred")
                }
        }
    }
}
