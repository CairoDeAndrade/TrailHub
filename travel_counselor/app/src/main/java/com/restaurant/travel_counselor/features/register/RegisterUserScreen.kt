package com.restaurant.travel_counselor.features.register

import RequiredTextField
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.restaurant.travel_counselor.database.AppDatabase
import com.restaurant.travel_counselor.shared.components.ErrorDialog
import com.restaurant.travel_counselor.shared.components.MyPasswordField


@Composable
fun RegisterUserScreen(
    onNavigateTo: (String) -> Unit
) {
    val context = LocalContext.current
    val userDao = AppDatabase.getDatabase(context).userDao()
    val factory = RegisterUserViewModelFactory(userDao)
    val registerUserViewModel: RegisterUserViewModel = viewModel(factory = factory)

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            RegisterUserFields(registerUserViewModel, onNavigateTo)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserFields(
    registerUserViewModel: RegisterUserViewModel,
    onNavigateTo: (String) -> Unit
) {
    val registerUser = registerUserViewModel.uiState.collectAsState()
    val ctx = LocalContext.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "User Registration",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        RequiredTextField(
            label = "Username",
            value = registerUser.value.user,
            onValueChange = registerUserViewModel::onUserChange
        )
        RequiredTextField(
            label = "Email",
            value = registerUser.value.email,
            onValueChange = registerUserViewModel::onEmailChange
        )
        MyPasswordField(
            label = "Password",
            value = registerUser.value.password,
            errorMessage = registerUser.value.validatePassword(),
            onValueChange = registerUserViewModel::onPasswordChange
        )
        MyPasswordField(
            label = "Confirm Password",
            value = registerUser.value.confirmPassword,
            errorMessage = registerUser.value.validateConfirmPassword(),
            onValueChange = registerUserViewModel::onConfirmPassword
        )

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                registerUserViewModel.register()
            }
        ) {
            Text(text = "Register")
        }

        if (registerUser.value.errorMessage.isNotBlank()) {
            ErrorDialog(
                error = registerUser.value.errorMessage,
                onDismissRequest = {
                    registerUserViewModel.cleanDisplayValues()
                }
            )
        }

        if (registerUser.value.isSaved) {
            Toast.makeText(ctx, "User registered successfully!", Toast.LENGTH_SHORT).show()
            registerUserViewModel.cleanDisplayValues()
            onNavigateTo("LoginScreen")
        }

        ClickableText(
            text = buildAnnotatedString {
                append("Already have an account? ")


                pushStringAnnotation(tag = "login", annotation = "LoginScreen")
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF1E88E5),
                        textDecoration = TextDecoration.Underline,
                        fontSize = 16.sp
                    )
                ) {
                    append("Login")
                }
                pop()
            },
            onClick = { offset ->
                val annotations = buildAnnotatedString {
                    append("Already have an account? ")
                    pushStringAnnotation(tag = "login", annotation = "LoginScreen")
                    append("Login")
                    pop()
                }.getStringAnnotations(tag = "login", start = offset, end = offset)

                if (annotations.isNotEmpty()) {
                    onNavigateTo(annotations[0].item)
                }
            },
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true, device = "id:Galaxy Nexus")
fun RegisterUserPreview() {
    RegisterUserScreen(onNavigateTo = {})
}