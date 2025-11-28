package seneca.pmugisha3.scaffold.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face

sealed class NavItems {
    object calculator : Item("calc", "Calculator", Icons.Default.AccountBox)
    object counter : Item("counter", "Counter", Icons.Default.Face)
    object logIn : Item("login","logIn", Icons.Default.AccountCircle)
}