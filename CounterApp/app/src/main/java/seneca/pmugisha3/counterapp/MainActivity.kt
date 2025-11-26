package seneca.pmugisha3.counterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import seneca.pmugisha3.counterapp.models.CounterViewModel
import seneca.pmugisha3.counterapp.models.UserViewModel
import seneca.pmugisha3.counterapp.ui.theme.CounterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val myVM = CounterViewModel()
            val userVM = UserViewModel()
            CounterAppTheme {
                Column {
                    UserInterface(myVM)

                    Spacer(modifier = Modifier.height(10.dp))

                    LogInUI(userVM)
                }
            }
        }
    }
}
