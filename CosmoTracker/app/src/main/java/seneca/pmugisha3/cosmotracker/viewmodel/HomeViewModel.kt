package seneca.pmugisha3.cosmotracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import seneca.pmugisha3.cosmotracker.data.remote.model.ApodResponse
import seneca.pmugisha3.cosmotracker.data.repository.Resource
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepository
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepositoryImpl

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val apod: ApodResponse) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SpaceRepository = SpaceRepositoryImpl(application)

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadApod()
    }

    fun loadApod() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            when (val result = repository.getApod()) {
                is Resource.Success -> {
                    _uiState.value = HomeUiState.Success(result.data)
                }
                is Resource.Error -> {
                    _uiState.value = HomeUiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _uiState.value = HomeUiState.Loading
                }
            }
        }
    }
}
