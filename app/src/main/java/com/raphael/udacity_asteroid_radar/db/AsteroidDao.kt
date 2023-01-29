package com.raphael.udacity_asteroid_radar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    //DESC -> descending order
    @Query("SELECT * FROM asteroids ORDER BY closeApproachDate DESC")
    fun getAsteroids(): LiveData<List<DbEntiry>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate = :startDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsDay(startDate: String): LiveData<List<DbEntiry>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsDate(startDate: String, endDate: String): LiveData<List<DbEntiry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: DbEntiry)

}