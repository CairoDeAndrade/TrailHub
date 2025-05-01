package com.restaurant.travel_counselor.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Trip(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destination: String,
    val tripType: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val budget: Double
)

