package com.restaurant.travel_counselor.features.menu


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MenuScreen(navController: NavHostController, function: () -> Unit) {
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val backStack = navController.currentBackStackEntryAsState()
                val currentDestination = backStack.value?.destination
                BottomNavigationItem(
                    selected =
                    currentDestination?.hierarchy?.any {
                        it.route == "MainScreen"
                    } == true, onClick = { navController.navigate("LoginScreen") },
                    icon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Main Screen")
                    }
                )

                BottomNavigationItem(
                    selected =
                    currentDestination?.hierarchy?.any {
                        it.route == "MainScreen"
                    } == true, onClick = { navController.navigate("NewTripScreen") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AirplanemodeActive,
                            contentDescription = "New Trip"
                        )
                    }
                )

                BottomNavigationItem(
                    selected =
                    currentDestination?.hierarchy?.any {
                        it.route == "MainScreen"
                    } == true, onClick = { navController.navigate("RegisterUserScreen") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Screen"
                        )
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Menu Screen")
        }
    }
}