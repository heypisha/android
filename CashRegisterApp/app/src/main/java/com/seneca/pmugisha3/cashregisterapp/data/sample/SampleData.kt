package com.seneca.pmugisha3.cashregisterapp.data.sample

import com.seneca.pmugisha3.cashregisterapp.data.model.Product
import com.seneca.pmugisha3.cashregisterapp.data.model.Purchase
import com.seneca.pmugisha3.cashregisterapp.data.model.StockItem

/**
 * SampleData for Cash Register App
 */
object SampleData {

    // Sample products using Android dessert names
    val productList = listOf(
        Product("CUPC-001", "Cupcake", 2.99),
        Product("DONT-002", "Donut", 1.99),
        Product("ECLR-003", "Eclair", 3.49),
        Product("FRYO-004", "Froyo", 4.99),
        Product("GNGR-005", "Gingerbread", 3.99),
        Product("HNYC-006", "Honeycomb", 5.49),
        Product("ICEC-007", "Ice Cream Sandwich", 4.49),
        Product("JLBN-008", "Jelly Bean", 2.49),
        Product("KTKT-009", "KitKat", 1.79),
        Product("LOLP-010", "Lollipop", 0.99)
    )

    // Initial stock with random quantities
    val initialStock = productList.map { product ->
        StockItem(product, stock = (20..100).random())
    }

    // Sample purchase history with random quantities
    val sampleHistory = productList.shuffled().take(5).map { product ->
        Purchase(product, quantity = (1..10).random())
    }
}