package seneca.pmugisha3.scaffold.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

/**
 * ViewModel for managing app-wide theme and UI settings.
 *
 * Controls dark/light mode and corresponding background colors.
 * This should be shared across the entire app for consistent theming.
 */
class AppThemeViewModel : ViewModel() {

    // Current background color based on theme
    var currentColor by mutableStateOf(Color.Companion.White)
        private set

    // Dark mode flag: false = light mode, true = dark mode
    var isDarkMode by mutableStateOf(false)
        private set

    /**
     * Updates the app's theme mode and adjusts colors accordingly.
     *
     * @param darkMode True for dark mode, false for light mode
     */
    fun updateThemeMode(darkMode: Boolean) {
        isDarkMode = darkMode
        // Update background color based on theme
        currentColor = if (darkMode) Color.Companion.DarkGray else Color.Companion.White
    }

    /**
     * Toggles between dark and light mode.
     */
    fun toggleTheme() {
        updateThemeMode(!isDarkMode)
    }
}