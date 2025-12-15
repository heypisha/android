package com.seneca.pmugisha3.cashregisterapp.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
 * List view displaying available stock items (Name, Quantity, Price).
 */
@Composable
fun ProductList(
    stockItems: List<StockItem>,
    selectedStockItem: StockItem?,
    onProductClick: (StockItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Header for the list
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Product Name", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(3f))
                Text("Stock", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                Text("Price", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1.5f), maxLines = 1)
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(stockItems) { item ->
            val isSelected = item.product.id == selectedStockItem?.product?.id
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductClick(item) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.product.name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(3f))
                    // The stock property is an observable state property
                    Text("${item.stock}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    Text(
                        FormattingUtils.formatCurrency(item.product.price),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1.5f)
                    )
                }
            }
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