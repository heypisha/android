package com.seneca.pmugisha3.cashregisterapp.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
 * Displays a list of products with their stock
 * @param stockItems List of products with available stock
 * @param selectedStockItem Currently selected stock item (if any)
 * @param onProductClick Callback when a product is clicked
 * @param modifier Optional modifier for the component
 */
@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    stockItems: List<StockItem>,
    selectedStockItem: StockItem?,
    onProductClick: (StockItem) -> Unit
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(stockItems) { stockItem ->
            ProductListItem(
                stockItem = stockItem,
                isSelected = stockItem == selectedStockItem,
                onClick = { onProductClick(stockItem) })
        }
    }
}

/**
 * Single product item in the list
 */
@Composable
fun ProductListItem(
    modifier: Modifier = Modifier,
    stockItem: StockItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = modifier.weight(1f)) {
                Text(text = stockItem.product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = FormattingUtils.formatCurrency(stockItem.product.price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "Stock: ${stockItem.stock}",
                style = MaterialTheme.typography.bodySmall,
                color = if (stockItem.stock > 0)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.error
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val sampleItems = listOf(
        StockItem(Product("1", "Cupcake", 2.99), 50),
        StockItem(Product("2", "Donut", 1.99), 75),
        StockItem(Product("3", "Eclair", 3.49), 0)
    )
    ProductList(
        stockItems = sampleItems,
        selectedStockItem = sampleItems[0],
        onProductClick = {}
    )
}