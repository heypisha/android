package seneca.pmugisha3.cosmotracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventsScreen(onEventClick: (eventId: String) -> Unit, onNavigateBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Events Screen")
        Button(
            onClick = { onEventClick("test-event-123") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Simulate Click: Event 123")
        }
    }
}
