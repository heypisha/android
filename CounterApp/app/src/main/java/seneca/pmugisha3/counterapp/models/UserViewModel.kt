package seneca.pmugisha3.counterapp.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var loggedInName = mutableStateOf("")
        private set
    var greetings = mutableStateOf("")
        private set

    fun updateName(newName: String) {
        loggedInName.value = newName
    }

    fun login() {
        if (loggedInName.value.isNotBlank()) {
            greetings.value = loggedInName.value
        }
    }
}