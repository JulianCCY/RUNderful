package com.example.running_app.data.weather.weatherData

import com.squareup.moshi.Json

data class DailyWeatherDataDto(
    val time: List<String>,

    @field:Json(name = "weathercode")
    val weatherCodes_daily: List<Int>,

    @field:Json(name = "temperature_2m_max")
    val temperatures_max: List<Double>,

    @field:Json(name = "temperature_2m_min")
    val temperatures_min: List<Double>,

    @field:Json(name = "sunrise")
    val sunrise: List<String>,

    @field:Json(name = "sunset")
    val sunset: List<String>,
)
