package seneca.pmugisha3.cosmotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import seneca.pmugisha3.cosmotracker.ui.CosmoTrackerApp
import seneca.pmugisha3.cosmotracker.ui.theme.CosmoTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CosmoTrackerTheme {
                CosmoTrackerApp()
            }
        }
    }
}