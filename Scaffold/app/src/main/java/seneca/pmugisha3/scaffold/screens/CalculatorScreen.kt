package seneca.pmugisha3.scaffold.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import seneca.pmugisha3.scaffold.composables.KeyPad

/**
 * Main calculator screen component that displays a text output and keypad.
 *
 * This composable function creates a complete calculator UI with:
 * - A display area showing the current calculation or result
 * - A keypad with numbers (0-9), operators (+, -, *, /), clear (C), and equals (=)
 *
 * The calculator supports basic arithmetic operations and evaluates expressions
 * when the equals button is pressed.
 */
@Composable
fun CalculatorScreen() {
    // State variable to store the calculation string (e.g., "5 + 3")
    // 'remember' keeps this value alive when the screen redraws
    // 'by' allows us to use operationString directly without .value
    var operationString by remember { mutableStateOf("") }
    // Column arranges items vertically (display on top, keypad below)

    // Track if the last operation was '=' so we can start fresh on next input
    var lastOperatorWasEqual by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()        // Take up the entire screen
            .padding(16.dp), // Add space around the edges,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display text showing the current calculation
        Text(
            text = operationString.ifEmpty { "0" },  // Show "0" if nothing entered yet
            fontSize = 24.sp,                         // Large text for readability
            modifier = Modifier.padding(bottom = 16.dp)  // Space below the display
        )

        // The calculator keypad with button click handling
        KeyPad(
            keyClicked = { key ->  // This function runs when any button is pressed
                // Update the display based on which button was pressed
                when (key) {
                    "C" -> {
                        // Clear button: reset to empty
                        operationString = ""
                        lastOperatorWasEqual = false
                    }

                    "=" -> {
                        // Equals: calculate result and mark that we just calculated
                        operationString =
                            evaluateExpression(operationString)  // Equals: calculate result
                        lastOperatorWasEqual = true
                    }
                    "+", "-", "*", "/" -> {
                        // If we just pressed '=', start fresh with the result and operator
                        if (lastOperatorWasEqual) {
                            lastOperatorWasEqual = false
                            operationString = "$operationString $key "  // Keep result, add operator with trailing space
                        } else {
                            operationString = "$operationString $key "  // Add operator with spaces
                        }
                    }
                    else -> {
                        // Numbers: append without space
                        // If we just pressed '=', start a new expression instead of appending
                        if (lastOperatorWasEqual) {
                            lastOperatorWasEqual = false
                            operationString = key // Start a new expression
                        } else {
                            operationString = if(operationString == "0") key else "$operationString$key" // direct append without space
                        }
                    }
                }
            }
        )
    }
}

/**
 * Evaluates a mathematical expression string and returns the result.
 *
 * This function parses and calculates space-separated mathematical expressions.
 * It processes operations left-to-right (no operator precedence).
 *
 * Examples:
 * - "5 + 3" returns "8"
 * - "10 - 4" returns "6"
 * - "6 * 7" returns "42"
 * - "15 / 3" returns "5"
 * - "5 + 3 * 2" returns "16" (calculates left-to-right: (5+3)*2)
 * - "10 / 0" returns "Error" (division by zero)
 * - "abc" returns "Error" (invalid input)
 *
 * @param expression A space-separated mathematical expression where tokens are
 *                   numbers and operators (+, -, *, /)
 * @return The calculated result as a string. Returns "Error" if the expression
 *         is invalid, contains division by zero, or cannot be parsed. Whole
 *         numbers are returned without decimals (e.g., "5" instead of "5.0").
 */
private fun evaluateExpression(expression: String): String {
    return try {
        // Split the expression by spaces and remove empty strings
        // "5 + 3" becomes ["5", "+", "3"]
        val tokens = expression.trim().split(" ").filter { it.isNotEmpty() }

        // Need at least 3 tokens for a valid expression (number operator number)
        if (tokens.size < 3) return expression

        // Start with the first number as our result
        var result = tokens[0].toDouble()

        // Index to track position in tokens list
        var i = 1

        // Loop through operators and operands (step by 2 each time)
        while (i < tokens.size - 1) {
            val operator = tokens[i]        // Get the operator (+, -, *, /)
            val operand = tokens[i + 1].toDouble()  // Get the next number

            // Perform the calculation based on the operator
            result = when (operator) {
                "+" -> result + operand      // Addition
                "-" -> result - operand      // Subtraction
                "*" -> result * operand      // Multiplication
                "/" -> if (operand != 0.0) result / operand else return "Error"  // Division (check for divide by zero)
                else -> return expression    // Unknown operator, return original
            }

            i += 2  // Move to next operator (skip the number we just used)
        }

        // Format the result: if it's a whole number, remove the decimal
        // 5.0 becomes "5", but 5.5 stays "5.5"
        if (result % 1.0 == 0.0) result.toInt().toString() else result.toString()

    } catch (_: Exception) {
        // If anything goes wrong (invalid number, etc.), return "Error"
        // NOTE: The error should be handled properly here. I am just returning an error string for simplicity
        "Error"
    }
}




