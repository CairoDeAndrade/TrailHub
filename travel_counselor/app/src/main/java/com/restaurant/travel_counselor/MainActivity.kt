package com.restaurant.travel_counselor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.restaurant.travel_counselor.features.login.LoginScreen
import com.restaurant.travel_counselor.features.menu.MenuScreen
import com.restaurant.travel_counselor.features.newtrip.NewTripScreen
import com.restaurant.travel_counselor.features.register.RegisterUserScreen
import com.restaurant.travel_counselor.shared.components.TopBar
import com.restaurant.travel_counselor.ui.theme.Travel_counselorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Travel_counselorTheme {
                Scaffold(
                    topBar = { TopBar() }
                ) {
                    Column(
                        modifier = Modifier.padding(it)
                    ) {
                        Boot()
                    }
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
            MenuScreen(navController) {}
        }

        composable(route = "NewTripScreen") {
            NewTripScreen(onNavigateTo = { navController.navigate(it) })
        }

    }
}
