package com.example.running_app.data.weather.weatherData

import com.squareup.moshi.Json

data class DailyWeatherDto(
    @field:Json(name = "daily")
    val dailyWeatherData: DailyWeatherDataDto
)
