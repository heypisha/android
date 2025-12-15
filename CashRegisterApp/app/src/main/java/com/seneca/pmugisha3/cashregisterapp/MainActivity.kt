package com.seneca.pmugisha3.cashregisterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.seneca.pmugisha3.cashregisterapp.ui.navigation.NavGraph
import com.seneca.pmugisha3.cashregisterapp.ui.navigation.NavItems
import com.seneca.pmugisha3.cashregisterapp.ui.theme.CashRegisterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashRegisterAppTheme {
                CashRegisterAppNavigation()
            }
        }
    }
}

@Composable
fun CashRegisterAppNavigation() {
    val navController = rememberNavController()

    NavGraph(
        navController = navController,
        onNavigateToHistory = {
            navController.navigate(NavItems.History.path)
        },
        onBackClicked = {
            navController.popBackStack()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CashRegisterAppTheme {
        CashRegisterAppNavigation()
    }
}