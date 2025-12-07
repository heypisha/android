package com.seneca.pmugisha3.cashregisterapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info

sealed class NavItems {
    object CashRegister: NavItem("cash_register","Cash Register", Icons.Default.Home)
    object History: NavItem("history","History", Icons.Default.Info)
}