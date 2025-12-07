package com.seneca.pmugisha3.cashregisterapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seneca.pmugisha3.cashregisterapp.ui.screens.CashRegisterScreen
import com.seneca.pmugisha3.cashregisterapp.ui.screens.PurchaseHistoryScreen
import com.seneca.pmugisha3.cashregisterapp.viewmodel.CashRegisterViewModel

/**
 * Main navigation graph for the app
 * @param navController Navigation controller for handling navigation
 */
@Composable
fun NavGraph(navController: NavHostController) {
    // Create a shared ViewModel instance at navigation level
    val sharedViewModel: CashRegisterViewModel = viewModel()
    NavHost(navController = navController, startDestination = NavItems.CashRegister.path) {
        composable(route = NavItems.CashRegister.path) {
            CashRegisterScreen(onNavigateToHistory = { navController.navigate(NavItems.History.path) })
        }
        composable(route = NavItems.History.path) {
            PurchaseHistoryScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}