package com.example.running_app.data.weather

import com.example.running_app.data.weather.weatherData.WeatherInfo

// State for getting data of hourly weather and forecasting
data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
