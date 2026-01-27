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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import seneca.pmugisha3.cosmotracker.viewmodel.EventDetailUiState
import seneca.pmugisha3.cosmotracker.viewmodel.EventDetailViewModel
import seneca.pmugisha3.cosmotracker.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
  eventId: String,
  onNavigateBack: () -> Unit = {}
) {
  val context = LocalContext.current
  val viewModel: EventDetailViewModel = viewModel(
    factory = ViewModelFactory(
      application = context.applicationContext as android.app.Application,
      eventId = eventId
    )
  )

  val uiState by viewModel.uiState.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Event Details") },
        navigationIcon = {
          IconButton(onClick = onNavigateBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        }
      )
    }
  ) { paddingValues ->
    when (val state = uiState) {
      is EventDetailUiState.Loading -> {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }

      is EventDetailUiState.Success -> {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ) {
          // Title
          Text(
            text = state.event.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
          )

          // Category
          if (state.event.categories.isNotEmpty()) {
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
              colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
              )
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Category:",
                  fontWeight = FontWeight.SemiBold,
                  modifier = Modifier.padding(end = 8.dp)
                )
                state.event.categories.first().title?.let {
                  Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                  )
                }
              }
            }
          }

          // Description
          if (!state.event.description.isNullOrBlank()) {
            Text(
              text = "Description",
              fontSize = 20.sp,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
              text = state.event.description,
              fontSize = 16.sp,
              lineHeight = 24.sp,
              modifier = Modifier.padding(bottom = 16.dp)
            )
          }

          // Location Info
          if (state.event.geometries.isNotEmpty()) {
            val firstGeometry = state.event.geometries.first()
            val coords = firstGeometry.coordinates

            Text(
              text = "Location",
              fontSize = 20.sp,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
            ) {
              Column(
                modifier = Modifier.padding(16.dp)
              ) {
                coords?.size?.let {
                  if (it >= 2) {
                    Row(
                      modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                      Text(
                        text = "Latitude: ",
                        fontWeight = FontWeight.SemiBold
                      )
                      Text(text = "${coords[1]}°")
                    }
                    Row {
                      Text(
                        text = "Longitude: ",
                        fontWeight = FontWeight.SemiBold
                      )
                      Text(text = "${coords[0]}°")
                    }
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                  Text(
                    text = "Date: ",
                    fontWeight = FontWeight.SemiBold
                  )
                  firstGeometry.date?.let { Text(text = it) }
                }
              }
            }
          }

          // Status
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (state.event.closed != null) {
                MaterialTheme.colorScheme.surfaceVariant
              } else {
                MaterialTheme.colorScheme.errorContainer
              }
            )
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Status:",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(end = 8.dp)
              )
              Text(
                text = if (state.event.closed != null) "Closed" else "Active",
                fontWeight = FontWeight.Medium,
                color = if (state.event.closed != null) {
                  MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                  MaterialTheme.colorScheme.error
                }
              )
            }
          }

          // Sources
          if (state.event.sources.isNotEmpty()) {
            Text(
              text = "Sources",
              fontSize = 20.sp,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(bottom = 8.dp)
            )

            state.event.sources.forEach { source ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 8.dp)
              ) {
                Column(
                  modifier = Modifier.padding(12.dp)
                ) {
                  source.id?.let {
                    Text(
                      text = it,
                      fontWeight = FontWeight.SemiBold,
                      modifier = Modifier.padding(bottom = 4.dp)
                    )
                  }
                  source.url?.let {
                    Text(
                      text = it,
                      fontSize = 14.sp,
                      color = MaterialTheme.colorScheme.primary
                    )
                  }
                }
              }
            }
          }
        }
      }

      is EventDetailUiState.Error -> {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = "Error: ${state.message}",
            color = MaterialTheme.colorScheme.error,
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp)
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(onClick = { viewModel.getEventDetail() }) {
            Text("Retry")
          }
        }
      }
    }
  }
}