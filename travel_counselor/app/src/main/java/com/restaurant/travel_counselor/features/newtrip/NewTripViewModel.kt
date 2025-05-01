package com.restaurant.travel_counselor.features.newtrip

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restaurant.travel_counselor.dao.TripDao
import com.restaurant.travel_counselor.entities.Trip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewTripViewModel(
    private val tripDao: TripDao
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val uiFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val _uiState = MutableStateFlow(NewTripData())
    val uiState: StateFlow<NewTripData> = _uiState

    val tripList: StateFlow<List<Trip>> = tripDao.findAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveTrip(onSuccess: () -> Unit, onError: () -> Unit, id: Int) {
        val tripData = _uiState.value
        if (tripData.validateFields().isNotBlank()) {
            onError()
            return
        }

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val trip = Trip(
            id = id,
            destination = tripData.destination,
            tripType = tripData.tripType,
            startDate = LocalDate.parse(tripData.startDate, formatter),
            endDate = LocalDate.parse(tripData.endDate, formatter),
            budget = tripData.budget
        )

        viewModelScope.launch {
            if (id != 0) {
                tripDao.updateTrip(trip)
            } else {
                tripDao.insertTrip(trip)
            }
            onSuccess()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadTripById(id: Int) {
        viewModelScope.launch {
            val trip = tripDao.findById(id)
            trip?.let {
                _uiState.value = NewTripData(
                    destination = it.destination,
                    tripType = it.tripType,
                    startDate = it.startDate.format(uiFormatter),
                    endDate = it.endDate.format(uiFormatter),
                    budget = it.budget
                )
            }
        }
    }
}
