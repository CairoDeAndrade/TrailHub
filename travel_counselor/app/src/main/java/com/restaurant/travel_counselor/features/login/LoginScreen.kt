package com.restaurant.travel_counselor.features.login


import RequiredTextField
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.restaurant.travel_counselor.database.AppDatabase
import com.restaurant.travel_counselor.shared.components.ErrorDialog
import com.restaurant.travel_counselor.shared.components.MyPasswordField
import com.restaurant.travel_counselor.shared.enums.AppRouter
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onNavigateTo: (String) -> Unit) {
    val context = LocalContext.current
    val userDao = AppDatabase.getDatabase(context).userDao()
    val factory = LoginViewModelFactory(userDao)
    val loginViewModel: LoginViewModel = viewModel(factory = factory)

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginFields(loginViewModel, onNavigateTo)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFields(loginViewModel: LoginViewModel, onNavigateTo: (String) -> Unit) {
    val loginState = loginViewModel.uiState.collectAsState()
    val ctx = LocalContext.current

    RequiredTextField(
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

    val scope = rememberCoroutineScope()

    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = {
            if (loginViewModel.login()) {
                scope.launch {
                    val isValidUser = loginViewModel.checkCredentials()
                    if (isValidUser) {
                        Toast.makeText(ctx, "Login successful", Toast.LENGTH_SHORT).show()
                        onNavigateTo(AppRouter.MENU.route)
                    } else {
                        Toast.makeText(ctx, "Invalid username or password", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    ) {
        Text(text = "Login")
    }

    Spacer(modifier = Modifier.height(16.dp))

    val annotatedText = buildAnnotatedString {
        append("Do not have an account? ")
        pushStringAnnotation(tag = "Register", annotation = "RegisterUserScreen")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Register now")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            val annotations = annotatedText.getStringAnnotations(
                tag = "Register",
                start = offset,
                end = offset
            )
            if (annotations.isNotEmpty()) {
                onNavigateTo(annotations[0].item)
            }
        }
    )

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
    LoginScreen(onNavigateTo = {})
}