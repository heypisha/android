package com.seneca.pmugisha3.cashregisterapp.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seneca.pmugisha3.cashregisterapp.data.model.Product
import com.seneca.pmugisha3.cashregisterapp.data.model.StockItem
import com.seneca.pmugisha3.cashregisterapp.util.FormattingUtils

/**
 * Displays the currently selected product with quantity and total
 * @param selectedStockItem Currently selected stock item (null if none selected)
 * @param enteredQuantity The quantity entered by user
 * @param currentTotal Calculated total price
 * @param canPay Whether the pay button should be enabled
 * @param onPayClick Callback when pay button is clicked
 * @param modifier Optional modifier for the component
 */
@Composable
fun SelectedProductDisplay(
    modifier: Modifier = Modifier,
    selectedStockItem: StockItem?,
    enteredQuantity: String,
    currentTotal: Double,
    canPay: Boolean,
    onPayClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Selected Product",
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider()

            if (selectedStockItem != null) {
                // Product name
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Product:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = selectedStockItem.product.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Unit price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Price:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = FormattingUtils.formatCurrency(selectedStockItem.product.price),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Quantity
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Quantity:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = enteredQuantity.ifEmpty { "0" },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                HorizontalDivider()

                // Total
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = FormattingUtils.formatCurrency(currentTotal),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Pay button
                Button(
                    onClick = onPayClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canPay
                ) {
                    Text(
                        text = "Pay",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                // No product selected
                Text(
                    text = "Please select a product",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedProductDisplayPreview() {
    val sampleProduct = StockItem(Product("1", "Cupcake", 2.99), 50)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // With selection
        SelectedProductDisplay(
            selectedStockItem = sampleProduct,
            enteredQuantity = "5",
            currentTotal = 14.95,
            canPay = true,
            onPayClick = {}
        )

        // Without selection
        SelectedProductDisplay(
            selectedStockItem = null,
            enteredQuantity = "",
            currentTotal = 0.0,
            canPay = false,
            onPayClick = {}
        )
    }
}