package com.restaurant.travel_counselor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.restaurant.travel_counselor.authorization.login.LoginScreen
import com.restaurant.travel_counselor.authorization.register.RegisterUserScreen
import com.restaurant.travel_counselor.ui.theme.Travel_counselorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Travel_counselorTheme {
                Surface {
                    Boot()
                }
            }
        }
    }
}

@Composable
fun Boot() {
    val navController = rememberNavController()
    RegisterUserScreen()
}

@Preview(showBackground = true)
@Composable
fun BootPreview() {
    Travel_counselorTheme {
        LoginScreen()
    }
}