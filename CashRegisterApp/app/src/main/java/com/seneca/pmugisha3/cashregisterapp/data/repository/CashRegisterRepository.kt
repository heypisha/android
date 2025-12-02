package com.seneca.pmugisha3.cashregisterapp.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.seneca.pmugisha3.cashregisterapp.data.model.Product
import com.seneca.pmugisha3.cashregisterapp.data.model.Purchase
import com.seneca.pmugisha3.cashregisterapp.data.model.StockItem
import com.seneca.pmugisha3.cashregisterapp.data.sample.SampleData

/**
 * Repository for managing cash register data
 * Handles stock items and purchase history with observable state lists
 */
class CashRegisterRepository {
    /**
     * Mutable state list of stock items - triggers recomposition automatically when changed
     */
    private val stockItems = mutableStateListOf<StockItem>()

    /**
     * Mutable state list of stock items - automatically triggers recomposition when changed
     */
    private val purchaseHistory = mutableStateListOf<Purchase>()

    // Initialize with sample data
    init {
        stockItems.addAll(SampleData.initialStock)
        purchaseHistory.addAll(SampleData.sampleHistory)
    }

    /**
     * Returns a read-only list of stock items
     */
    fun getStockItems(): List<StockItem> = stockItems

    /**
     * Returns a read-only list of purchase history
     */
    fun getPurchaseHistory(): List<Purchase> = purchaseHistory

    /**
     * Processes a purchase by adding to history and updating the stock
     *
     * @param product - The product being purchased
     * @param quantity - Number of units of the product to purchase
     * @throws IllegalArgumentException if insufficient stock is available
     */
    fun addPurchaseHistory(product: Product, quantity: Int) {
        // Find the product in the stock
        val item = stockItems.find { it.product.id == product.id }

        // Validate stock availability
        if (item != null && item.stock >= quantity) {
            item.stock -= quantity // Decrease stock by quantity purchased

            // Add purchase record to history
            purchaseHistory.add(Purchase(product, quantity))
        } else { // Throw an error if not enough stock
            throw IllegalArgumentException("Not enough stock for ${product.name}")
        }
    }
}