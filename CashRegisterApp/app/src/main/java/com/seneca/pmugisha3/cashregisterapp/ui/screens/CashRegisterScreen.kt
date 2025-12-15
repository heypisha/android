package com.seneca.pmugisha3.cashregisterapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seneca.pmugisha3.cashregisterapp.ui.screens.components.NumberPad
import com.seneca.pmugisha3.cashregisterapp.ui.screens.components.ProductList
import com.seneca.pmugisha3.cashregisterapp.util.FormattingUtils
import com.seneca.pmugisha3.cashregisterapp.viewmodel.CashRegisterViewModel
import com.seneca.pmugisha3.cashregisterapp.viewmodel.ValidationResult

/**
 * Main Cash Register Screen.
 * @param viewModel The shared ViewModel managing state.
 * @param onNavigateToHistory Callback to navigate to purchase history screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashRegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: CashRegisterViewModel = viewModel(),
    onNavigateToHistory: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Top bar of the main screen has only the title of the app (per requirement)
                    Text("Cash Register")
                },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = "View History"
                        )
                    }
                },
                // Use custom color for TopBar (assumes primary color is set to dark seafoam/turquoise)
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = viewModel.selectedStockItem?.product?.name ?: "Select a product",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = "Price: ${FormattingUtils.formatCurrency(viewModel.selectedStockItem?.product?.price ?: 0.0)}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            // 2. Total Amount TextView
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: ",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Text(
                        text = FormattingUtils.formatCurrency(viewModel.currentTotal),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            // 3. Quantity TextView
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    // The enteredQuantity is a mutableState, so changes trigger recomposition
                    text = "Quantity: ${viewModel.enteredQuantity.ifEmpty { "0" }}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }


            // 4. NumberPad (Occupies a fixed space before the list)
            NumberPad(
                onNumberClick = { digit -> viewModel.appendDigit(digit) },
                onClearClick = { viewModel.clearQuantity() },
                onBuyClick = {
                    val validation = viewModel.validatePurchase()

                    when (validation) {
                        ValidationResult.Success -> {
                            // Purchase must trigger navigation to history screen after storing
                            viewModel.completePurchase()
                            Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT).show()
                            onNavigateToHistory()
                        }
                        ValidationResult.NoProduct -> {
                            // Required: error message must appear
                            Toast.makeText(context, "Error: Please select a product.", Toast.LENGTH_LONG).show()
                        }
                        ValidationResult.InvalidQuantity -> {
                            // Required: error message must appear
                            Toast.makeText(context, "Error: Please enter a valid quantity.", Toast.LENGTH_LONG).show()
                        }
                        is ValidationResult.NotEnoughStock -> {
                            // Required: toast popped up with an error message
                            Toast.makeText(
                                context,
                                "Error: Not enough stock! Available: ${validation.availableStock}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )

            // 5. Products Name, Quantity, and Price List View
            ProductList(
                // The list from the ViewModel is the observable list from the repository
                stockItems = viewModel.stockItems,
                selectedStockItem = viewModel.selectedStockItem,
                onProductClick = { stockItem -> viewModel.selectProduct(stockItem) },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 900)
@Composable
fun CashRegisterScreenPreview() {
    CashRegisterScreen(onNavigateToHistory = {})
}