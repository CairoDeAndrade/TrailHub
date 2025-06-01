package com.restaurant.travel_counselor.shared.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomBarRouter(val route: String, val icon: ImageVector, val label: String) {
    LOGIN(AppRouter.LOGIN.route, Icons.AutoMirrored.Filled.ArrowBack, "Sign Out"),
    MENU("", Icons.Default.AirplanemodeActive, "My Trips"),
    NEW_TRIP(AppRouter.NEW_TRIP.route, Icons.Default.AddCircleOutline, "New Trip"),
}
