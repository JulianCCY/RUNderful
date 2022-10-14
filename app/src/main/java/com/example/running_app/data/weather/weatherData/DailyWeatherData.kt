package com.example.running_app.data.weather.weatherData

import com.example.running_app.data.weather.WeatherType
import java.time.LocalDateTime

data class DailyWeatherData(
    val date: LocalDateTime,
    val temperatureCelsius_max: Double,
    val temperatureCelsius_min: Double,
    val daily_weatherType: WeatherType,
    val sunrise_time: LocalDateTime,
    val sunset_time: LocalDateTime,
)
