package com.seneca.pmugisha3.cashregisterapp.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.seneca.pmugisha3.cashregisterapp.data.model.Purchase
import com.seneca.pmugisha3.cashregisterapp.util.FormattingUtils

/**
 * Single purchase item in the history list
 */
@Composable
fun PurchaseHistoryItem(
    purchase: Purchase,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Product name
            Text(
                text = purchase.product.name,
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider()

            // Quantity and unit price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Quantity: ${purchase.quantity}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Unit Price: ${FormattingUtils.formatCurrency(purchase.product.price)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Total price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = purchase.getFormattedTotal(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Timestamp
            Text(
                text = purchase.getFormattedTimestamp(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PurchaseHistoryItemPreview() {
    val samplePurchase = Purchase(
        product = Product("1", "Cupcake", 2.99),
        quantity = 5,
        timestamp = System.currentTimeMillis()
    )
    PurchaseHistoryItem(purchase = samplePurchase)
}