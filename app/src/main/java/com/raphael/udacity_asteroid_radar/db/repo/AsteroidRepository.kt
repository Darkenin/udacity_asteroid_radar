package com.raphael.udacity_asteroid_radar.db.repo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.raphael.udacity_asteroid_radar.api.RetrofitInstance
import com.raphael.udacity_asteroid_radar.api.parseAsteroidsJsonResult
import com.raphael.udacity_asteroid_radar.db.AsteroidDatabase
import com.raphael.udacity_asteroid_radar.util.Asteroid
import com.raphael.udacity_asteroid_radar.Constants.API_KEY
import com.raphael.udacity_asteroid_radar.db.asDatabaseModel
import com.raphael.udacity_asteroid_radar.db.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AsteroidRepository(private val database: AsteroidDatabase) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val startDate = LocalDateTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val endDate = LocalDateTime.now().minusDays(7)

    val allAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.getAsteroidDao().getAsteroids()) {
            it.asDomainModel()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val todayAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.getAsteroidDao().getAsteroidsDay(startDate.format(DateTimeFormatter.ISO_DATE))) {
            it.asDomainModel()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val weekAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(
            database.getAsteroidDao().getAsteroidsDate(
                startDate.format(DateTimeFormatter.ISO_DATE),
                endDate.format(DateTimeFormatter.ISO_DATE)
            )
        ) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroids = RetrofitInstance.retrofitService.getAsteroids(API_KEY)
                val result = parseAsteroidsJsonResult(JSONObject(asteroids))
                database.getAsteroidDao().insertAll(*result.asDatabaseModel())
            } catch (err: Exception) {
                Log.e("Failed: ", err.message.toString())
            }
        }
    }
}