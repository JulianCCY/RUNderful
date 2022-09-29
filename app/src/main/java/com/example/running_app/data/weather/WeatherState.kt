package com.example.running_app.data.weather

import com.example.running_app.data.weather.weatherData.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)