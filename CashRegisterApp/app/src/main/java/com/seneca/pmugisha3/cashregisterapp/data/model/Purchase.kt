package com.seneca.pmugisha3.cashregisterapp.data.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

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
class Purchase(val product: Product, quantity: Int) {
    var quantity by mutableStateOf(quantity)
}