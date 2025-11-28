package seneca.pmugisha3.scaffold.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel for managing counter state and operations.
 *
 * Maintains two counter values and provides increment/decrement operations.
 * This ViewModel survives configuration changes (like screen rotation).
 */
class CounterViewModel : ViewModel() {

    // Primary counter starting at 0
    // Using 'by' delegation for cleaner syntax (no .value needed)
    var myCounter by mutableIntStateOf(0)
        private set

    // Secondary counter starting at 10
    // Private setter prevents external modification
    var counter by mutableIntStateOf(10)
        private set

    /**
     * Increments both counters by 1.
     */
    fun increase() {
        counter++
        myCounter++
    }

    /**
     * Decrements the main counter by 1.
     */
    fun decrease() {
        counter--
    }

    /**
     * Resets both counters to their initial values.
     */
    fun reset() {
        myCounter = 0
        counter = 10
    }
}