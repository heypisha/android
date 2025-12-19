package seneca.pmugisha3.cosmotracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepository
import seneca.pmugisha3.cosmotracker.data.repository.SpaceRepositoryImpl

class ViewModelFactory(private val repository: SpaceRepository = SpaceRepositoryImpl()) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(EventsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
