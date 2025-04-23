package com.restaurant.travel_counselor.shared.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomBarRouter(val route: String, val icon: ImageVector, val label: String) {
    MAIN("MainScreen", Icons.Default.Home, "Início"),
    NEW_TRIP("NewTripScreen", Icons.Default.AirplanemodeActive, "Nova Viagem"),
    PROFILE("RegisterUserScreen", Icons.Default.Person, "Perfil")
}
