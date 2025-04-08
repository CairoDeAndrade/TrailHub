package com.restaurant.travel_counselor.features.newtrip

data class NewTripData(
    val destination: String = "",
    val tripType: String = "Lazer",
    val startDate: String = "",
    val endDate: String = "",
    val budget: String = ""
) {
    fun validateFields(): String {
        return when {
            destination.isBlank() -> "Destination is required"
            startDate.isBlank() -> "Start Date is required"
            endDate.isBlank() -> "End Date is required"
            budget.isBlank() -> "Budget is required"
            else -> ""
        }
    }
}
