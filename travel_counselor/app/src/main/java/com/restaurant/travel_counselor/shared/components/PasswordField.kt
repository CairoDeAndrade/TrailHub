package com.restaurant.travel_counselor.shared.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun MyPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String = ""
) {
    var isTouched = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {
            isTouched.value = true
            onValueChange(it)
        },
        singleLine = true,
        label = { Text(text = label) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusEvent {
                if (it.hasFocus) {
                    isTouched.value = true
                }
            },
        isError = isTouched.value && errorMessage.isNotBlank(),
        supportingText = {
            if (isTouched.value && errorMessage.isNotBlank()) {
                Text(text = errorMessage)
            }
        }
    )
}