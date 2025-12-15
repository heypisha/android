package com.seneca.pmugisha3.cashregisterapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seneca.pmugisha3.cashregisterapp.ui.screens.components.PurchaseHistoryList
import com.seneca.pmugisha3.cashregisterapp.viewmodel.CashRegisterViewModel

/**
 * Purchase History Screen
 * @param viewModel The shared ViewModel managing state.
 * @param onBackClicked Callback to navigate back to the Cash Register screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CashRegisterViewModel = viewModel(),
    onBackClicked: () -> Unit
) {
    // Direct access to the observable history list
    val historyList = viewModel.purchaseHistory

    Scaffold(
        topBar = {
            TopAppBar(
                // Second screen will have the title for the second screen (per requirement)
                title = { Text("Purchase History") },
                // Second screen will have a back button (per requirement)
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
                    }
                },
                // Use custom color for TopBar
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        PurchaseHistoryList(
            // The history list updates automatically when the repository's mutableStateListOf is modified.
            purchaseHistory = historyList,
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PurchaseHistoryScreenPreview() {
    // Preview with empty history will be shown by default
    PurchaseHistoryScreen(onBackClicked = {})
}

