package seneca.pmugisha3.scaffold.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import seneca.pmugisha3.scaffold.screens.CounterScreen
import seneca.pmugisha3.scaffold.screens.LogInScreen
import seneca.pmugisha3.scaffold.viewmodels.CounterViewModel
import seneca.pmugisha3.scaffold.viewmodels.UserViewModel
import seneca.pmugisha3.scaffold.screens.CalculatorScreen
import seneca.pmugisha3.scaffold.viewmodels.AppThemeViewModel

@Composable
fun MyNavHost(
    navController: NavHostController,
    counterViewModel: CounterViewModel,
    userViewModel: UserViewModel,
    themeViewModel: AppThemeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavItems.calculator.path
    ) {

        composable(route = NavItems.calculator.path) { CalculatorScreen() }
        composable(route = NavItems.counter.path) { CounterScreen(counterViewModel) }
        composable(route = NavItems.logIn.path ) { LogInScreen(userViewModel, themeViewModel) }

    }
}