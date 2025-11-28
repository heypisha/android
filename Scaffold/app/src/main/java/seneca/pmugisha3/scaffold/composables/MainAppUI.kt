package seneca.pmugisha3.scaffold.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.jan6_project2.Scaffold.MyTopBar
import seneca.pmugisha3.scaffold.navigation.MyNavHost
import seneca.pmugisha3.scaffold.viewmodels.AppThemeViewModel
import seneca.pmugisha3.scaffold.viewmodels.CounterViewModel
import seneca.pmugisha3.scaffold.viewmodels.UserViewModel

/**
 * Main composable UI for the entire application.
 *
 * This composable sets up the app's scaffold structure including:
 * - Top app bar for titles and actions
 * - Bottom navigation bar for screen switching
 * - Floating action button for quick actions
 * - Navigation host that displays different screens
 * - Theme-aware background color that updates based on dark/light mode
 *
 * This composable is the root of the UI hierarchy and coordinates
 * all major UI components and ViewModels.
 *
 * @param counterViewModel ViewModel managing counter state and operations
 * @param userViewModel ViewModel managing user authentication and login state
 * @param themeViewModel ViewModel managing app theme, dark mode, and colors
 */
@Composable
fun MainAppUI(
    counterViewModel: CounterViewModel,
    userViewModel: UserViewModel,
    themeViewModel: AppThemeViewModel
) {
    // Create navigation controller to manage screen navigation
    // rememberNavController ensures it survives recomposition
    val navController = rememberNavController()

    // Scaffold provides the basic Material Design structure
    Scaffold(
        // Top bar shows app title and optional actions
        topBar = { MyTopBar() },

        // Floating action button for primary actions
        floatingActionButton = { MyFAB() },

        // Bottom navigation bar for switching between main screens
        bottomBar = {
            MyBottomBar { path ->
                // Navigate to the selected screen when a nav item is clicked
                navController.navigate(path)
            }
        }
    ) { innerPadding ->
        // Main content area that fills the space between scaffold components
        Column(
            modifier = Modifier
                .padding(innerPadding)  // Respect scaffold padding (top/bottom bars)
                .fillMaxSize()          // Take up all available space
                .background(themeViewModel.currentColor), // Apply theme background color
            verticalArrangement = Arrangement.SpaceBetween  // Space items evenly
        ) {
            // Navigation host manages which screen is currently displayed
            // Pass all ViewModels so individual screens can access the data they need
            MyNavHost(
                navController = navController,
                counterViewModel = counterViewModel,
                userViewModel = userViewModel,
                themeViewModel = themeViewModel
            )
        }
    }
}