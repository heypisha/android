package seneca.pmugisha3.counterapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import seneca.pmugisha3.counterapp.models.UserViewModel

@Composable
fun LogInUI(viewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            placeholder = { Text("Enter Your Name") },
            label = { Text("Log in Info") },
            value = viewModel.loggedInName.value,
            onValueChange = { newValue ->
                viewModel.updateName(newValue)
            })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.login()
        }) {
            Text("Log In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.greetings.value.isNotBlank()) {
            Text("Welcome, ${viewModel.greetings.value} !", style = MaterialTheme.typography.headlineMedium)
        }
    }
}