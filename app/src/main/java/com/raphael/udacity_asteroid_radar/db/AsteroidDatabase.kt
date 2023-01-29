package com.raphael.udacity_asteroid_radar.db

import android.content.Context
import androidx.room.*

@Database(entities = [DbEntiry::class], version = 1, exportSchema = false)

abstract class AsteroidDatabase: RoomDatabase() {

    abstract fun getAsteroidDao(): AsteroidDao

    // create the database
    companion object{
        @Volatile
        private var instance: AsteroidDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

         fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids"
            ).build()
    }
}