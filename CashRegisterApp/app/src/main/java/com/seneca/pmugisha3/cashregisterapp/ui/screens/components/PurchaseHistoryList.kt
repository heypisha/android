package com.seneca.pmugisha3.cashregisterapp.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seneca.pmugisha3.cashregisterapp.data.model.Purchase

/**
 * List view displaying all completed purchases.
 * This component uses PurchaseHistoryItem for the layout of each individual entry.
 */
@Composable
fun PurchaseHistoryList(
    purchaseHistory: List<Purchase>,
    modifier: Modifier = Modifier
) {
    if (purchaseHistory.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No purchase history yet.", style = MaterialTheme.typography.headlineSmall)
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 🎯 Using your defined PurchaseHistoryItem for each entry
        items(purchaseHistory) { purchase ->
            PurchaseHistoryItem(purchase = purchase)
        }
    }
}