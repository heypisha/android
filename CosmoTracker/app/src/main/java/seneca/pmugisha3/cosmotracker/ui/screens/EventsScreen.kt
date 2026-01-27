package seneca.pmugisha3.cosmotracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto
import seneca.pmugisha3.cosmotracker.viewmodel.EventsUiState
import seneca.pmugisha3.cosmotracker.viewmodel.EventsViewModel
import seneca.pmugisha3.cosmotracker.viewmodel.ViewModelFactory

@Composable
fun EventsScreen(
  onEventClick: (String) -> Unit = {},
  onNavigateBack: () -> Unit = {}
) {
  val context = LocalContext.current
  val viewModel: EventsViewModel = viewModel(
    factory = ViewModelFactory(context.applicationContext as android.app.Application)
  )

  val eventsState by viewModel.eventsState.collectAsState()
  val favoriteMap by viewModel.isFavorite.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = "Detected Events",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    when (val state = eventsState) {
      is EventsUiState.Loading -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }

      is EventsUiState.Success -> {
        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(state.events) { event ->
            EventCard(
              event = event,
              onClick = { onEventClick(event.id) },
              isFavorite = favoriteMap[event.id] ?: false,
              onFavoriteToggle = { viewModel.toggleFavorite(event) }
            )
          }
        }
      }

      is EventsUiState.Error -> {
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
          Button(onClick = { viewModel.loadEvents() }) {
            Text("Retry")
          }
        }
      }
    }
  }
}

@Composable
fun EventCard(
  event: EventDto,
  onClick: () -> Unit,
  isFavorite: Boolean = false,
  onFavoriteToggle: () -> Unit = {}
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = event.title,
          fontSize = 18.sp,
          fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (event.categories.isNotEmpty()) {
          event.categories.first().title?.let {
            Text(
              text = it,
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.primary
            )
          }
        }

        if (!event.description.isNullOrBlank()) {
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = event.description,
            fontSize = 14.sp,
            maxLines = 2
          )
        }
      }

      IconButton(onClick = onFavoriteToggle) {
        Icon(
          imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
          contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
          tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}