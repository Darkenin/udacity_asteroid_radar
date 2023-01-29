package com.raphael.udacity_asteroid_radar.api

import com.udacity.asteroidradar.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApi {

    // endpoints
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("api_key") api_key: String
    ): String

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") api_key: String
    ): PictureOfDay
}