package com.restaurant.travel_counselor.features.menu


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.restaurant.travel_counselor.database.AppDatabase
import com.restaurant.travel_counselor.entities.User
import com.restaurant.travel_counselor.features.newtrip.NewTripViewModel
import com.restaurant.travel_counselor.features.newtrip.NewTripViewModelFactory
import com.restaurant.travel_counselor.features.newtrip.TripCard
import com.restaurant.travel_counselor.shared.enums.BottomBarRouter


@Composable
fun MenuScreen(navController: NavHostController, function: () -> Unit) {
    val context = LocalContext.current
    val tripDao = AppDatabase.getDatabase(context).tripDao()
    val factory = NewTripViewModelFactory(tripDao)
    val viewModel: NewTripViewModel = viewModel(factory = factory)
    val tripList by viewModel.tripList.collectAsState()

    Scaffold(
        bottomBar = {
            val backStack = navController.currentBackStackEntryAsState()
            val currentDestination = backStack.value?.destination

            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                BottomBarRouter.entries.forEach { screen ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) navController.navigate(screen.route)
                        },
                        icon = {
                            if (isSelected) {
                                androidx.compose.material3.Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.label,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
                                            shape = CircleShape
                                        )
                                        .padding(6.dp)
                                        .size(24.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.label,
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        },
                        label = {
                            Text(
                                screen.label,
                                color = if (isSelected)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(tripList) { trip ->
                TripCard(trip = trip) {
                    navController.navigate("new_trip/${trip.id}")
                }
            }
        }
    }
}

@Composable
fun UserCard(user: User, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.name, style = MaterialTheme.typography.titleMedium)
            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    val navController = rememberNavController()
    MenuScreen(navController = navController) {}
}
