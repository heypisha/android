package com.seneca.pmugisha3.cashregisterapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seneca.pmugisha3.cashregisterapp.ui.screens.CashRegisterScreen
import com.seneca.pmugisha3.cashregisterapp.ui.screens.PurchaseHistoryScreen

/**
 * Main navigation graph for the app
 * @param navController Navigation controller for handling navigation
 * @param onNavigateToHistory Callback to navigate to history screen
 * @param onBackClicked Callback to pop the back stack
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    onNavigateToHistory: () -> Unit,
    onBackClicked: () -> Unit
) {
    NavHost(navController = navController, startDestination = NavItems.CashRegister.path) {
        // Cash register screen
        composable(route = NavItems.CashRegister.path) {
            CashRegisterScreen(
                onNavigateToHistory = onNavigateToHistory // Pass callback to screen
            )
        }
        // Purchase history screen
        composable(route = NavItems.History.path) {
            PurchaseHistoryScreen(
                onBackClicked = onBackClicked // Pass callback to screen
            )
        }
    }
}