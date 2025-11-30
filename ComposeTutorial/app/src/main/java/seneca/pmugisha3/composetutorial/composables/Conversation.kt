package seneca.pmugisha3.composetutorial.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import seneca.pmugisha3.composetutorial.ui.theme.ComposeTutorialTheme

@Composable
fun Conversation(messages: List<Message>){
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation(){
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}