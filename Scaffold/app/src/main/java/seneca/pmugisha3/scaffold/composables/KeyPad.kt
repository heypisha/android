package seneca.pmugisha3.scaffold.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Creates the calculator keypad with all buttons arranged in a 4x4 grid.
 *
 * This composable generates a button grid for calculator input:
 * - Row 1: 1, 2, 3, +
 * - Row 2: 4, 5, 6, -
 * - Row 3: 7, 8, 9, *
 * - Row 4: C, 0, =, /
 *
 * @param keyClicked A callback function that receives the button label (String)
 *                   when any button is pressed. The parent component uses this
 *                   to handle button clicks and update the calculator state.
 */
@Composable
fun KeyPad(keyClicked: (String) -> Unit) {
    // 2D list defining the button layout (4 rows x 4 columns)
    // Each inner list is one row of buttons
    val buttons = listOf(
        listOf("1", "2", "3", "+"),  // First row
        listOf("4", "5", "6", "-"),  // Second row
        listOf("7", "8", "9", "*"),  // Third row
        listOf("C", "0", "=", "/")   // Fourth row
    )

    // Column arranges button rows vertically
    Column(
        modifier = Modifier.Companion.fillMaxWidth(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        // Loop through each row of buttons
        buttons.forEach { row ->
            // Row arranges buttons horizontally
            Row(
                modifier = Modifier.Companion.padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)  // 8dp space between buttons
            ) {
                // Loop through each button in the current row
                row.forEach { label ->
                    // Create a button for each label
                    Button(onClick = { keyClicked(label) }) {  // Call keyClicked when pressed
                        Text(label)  // Display the button's label (number or operator)
                    }
                }
            }
        }
    }
}