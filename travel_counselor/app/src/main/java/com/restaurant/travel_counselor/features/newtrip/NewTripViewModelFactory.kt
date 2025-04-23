package com.restaurant.travel_counselor.features.newtrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.restaurant.travel_counselor.dao.TripDao

class NewTripViewModelFactory(
    private val tripDao: TripDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTripViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewTripViewModel(tripDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
