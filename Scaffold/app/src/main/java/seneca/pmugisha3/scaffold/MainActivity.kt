package seneca.pmugisha3.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import seneca.pmugisha3.scaffold.composables.MainAppUI
import seneca.pmugisha3.scaffold.viewmodels.AppThemeViewModel
import seneca.pmugisha3.scaffold.viewmodels.CounterViewModel
import seneca.pmugisha3.scaffold.viewmodels.UserViewModel

/**
 * Main activity for the application.
 *
 * This activity serves as the entry point and container for the app.
 * It initializes all ViewModels with proper lifecycle management and
 * delegates UI rendering to the MainAppUI composable.
 * 
 * All ViewModels are scoped to this activity and will survive
 * configuration changes like screen rotation.
 */
class MainActivity : ComponentActivity() {
    // Initialize ViewModels using 'by viewModels()' delegate
    // This ensures proper lifecycle management and configuration change survival
    private val counterVM: CounterViewModel by viewModels()
    private val userVM: UserViewModel by viewModels()
    private val themeVM: AppThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the composable content with all ViewModels
        setContent {
            MainAppUI(
                counterViewModel = counterVM,
                userViewModel = userVM,
                themeViewModel = themeVM
            )
        }
    }
}



