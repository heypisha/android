package com.seneca.pmugisha3.cashregisterapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.seneca.pmugisha3.cashregisterapp.data.model.StockItem
import com.seneca.pmugisha3.cashregisterapp.data.repository.CashRegisterRepository

/**
 * Result of the purchase validation check.
 */
sealed class ValidationResult {
    object Success : ValidationResult()
    object NoProduct : ValidationResult()
    object InvalidQuantity : ValidationResult()
    data class NotEnoughStock(val availableStock: Int) : ValidationResult()
}

/**
 * ViewModel for the Cash Register application.
 * Manages UI state, transaction logic, and access to data via the repository.
 */
class CashRegisterViewModel(
    private val repository: CashRegisterRepository = CashRegisterRepository()
) : ViewModel() {

    // --- Observable List Getters ---
    // These return the observable lists from the Repository directly.
    val stockItems: List<StockItem>
        get() = repository.getStockItems()

    val purchaseHistory: List<com.seneca.pmugisha3.cashregisterapp.data.model.Purchase>
        get() = repository.getPurchaseHistory()

    // --- State Management: Current Transaction ---

    // Selected product/stock item from the list
    var selectedStockItem: StockItem? by mutableStateOf(null)
        private set

    // Quantity entered by the user via the number pad
    var enteredQuantity by mutableStateOf("")
        private set

    // Automatically calculate the total amount
    val currentTotal: Double
        get() {
            val price = selectedStockItem?.product?.price ?: 0.0
            val quantity = enteredQuantity.toIntOrNull() ?: 0
            return price * quantity
        }

    // --- UI Interaction Handlers ---

    fun selectProduct(item: StockItem) {
        selectedStockItem = item
        // Clear quantity input for a new product selection
        enteredQuantity = ""
    }

    fun appendDigit(digit: Int) {
        // Prevent leading zero if input is empty
        if (enteredQuantity.isEmpty() && digit == 0) return
        if (enteredQuantity.length < 9) { // Simple length limit
            enteredQuantity += digit.toString()
        }
    }

    fun clearQuantity() {
        enteredQuantity = ""
    }

    /**
     * Validates the current transaction state against business rules.
     */
    fun validatePurchase(): ValidationResult {
        val selected = selectedStockItem
        val quantity = enteredQuantity.toIntOrNull()

        if (selected == null) {
            return ValidationResult.NoProduct // no product selected
        }

        if (quantity == null || quantity <= 0) {
            return ValidationResult.InvalidQuantity // no quantity selected
        }

        if (quantity > selected.stock) {
            // quantity > available stock
            return ValidationResult.NotEnoughStock(selected.stock)
        }

        return ValidationResult.Success
    }

    /**
     * Executes the purchase logic (called only after successful validation).
     */
    fun completePurchase() {
        val item = selectedStockItem ?: return
        val quantity = enteredQuantity.toIntOrNull() ?: return

        // The repository handles the creation of the Purchase object and stock update.
        repository.completePurchase(item.product, quantity)

        // Reset state after successful purchase
        selectedStockItem = null
        enteredQuantity = ""
    }
}