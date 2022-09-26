package com.example.running_app.data.weather.weatherData

import com.example.running_app.data.weather.weatherData.WeatherDataDto
import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)