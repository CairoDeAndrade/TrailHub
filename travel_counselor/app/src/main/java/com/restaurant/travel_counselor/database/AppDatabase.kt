package com.restaurant.travel_counselor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.restaurant.travel_counselor.dao.TripDao
import com.restaurant.travel_counselor.dao.UserDao
import com.restaurant.travel_counselor.entities.Trip
import com.restaurant.travel_counselor.entities.User

@Database(
    entities = [User::class, Trip::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Criação da tabela User
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

                // Inserção do usuário 'teste'
                db.execSQL(
                    """
                            INSERT INTO User (name, password, email) 
                            VALUES ('teste', '123456', 'teste@email.com')
                        """
                )

                // Criação da tabela Trip
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

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "travel_counselor_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build().also { Instance = it }
            }
        }
    }
}
