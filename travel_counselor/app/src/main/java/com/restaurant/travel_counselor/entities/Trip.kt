package com.restaurant.travel_counselor.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.restaurant.travel_counselor.features.newtrip.TripType
import java.time.LocalDate

@Entity(tableName = "trip")
data class Trip(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destination: String,
    val tripType: TripType,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val budget: Double
)

