package seneca.pmugisha3.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nameFromMainActivity = intent.getStringExtra("NewName")
            val names = listOf("Mary", "John", "Lee")
            val newNames = names + nameFromMainActivity
            ListComposable(newNames)
        }
    }
}

@Composable
fun ListComposable(list: List<String?>) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(list) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.summer),
                        contentDescription = "summer"
                    )
                    Spacer(Modifier.width(10.dp))

                    if (it != null) {
                        Text(text = it, fontSize = 20.sp)
                    }
                }
            }

        }
    }


}
