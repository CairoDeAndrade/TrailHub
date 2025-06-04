package com.restaurant.travel_counselor.features.menu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.restaurant.travel_counselor.database.AppDatabase
import com.restaurant.travel_counselor.features.newtrip.NewTripViewModel
import com.restaurant.travel_counselor.features.newtrip.NewTripViewModelFactory
import com.restaurant.travel_counselor.features.newtrip.TripCard
import com.restaurant.travel_counselor.shared.enums.BottomBarRouter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MenuScreen(navController: NavHostController, function: () -> Unit) {
    val context = LocalContext.current
    val tripDao = AppDatabase.getDatabase(context).tripDao()
    val factory = NewTripViewModelFactory(tripDao)
    val viewModel: NewTripViewModel = viewModel(factory = factory)
    val tripList by viewModel.tripList.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedTripId by remember { mutableStateOf<Int?>(null) }

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
                                Icon(
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
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(tripList) { trip ->
                TripCard(
                    trip = trip,
                    onEditClick = {
                        navController.navigate("new_trip/${trip.id}")
                    },
                    onItineraryClick = {
                        navController.navigate("itinerary/${trip.id}")
                    },
                    onDeleteClick = {
                        selectedTripId = trip.id
                        showDialog = true
                    }
                )
            }
        }

        // Diálogo de confirmação
        if (showDialog && selectedTripId != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmar exclusão") },
                text = { Text("Deseja realmente excluir esta viagem?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.softDeleteTrip(selectedTripId!!)
                        showDialog = false
                        selectedTripId = null
                    }) {
                        Text("Sim")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        selectedTripId = null
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    val navController = rememberNavController()
    MenuScreen(navController = navController) {}
}
