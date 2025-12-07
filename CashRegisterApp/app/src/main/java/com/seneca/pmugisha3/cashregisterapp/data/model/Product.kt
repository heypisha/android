package com.seneca.pmugisha3.cashregisterapp.data.model

import androidx.compose.runtime.Immutable

/**
 * Immutable data class representing a product in the store
 *
 * @property id - Unique product identifier
 * @property name - Name of the product
 * @property price - Price per unity
 *
 * Class marked as @Immutable because its properties never changes after creation.
 * This allows Jetpack Compose to optimize recompositions.
 */
@Immutable
data class Product(
    val id: String,
    val name: String,
    val price: Double
)

