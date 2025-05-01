package com.restaurant.travel_counselor.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.restaurant.travel_counselor.dao.TripDao
import com.restaurant.travel_counselor.dao.UserDao
import com.restaurant.travel_counselor.entities.Trip
import com.restaurant.travel_counselor.entities.User
import com.restaurant.travel_counselor.shared.utils.Converters

@Database(
    entities = [User::class, Trip::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        private val V2_CREATING_TABLES_USER_AND_TRIP = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                            CREATE TABLE IF NOT EXISTS User (
                                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                                name TEXT NOT NULL,
                                password TEXT NOT NULL,
                                email TEXT NOT NULL
                            )
                        """
                )

                db.execSQL(
                    """
                            INSERT INTO User (name, password, email) 
                            VALUES ('teste', '123456', 'teste@email.com')
                        """
                )

                db.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS Trip (
                            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                            destination TEXT NOT NULL,
                            tripType TEXT NOT NULL,
                            startDate TEXT NOT NULL,
                            endDate TEXT NOT NULL,
                            budget TEXT NOT NULL
                        )
                    """
                )
            }
        }

        private val V3_ALTER_DATE_FIELDS = object : Migration(2, 3) {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE IF EXISTS Trip")

                db.execSQL(
                    """
            CREATE TABLE trip (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                destination TEXT NOT NULL,
                tripType TEXT NOT NULL,
                startDate INTEGER NOT NULL,
                endDate INTEGER NOT NULL,
                budget REAL NOT NULL
            )
            """
                )

                val startEpochDay = java.time.LocalDate.of(2025, 5, 1).toEpochDay()
                val endEpochDay = java.time.LocalDate.of(2025, 5, 5).toEpochDay()
                val insertMockTrip = """
            INSERT INTO trip (destination, tripType, startDate, endDate, budget)
            VALUES ('Paris', 'Lazer', $startEpochDay, $endEpochDay, 3500.0)
        """
                db.execSQL(insertMockTrip)
            }
        }


        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "travel_counselor_database"
                )
                    .addMigrations(
                        V2_CREATING_TABLES_USER_AND_TRIP,
                        V3_ALTER_DATE_FIELDS
                    )
                    .build().also { Instance = it }
            }
        }
    }
}
