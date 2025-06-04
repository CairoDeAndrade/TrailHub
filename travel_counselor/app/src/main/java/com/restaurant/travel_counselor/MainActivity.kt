package com.restaurant.travel_counselor

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.restaurant.travel_counselor.features.itnerary.ItinerarySuggestionScreen
import com.restaurant.travel_counselor.features.login.LoginScreen
import com.restaurant.travel_counselor.features.menu.MenuScreen
import com.restaurant.travel_counselor.features.newtrip.NewTripScreen
import com.restaurant.travel_counselor.features.register.RegisterUserScreen
import com.restaurant.travel_counselor.shared.components.TopBar
import com.restaurant.travel_counselor.shared.enums.AppRouter
import com.restaurant.travel_counselor.ui.theme.Travel_counselorTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Boot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRouter.LOGIN.route    ) {
        composable(route = AppRouter.LOGIN.route) {
            LoginScreen(onNavigateTo = {
                navController.navigate(it)
            })
        }

        composable(route = AppRouter.REGISTER.route) {
            RegisterUserScreen(onNavigateTo = {
                navController.navigate(it)
            })
        }

        composable(route = AppRouter.MENU.route) {
            MenuScreen(navController) {}
        }

        composable(route = AppRouter.NEW_TRIP.route) {
            NewTripScreen(
                onNavigateTo = { navController.navigate(it) },
                tripId = null
            )
        }

        composable("new_trip/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")?.toIntOrNull()
            if (tripId != null) {
                NewTripScreen(onNavigateTo = { navController.navigate(it) }, tripId = tripId)
            }
        }

        composable("itinerary/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")?.toIntOrNull()
            if (tripId != null) {
                ItinerarySuggestionScreen(
                    tripId = tripId,
                    onNavigateTo = { navController.navigate(it) }
                )
            }
        }

    }
}
