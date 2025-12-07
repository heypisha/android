package com.seneca.pmugisha3.cashregisterapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seneca.pmugisha3.cashregisterapp.ui.screens.components.NumberPad
import com.seneca.pmugisha3.cashregisterapp.ui.screens.components.ProductList
import com.seneca.pmugisha3.cashregisterapp.util.FormattingUtils
import com.seneca.pmugisha3.cashregisterapp.viewmodel.CashRegisterViewModel

/**
 * Main Cash Register Screen
 * Vertical layout matching assignment requirements
 * @param viewModel The shared ViewModel managing state
 * @param onNavigateToHistory Callback to navigate to purchase history screen
 * @param modifier Optional modifier for the screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashRegisterScreen(
    viewModel: CashRegisterViewModel = viewModel(),
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cash Register") },
                actions = {
                    TextButton(onClick = onNavigateToHistory) {
                        Text("History")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Selected Product TextView
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = viewModel.selectedStockItem?.product?.name ?: "No product selected",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // 2. Total Amount TextView
            Card(
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End),
                /*colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )*/
            ) {
                Text(
                    text = "Total: ${FormattingUtils.formatCurrency(viewModel.currentTotal)}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // 3. NumberPad
            NumberPad(
                onNumberClick = { digit ->
                    viewModel.appendDigit(digit)
                },
                onClearClick = {
                    viewModel.clearQuantity()
                },
                onBuyClick = {
                    // Validate before purchase
                    val selectedItem = viewModel.selectedStockItem
                    val quantity = viewModel.enteredQuantity.toIntOrNull() ?: 0

                    when {
                        selectedItem == null -> {
                            android.widget.Toast.makeText(
                                context,
                                "Please select a product first",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                        quantity <= 0 -> {
                            android.widget.Toast.makeText(
                                context,
                                "Please enter a valid quantity",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                        quantity > selectedItem.stock -> {
                            android.widget.Toast.makeText(
                                context,
                                "Not enough stock! Available: ${selectedItem.stock}",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            if (viewModel.completePurchase()) {
                                android.widget.Toast.makeText(
                                    context,
                                    "Purchase successful!",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                                onNavigateToHistory()
                            }
                        }
                    }
                },
                canBuy = true,  // Always enabled, validation happens in onClick
                modifier = Modifier.height(250.dp)
            )

            // 4. Quantity TextView
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Text(
                    text = "Quantity: ${viewModel.enteredQuantity.ifEmpty { "0" }}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // 5. Products List
            ProductList(
                stockItems = viewModel.stockItems,
                selectedStockItem = viewModel.selectedStockItem,
                onProductClick = { stockItem ->
                    viewModel.selectProduct(stockItem)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 900)
@Composable
fun CashRegisterScreenPreview() {
    CashRegisterScreen(
        onNavigateToHistory = {}
    )
}