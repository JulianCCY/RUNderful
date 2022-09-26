package com.example.running_app.data.weather

import retrofit2.http.*

// There is where we call the weather api from Open-Meteo Weather Forecast API
interface WeatherApi {

    //https://api.open-meteo.com
    //v1/forecast?hourly=weathercode&daily=weathercode&timezone=auto&latitude=52.52&longitude=13.41
    //temperature_2m,relativehumidity_2m,apparent_temperature,rain,snowfall,weathercode,surface_pressure,windspeed_10m

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