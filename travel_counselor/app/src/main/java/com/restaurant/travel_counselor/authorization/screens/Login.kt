package com.restaurant.travel_counselor.authorization.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Login(
    onNavigateTo: (String) -> Unit,
    function: () -> Unit
): Unit {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Magenta),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleLarge
        )
        Button(onClick = { onNavigateTo("Register") }) {
            Text(
                text = "Register"
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun LoginPreview() {
    MaterialTheme {
        Login(onNavigateTo = {}, function = { })
    }
}