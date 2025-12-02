package com.seneca.pmugisha3.cashregisterapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.seneca.pmugisha3.cashregisterapp.data.model.Purchase
import com.seneca.pmugisha3.cashregisterapp.data.model.StockItem
import com.seneca.pmugisha3.cashregisterapp.data.repository.CashRegisterRepository
import com.seneca.pmugisha3.cashregisterapp.util.FormattingUtils

/**
 * ViewModel for the Cash Register screen
 * Manages UI states and delegates data operations to the repository to insure that the UI only reacts to state changes,
 * without directly manipulating the data layer
 */
class CashRegisterViewModel(private val repository: CashRegisterRepository = CashRegisterRepository()) :
    ViewModel() {
    /**
     * Current selected item for purchase
     */
    var selectedStockItem by mutableStateOf<StockItem?>(null)
        private set

    /**
     * Current quantity entered by user via number pad
     */
    var enteredQuantity by mutableStateOf("")
        private set

    /**
     * Retrieve stock items from repository
     */
    val stockItems: List<StockItem>
        get() = repository.getStockItems()

    /**
     * Retrieve purchase history from repository
     */
    val purchaseHistory: List<Purchase>
        get() = repository.getPurchaseHistory()

    /**
     * Calculate total price for the current selected product
     */
    val currentTotal: Double
        get() {
            val qty = enteredQuantity.toIntOrNull() ?: 0
            val price = selectedStockItem?.product?.price ?: 0.0
            return FormattingUtils.calculateTotal(qty,price)
        }

    /**
     * Append a digit to the entered quantity
     * @param digit - the digit to append
     */
    fun appendDigit(digit: Int) {
        // Prevent leading zeros
        if (enteredQuantity.isEmpty() && digit == 0) return

        // Limit to 3 digits(max quantity = 999)
        if (enteredQuantity.length > 3) return

        enteredQuantity += digit.toString()
    }

    /**
     * Clear the entered quantity
     */
    fun clearQuantity() {
        enteredQuantity = ""
    }

    /**
     * Validate if purchase can be completed
     * @return true if product is selected, quantity is valid and stock is sufficient
     */
    fun canCompletePurchase(): Boolean {
        val qty = enteredQuantity.toIntOrNull() ?: 0
        val stock = selectedStockItem?.stock ?: 0
        return selectedStockItem != null && qty > 0 && qty <= stock
    }

    /**
     * Complete purchase transaction
     * Update stock and add record to purchase history via repository
     * @return true if successful, false otherwise
     */
    fun completePurchase(): Boolean{
        val stockItem = selectedStockItem?:return false
        val quantity = enteredQuantity.toIntOrNull()?:return false
        return try {
            // Delegate to repository
            repository.addPurchaseHistory(stockItem.product,quantity)

            // Reset UI after successful transaction
            selectedStockItem=null
            enteredQuantity = ""
            true
        }catch (e: IllegalArgumentException){
            false
        }
    }
}