package com.example.running_app.data.weather.api

import com.example.running_app.data.weather.weatherData.DailyWeatherDto
import com.example.running_app.data.weather.weatherData.WeatherDto
import retrofit2.http.*

// There is where we call the weather api from Open-Meteo Weather Forecast API
// hourly weather forecasting (coming 24 hours)
interface WeatherApi {

    //base uri: https://api.open-meteo.com

    @GET("v1/forecast?hourly=" +
            "temperature_2m,relativehumidity_2m,apparent_temperature,rain,snowfall,weathercode,pressure_msl,windspeed_10m" +
            "&timezone=auto")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto

}

//current to get hourly weather only:
//v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl&timezone=auto