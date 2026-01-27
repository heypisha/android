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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import seneca.pmugisha3.cosmotracker.viewmodel.HomeUiState
import seneca.pmugisha3.cosmotracker.viewmodel.HomeViewModel
import seneca.pmugisha3.cosmotracker.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
  onNavigateToEvents: () -> Unit = {},
  onNavigateToHazardNearMe: () -> Unit = {}
) {
  val context = LocalContext.current
  val viewModel: HomeViewModel = viewModel(
    factory = ViewModelFactory(context.applicationContext as android.app.Application)
  )

  val apodState by viewModel.uiState.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Dashboard of the Universe",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    when (val state = apodState) {
      is HomeUiState.Loading -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }

      is HomeUiState.Success -> {
        Column(
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = state.apod.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
          )

          AsyncImage(
            model = state.apod.url,
            contentDescription = state.apod.title,
            modifier = Modifier
              .fillMaxWidth()
              .height(300.dp),
            contentScale = ContentScale.Crop
          )

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = state.apod.date,
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!state.apod.copyright.isNullOrBlank()) {
              Text(
                text = "© ${state.apod.copyright}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = state.apod.explanation,
            fontSize = 14.sp,
            lineHeight = 20.sp
          )
        }
      }

      is HomeUiState.Error -> {
        Column(
          modifier = Modifier.fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = "Error: ${state.message}",
            color = MaterialTheme.colorScheme.error
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(onClick = { viewModel.loadApod() }) {
            Text("Retry")
          }
        }
      }
    }
  }
}