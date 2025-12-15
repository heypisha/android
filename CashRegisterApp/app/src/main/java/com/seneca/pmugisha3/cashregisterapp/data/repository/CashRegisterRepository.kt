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
     * Mutable state list of purchase history - automatically triggers recomposition when changed
     */
    private val purchaseHistory = mutableStateListOf<Purchase>()

    // Initialize with sample data
    init {
        // Add sample stock
        stockItems.addAll(SampleData.initialStock)
        // Add sample history, ensuring newest are first
        purchaseHistory.addAll(SampleData.sampleHistory.reversed())
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
     * Processes a purchase by creating a new Purchase object, adding it to history,
     * and updating the stock.
     *
     * @param product - The product being purchased (for stock lookup)
     * @param quantity - Number of units of the product to purchase
     * @throws IllegalArgumentException (Validation is usually handled by the VM, but retained here)
     */
    fun completePurchase(product: Product, quantity: Int) {
        // Find the product in the stock
        val item = stockItems.find { it.product.id == product.id }

        // The ViewModel should have validated this, but we finalize the state update here.
        if (item != null && item.stock >= quantity) {
            // 1. Update Stock
            item.stock -= quantity // Decrease stock by quantity purchased

            // 2. Add purchase record to history (creates the new @Stable Purchase object)
            purchaseHistory.add(0, Purchase(product, quantity)) // Add to the front
        } else {
            // Throwing is fine, but the VM logic will catch this case before calling.
            throw IllegalArgumentException("Not enough stock for ${product.name}")
        }
    }
}