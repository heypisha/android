package com.seneca.pmugisha3.cashregisterapp.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NumberPad(
    onNumberClick: (Int) -> Unit,
    onClearClick: () -> Unit,
    onBuyClick: () -> Unit,
    canBuy: Boolean,
    modifier: Modifier = Modifier
) {
    // 2D list defining the number buttons layout (1 through 9)
    // Each inner list is one row of buttons
    val numberButtons = listOf(
        listOf("1", "2", "3"),  // First row
        listOf("4", "5", "6"),  // Second row
        listOf("7", "8", "9"),  // Third row
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Left column - Number pad
        Column(
            modifier = modifier.weight(3f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Loop through each row of numberButtons
            numberButtons.forEach { row ->
                // Row arranges buttons horizontally
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)  // 8dp space between buttons
                ) {
                    // Loop through each button in the current row
                    row.forEach { label ->
                        // Create a button for each label
                        Button(
                            onClick = { onNumberClick(label.toInt()) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                label,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onClearClick,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        "C",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Button(
                    onClick = { onNumberClick(0) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "0",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }

        }

        // Right column - Buy button
        Button(
            onClick = onBuyClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            enabled = canBuy
        ) {
            Text(
                "BUY",
                style = MaterialTheme.typography.headlineMedium
            )

        }
    }
}

@Preview(showBackground = true, heightDp = 215)
@Composable
fun NumberPadPreview() {
    NumberPad({}, {}, {},true,)
}

