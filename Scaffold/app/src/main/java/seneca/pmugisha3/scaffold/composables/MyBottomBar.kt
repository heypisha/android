package seneca.pmugisha3.scaffold.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import seneca.pmugisha3.scaffold.navigation.NavItems

/**
 * Bottom navigation bar component for app navigation.
 *
 * Creates a Material3 NavigationBar with items for different screens.
 * Highlights the currently selected item and handles navigation between screens.
 *
 * @param onNavigate Callback function invoked when a navigation item is clicked.
 *                   Receives the path/route of the selected destination.
 */
@Composable
fun MyBottomBar(onNavigate: (String) -> Unit) {
    // List of navigation items to display in the bottom bar
    // Use 'val' instead of 'var' since this list never changes
    val navItems = listOf(NavItems.calculator, NavItems.counter, NavItems.logIn)

    // Track which navigation item is currently selected (0-based index)
    // Use 'by' delegation for cleaner syntax (no .value needed)
    var selectedIndex by remember { mutableStateOf(0) }

    NavigationBar {
        // Loop through each navigation item with its index
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                onClick = {
                    // Update the selected index
                    selectedIndex = index
                    // Trigger navigation to the selected screen
                    onNavigate(item.path)
                },
                // Highlight this item if it matches the selected index
                selected = selectedIndex == index,
                // Display the item's title below the icon
                label = { Text(item.title) },
                // Display the item's icon
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title  // Use title for better accessibility
                    )
                }
            )
        }
    }
}