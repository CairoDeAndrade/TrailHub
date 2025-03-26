package com.restaurant.travel_counselor.shared.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun ErrorDialog(error: String, onDismissRequest: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = "Error message")
        },
        text = {
            Text(text = error)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "OK")
            }
        }
    )
}