package com.restaurant.travel_counselor.features.newtrip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.restaurant.travel_counselor.entities.Trip

@Composable
fun TripCard(trip: Trip, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Destination: ${trip.destination}")
            Text(text = "Trip Type: ${trip.tripType}")
            Text(text = "Start Date: ${trip.startDate}")
            Text(text = "End Date: ${trip.endDate}")
            Text(text = "Budget: ${trip.budget}")
        }
    }
}

