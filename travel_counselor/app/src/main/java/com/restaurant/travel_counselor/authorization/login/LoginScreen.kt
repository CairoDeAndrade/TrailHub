package com.restaurant.travel_counselor.authorization.login


import MyTextField
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.restaurant.travel_counselor.shared.components.ErrorDialog
import com.restaurant.travel_counselor.shared.components.MyPasswordField

@Composable
fun LoginScreen() {
    val loginViewModel: LoginViewModel = viewModel()

    Scaffold(
        topBar = { LoginTopBar() }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginFields(loginViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "TrailHub",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Flight,
                contentDescription = "Logo",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(32.dp)
            )
        },
        actions = {
            IconButton(onClick = { /* TODO: Ação do menu */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFields(loginViewModel: LoginViewModel) {
    val loginState = loginViewModel.uiState.collectAsState()
    val ctx = LocalContext.current

    MyTextField(
        label = "Username",
        value = loginState.value.username,
        onValueChange = loginViewModel::onUsernameChange
    )

    MyPasswordField(
        label = "Password",
        value = loginState.value.password,
        errorMessage = loginState.value.validatePassword(),
        onValueChange = loginViewModel::onPasswordChange
    )

    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = {
            if (loginViewModel.login()) {
                Toast.makeText(ctx, "Login successful", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Text(text = "Login")
    }

    if (loginState.value.errorMessage.isNotBlank()) {
        ErrorDialog(
            error = loginState.value.errorMessage,
            onDismissRequest = loginViewModel::clearErrorMessage
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true, device = "id:Galaxy Nexus")
fun LoginScreenPreview() {
    LoginScreen()
}