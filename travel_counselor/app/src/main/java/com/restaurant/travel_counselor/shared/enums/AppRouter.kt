package com.restaurant.travel_counselor.shared.enums

enum class AppRouter(val route: String) {
    MAIN("MainScreen"),
    NEW_TRIP("NewTripScreen"),
    NEW_TRIP_WITH_ID("new_trip/{tripId}"),
    REGISTER("RegisterUserScreen"),
    LOGIN("LoginScreen"),
    MENU("MenuScreen")
}
