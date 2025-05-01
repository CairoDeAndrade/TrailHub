package com.restaurant.travel_counselor.features.newtrip

data class NewTripData(
    val destination: String = "",
    val tripType: String = "Lazer",
    val startDate: String = "",
    val endDate: String = "",
    val budget: Double = 0.0
) {
    fun validateFields(): String {
        return when {
            destination.isBlank() -> "Destination is required"
            startDate.isBlank() -> "Start Date is required"
            endDate.isBlank() -> "End Date is required"
            !(budget > 0.0) -> "Budget is required and must be more than zero"
            else -> ""
        }
    }
}
