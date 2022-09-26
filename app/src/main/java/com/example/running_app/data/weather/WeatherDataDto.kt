package com.example.running_app.data.weather

import retrofit2.http.*
import com.squareup.moshi.Json
import java.sql.Timestamp

data class WeatherDataDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "weathercode")
    val weatherCodes: List<Int>,
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>,
    @field:Json(name = "windspeed_10m")
    val windSpeeds: List<Double>,
    @field:Json(name = "relativehumidity_2m")
    val humidities: List<Double>,

    //apparent_temperature,rain,snowfall
    @field:Json(name = "apparent_temperature")
    val apparentTemperatures: List<Double>,
    @field:Json(name = "rain")
    val rain: List<Double>,
    @field:Json(name = "snowfall")
    val snowfall: List<Double>,
    @field:Json(name = "timezone")
    val timeZone: List<String>
)
