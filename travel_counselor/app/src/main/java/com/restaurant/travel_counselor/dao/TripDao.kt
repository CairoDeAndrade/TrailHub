package com.restaurant.travel_counselor.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.restaurant.travel_counselor.entities.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrip(trip: Trip): Long

    @Update
    suspend fun updateTrip(trip: Trip)

    @Query("UPDATE Trip SET itinerary = :itinerary WHERE id = :id")
    suspend fun updateTripItineraryById(id: Int, itinerary: String)

    @Delete
    suspend fun delete(trip: Trip)

    @Query("SELECT * FROM Trip WHERE id = :id")
    suspend fun findById(id: Int): Trip?

    @Query("SELECT * FROM Trip ORDER BY startDate DESC")
    fun findAll(): Flow<List<Trip>>

    @Query("SELECT * FROM Trip WHERE active = 1 ORDER BY startDate DESC")
    fun findActiveTrips(): Flow<List<Trip>>

    @Query("UPDATE Trip SET active = 0 WHERE id = :id")
    suspend fun softDeleteTripById(id: Int)
}

