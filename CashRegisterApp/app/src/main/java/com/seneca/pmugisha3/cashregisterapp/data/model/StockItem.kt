package com.seneca.pmugisha3.cashregisterapp.data.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Class representing a product along with its current stock count
 * @property product - immutable product details
 * @property stock - current available stock
 *
 * Class is marked @Stable because it contains mutable state (stock)
 * that is tracked by Compose using mutableStateOf.
 */
@Stable
class StockItem
    (
    val product: Product,
    stock: Int
) {
    var stock by mutableStateOf(stock)
}