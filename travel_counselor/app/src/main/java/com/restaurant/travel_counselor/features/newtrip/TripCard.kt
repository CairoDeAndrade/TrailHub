package com.restaurant.travel_counselor.features.newtrip

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.restaurant.travel_counselor.entities.Trip
import com.restaurant.travel_counselor.shared.utils.DateUtils

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TripCard(trip: Trip, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Destination: ${trip.destination}")
            Text(text = "Trip Type: ${trip.tripType}")
            Text(text = "Start Date: ${DateUtils.formatDate(trip.startDate)}")
            Text(text = "End Date: ${DateUtils.formatDate(trip.endDate)}")
            Text(text = "Budget: R$ ${"%.2f".format(trip.budget)}")
        }
    }
}

