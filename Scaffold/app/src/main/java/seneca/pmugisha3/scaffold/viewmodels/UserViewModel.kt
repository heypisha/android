package seneca.pmugisha3.scaffold.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel for managing user authentication state.
 *
 * Handles user login information and greeting messages.
 * Stores the logged-in user's name and generates greetings.
 */
class UserViewModel : ViewModel() {

    // The name entered by the user (not yet logged in)
    var loggedInName by mutableStateOf("")
        private set

    // Greeting message shown after successful login
    var greetings by mutableStateOf("")
        private set

    /**
     * Updates the user's name as they type.
     *
     * @param newName The updated name string from the input field
     */
    fun updateName(newName: String) {
        loggedInName = newName
    }

    /**
     * Attempts to log in the user.
     *
     * If the name is not blank, sets the greeting message.
     * Otherwise, the user remains logged out with no greeting.
     */
    fun login() {
        if (loggedInName.isNotBlank()) {
            greetings = "Welcome, $loggedInName!"
        }
    }

    /**
     * Logs out the current user by clearing all state.
     */
    fun logout() {
        loggedInName = ""
        greetings = ""
    }
}