package com.seneca.pmugisha3.cashregisterapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility object for formatting values throughout the app
 */
object FormattingUtils {

    /**
     * Formats a price or total amount as currency string
     * @param amount The amount to format
     * @return Formatted string like "$12.99"
     */
    fun formatCurrency(amount: Double): String {
        return "$%.2f".format(amount)
    }

    /**
     * Formats a timestamp as a human-readable date/time string
     * @param timestamp Time in milliseconds since epoch
     * @param pattern Date format pattern (default: "MMM dd, yyyy hh:mm a")
     * @return Formatted string like "Dec 01, 2024 03:45 PM"
     */
    fun formatTimestamp(
        timestamp: Long,
        pattern: String = "MMM dd, yyyy hh:mm a"
    ): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

    /**
     * Safely parses a string to an integer, returning default value on failure
     * @param value String to parse
     * @param defaultValue Value to return if parsing fails (default: 0)
     * @return Parsed integer or default value
     */
    fun parseQuantity(value: String, defaultValue: Int = 0): Int {
        return value.toIntOrNull() ?: defaultValue
    }

    /**
     * Calculates total price for a given quantity and unit price
     * @param quantity Number of units
     * @param unitPrice Price per unit
     * @return Total price
     */
    fun calculateTotal(quantity: Int, unitPrice: Double): Double {
        return quantity * unitPrice
    }
}