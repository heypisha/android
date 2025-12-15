package com.seneca.pmugisha3.cashregisterapp.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * On-screen number pad for quantity input.
 */
@Composable
fun NumberPad(
    onNumberClick: (Int) -> Unit,
    onClearClick: () -> Unit,
    onBuyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Custom color based on user request for darker seafoam/turquoise
    val customColor = MaterialTheme.colorScheme.primary

    // Define the button grid layout (3x4 for digits, plus C and Buy)
    val buttons = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9",
        "C", "0", "BUY"
    )

    // Calculate the number of columns and rows
    val columns = 3
    val rows = buttons.chunked(columns)

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { label ->
                    val isAction = label == "C" || label == "BUY"
                    val buttonColor = if (label == "BUY") customColor else MaterialTheme.colorScheme.tertiaryContainer

                    Button(
                        onClick = {
                            when (label) {
                                "C" -> onClearClick()
                                "BUY" -> onBuyClick()
                                else -> onNumberClick(label.toInt())
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = if (label == "BUY") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiaryContainer
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text(label, style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 245)
@Composable
fun NumberPadPreview() {
    NumberPad({}, {}, {},)
}

