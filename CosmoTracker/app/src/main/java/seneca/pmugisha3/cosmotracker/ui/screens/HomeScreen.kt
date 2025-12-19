package seneca.pmugisha3.cosmotracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import seneca.pmugisha3.cosmotracker.viewmodel.HomeUiState
import seneca.pmugisha3.cosmotracker.viewmodel.HomeViewModel
import seneca.pmugisha3.cosmotracker.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
  viewModel: HomeViewModel = viewModel(factory = ViewModelFactory())
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  Box(modifier = Modifier.fillMaxSize()) {
    when (val state = uiState) {
      is HomeUiState.Loading -> {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }

      is HomeUiState.Success -> {
        val apod = state.apod
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ) {
          Text(
            text = apod.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
          )

          AsyncImage(
            model = apod.url,
            contentDescription = apod.title,
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 8.dp),
            contentScale = ContentScale.FillWidth
          )

          Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
              text = apod.date,
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.secondary,
              modifier = Modifier.padding(bottom = 8.dp)
            )

            if (!apod.copyright.isNullOrEmpty()) {
              Text(
                text = " (Copyright: ${apod.copyright})",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = apod.explanation,
            style = MaterialTheme.typography.bodyLarge
          )
        }
      }

      is HomeUiState.Error -> {
        Column(
          modifier = Modifier.align(Alignment.Center),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(text = "Error: ${state.message}", color = MaterialTheme.colorScheme.error)
          Button(onClick = { viewModel.getApod() }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Retry")
          }
        }
      }
    }
  }
}
