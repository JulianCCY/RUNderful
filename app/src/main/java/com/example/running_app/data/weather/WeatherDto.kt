package com.example.running_app.data.weather

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)