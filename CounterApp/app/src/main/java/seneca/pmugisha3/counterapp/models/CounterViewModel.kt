package seneca.pmugisha3.counterapp.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CounterViewModel: ViewModel() {

    var myCounter by mutableStateOf(0)
    var counter = mutableStateOf(10)
        private set
    // will enforce the UI to rebuild after each update

    fun increase(){
        counter.value++
        myCounter++
    }

    fun decrease(){
        counter.value--
    }
}