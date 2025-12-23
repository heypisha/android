package seneca.pmugisha3.cosmotracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto
import seneca.pmugisha3.cosmotracker.ui.components.Badge
import seneca.pmugisha3.cosmotracker.ui.components.InfoCard
import seneca.pmugisha3.cosmotracker.ui.components.TimelineItem
import seneca.pmugisha3.cosmotracker.ui.utils.BookmarkButton
import seneca.pmugisha3.cosmotracker.ui.utils.MapIcon
import seneca.pmugisha3.cosmotracker.ui.utils.ShareButton
import seneca.pmugisha3.cosmotracker.viewmodel.EventDetailUiState
import seneca.pmugisha3.cosmotracker.viewmodel.EventDetailViewModel
import seneca.pmugisha3.cosmotracker.viewmodel.ViewModelFactory
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    eventId: String,
    onNavigateBack: () -> Unit,
    viewModel: EventDetailViewModel = viewModel(factory = ViewModelFactory(eventId = eventId))
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            "EVENT DETAILS",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    BookmarkButton(
                        isBookmarked = false,
                        onClick = { /* TODO: Update ViewModel */ }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF121212),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF121212))
        ) {
            when (val state = uiState) {
                is EventDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is EventDetailUiState.Success -> {
                    EventDetailContent(event = state.event)
                }
                is EventDetailUiState.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun EventDetailContent(event: EventDto) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Badges
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Badge(
                text = event.categories.firstOrNull()?.title ?: "Unknown",
                containerColor = Color(0xFF4A148C),
                contentColor = Color.White
            )
            Badge(
                text = if (event.closed == null) "Active" else "Closed",
                containerColor = Color(0xFF1B5E20),
                contentColor = Color.White,
                hasDot = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = event.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Map/Image Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF81D4FA))
        ) {
            Badge(
                text = "LIVE TRACKING",
                containerColor = Color(0xFF1B5E20).copy(alpha = 0.8f),
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
            MapIcon(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .background(Color(0xFF4CAF50), CircleShape)
                    .padding(8.dp)
                    .size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info Cards (Coords & Detected)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val geometry = event.geometries.firstOrNull()
            val coords = geometry?.coordinates
            val lat = coords?.getOrNull(1) ?: 0.0
            val lon = coords?.getOrNull(0) ?: 0.0

            InfoCard(
                title = "COORDS",
                content = String.format(Locale.US, "%.1f° N\n%.1f° W", lat, lon),
                icon = Icons.Default.LocationOn,
                modifier = Modifier.weight(1f)
            )
            
            val dateStr = geometry?.date?.take(10) ?: "Unknown"
            InfoCard(
                title = "DETECTED",
                content = dateStr,
                icon = Icons.Default.Notifications,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Source Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn, 
                        contentDescription = null, 
                        tint = Color.Gray, 
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SOURCE", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
                Text(
                    text = event.sources.firstOrNull()?.id ?: "NASA",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Green,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "EONET System",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Situation Report
        Text(
            "Situation Report",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = event.description ?: "No detailed report available for this event at this time.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Latest Updates
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "Latest Updates",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                "Live Feed",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Green
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Timeline
        TimelineItem(title = "Just now", description = "Current status maintained.", isLatest = true)
        TimelineItem(title = "2 hours ago", description = "System update received.", isLatest = false)

        Spacer(modifier = Modifier.height(32.dp))

        // Bottom Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ShareButton(
                onClick = { /* TODO: Share functionality */ },
                modifier = Modifier
                    .weight(0.4f)
                    .background(Color(0xFF1E1E1E), RoundedCornerShape(24.dp))
            )
            
            Button(
                onClick = { /* TODO: Map functionality */ },
                modifier = Modifier.weight(0.6f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                shape = RoundedCornerShape(24.dp)
            ) {
                MapIcon(modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open on Map")
            }
        }
    }
}
