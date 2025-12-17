package seneca.pmugisha3.cosmotracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventDetailScreen(eventId: String, onNavigateBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Event Detail Screen")
        Text("Viewing Event: $eventId", modifier = Modifier.padding(top = 8.dp))
        
        Button(
            onClick = { onNavigateBack() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back to List")
        }
    }
}
