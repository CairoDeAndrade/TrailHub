package com.restaurant.travel_counselor.features.newtrip

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewTripViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewTripData())
    val uiState: StateFlow<NewTripData> = _uiState

    fun onDestinationChange(newDestination: String) {
        _uiState.value = _uiState.value.copy(destination = newDestination)
    }

    fun onTripTypeChange(newTripType: String) {
        _uiState.value = _uiState.value.copy(tripType = newTripType)
    }

    fun onStartDateChange(newStartDate: String) {
        _uiState.value = _uiState.value.copy(startDate = newStartDate)
    }

    fun onEndDateChange(newEndDate: String) {
        _uiState.value = _uiState.value.copy(endDate = newEndDate)
    }

    fun onBudgetChange(newBudget: String) {
        _uiState.value = _uiState.value.copy(budget = newBudget)
    }

    fun saveTrip(): Boolean {
        val error = _uiState.value.validateFields()
        return if (error.isBlank()) true else false
    }
}
