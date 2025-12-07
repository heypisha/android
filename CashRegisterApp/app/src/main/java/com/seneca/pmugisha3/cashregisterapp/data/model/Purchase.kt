package com.seneca.pmugisha3.cashregisterapp.data.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.seneca.pmugisha3.cashregisterapp.util.FormattingUtils

/**
 * Class representing a purchase of a product in the cart
 *
 * @property product - Product being purchased
 * @property quantity - Quantity of units to purchase
 *
 * The class is marked @Stable because quantity changes are tracked via
 * mutableStateOf, allowing Compose to recompose only when needed.
 */
@Stable
class Purchase(
    val product: Product,
    quantity: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    var quantity by mutableStateOf(quantity)

    /**
     * Formats timestamp into human-readable date/time string
     */
    fun getFormattedTimestamp(): String {
        return FormattingUtils.formatTimestamp(timestamp)
    }

    /**
     * Gets total price for this purchase
     */
    fun getTotalPrice(): Double {
        return FormattingUtils.calculateTotal(quantity, product.price)
    }

    /**
     * Formats the total price as currency string
     */
    fun getFormattedTotal(): String {
        return FormattingUtils.formatCurrency(getTotalPrice())
    }
}