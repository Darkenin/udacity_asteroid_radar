package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raphael.udacity_asteroid_radar.db.repo.AsteroidRepository
import com.raphael.udacity_asteroid_radar.db.AsteroidDatabase.Companion.createDatabase
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshAstroidWorker"
    }

    override suspend fun doWork(): Result {
        val database = createDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}