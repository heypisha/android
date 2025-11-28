package seneca.pmugisha3.scaffold.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import seneca.pmugisha3.scaffold.viewmodels.AppThemeViewModel
import seneca.pmugisha3.scaffold.viewmodels.UserViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun LogInScreen(viewModel: UserViewModel, themeViewModel: AppThemeViewModel) {
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
            value = viewModel.loggedInName,
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

        if (viewModel.greetings.isNotBlank()) {
            Text(
                viewModel.greetings,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Switch(
                checked = themeViewModel.isDarkMode,
                onCheckedChange = {
                    themeViewModel.updateThemeMode(it)
                })
        }
    }
}


