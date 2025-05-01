package com.restaurant.travel_counselor.shared.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.restaurant.travel_counselor.features.newtrip.TripType
import java.time.LocalDate

class Converters {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(epochDay: Long): LocalDate {
        return LocalDate.ofEpochDay(epochDay)
    }

    @TypeConverter
    fun fromTripType(value: TripType): String {
        return value.name
    }

    @TypeConverter
    fun toTripType(value: String): TripType {
        return TripType.valueOf(value)
    }
}