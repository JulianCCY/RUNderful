package com.example.running_app.data.weather.api

import com.example.running_app.data.weather.weatherData.DailyWeatherDto
import retrofit2.http.*

// daily weather forecasting (7 days)
interface DailyWeatherApi {

    //weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset

    @GET("v1/forecast?timezone=auto&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset")
    suspend fun getDailyWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): DailyWeatherDto
}