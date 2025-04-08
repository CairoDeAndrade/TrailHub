package com.restaurant.travel_counselor.shared.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun RequiredNumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var isTouched by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val errorMessage = when {
        !isTouched -> null
        value.isBlank() -> "Field $label is required"
        value.toDoubleOrNull() == null -> "Enter a valid number"
        value.toDouble() <= 0.0 -> "Value must be greater than 0"
        else -> null
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            isTouched = true
            onValueChange(it)
        },
        singleLine = true,
        label = { Text(text = label) },
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusEvent {
                if (it.hasFocus) isTouched = true
            },
        isError = errorMessage != null,
        supportingText = {
            errorMessage?.let { Text(text = it) }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
