package com.restaurant.travel_counselor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.restaurant.travel_counselor.features.login.LoginScreen
import com.restaurant.travel_counselor.features.menu.MenuScreen
import com.restaurant.travel_counselor.features.register.RegisterUserScreen
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
    NavHost(
        navController = navController,
        startDestination = "LoginScreen"
    ) {
        composable(route = "LoginScreen") {
            LoginScreen(onNavigateTo = {
                navController.navigate(it)
            })
        }

        composable(route = "RegisterUserScreen") {
            RegisterUserScreen(onNavigateTo = {
                navController.navigate(it)
            })
        }

        composable(route = "MenuScreen") {
            MenuScreen {}
        }
    }
}
