package com.example.running_app.data.weather

import com.example.running_app.data.weather.weatherData.DailyWeatherInfo

// State for getting data of daily weather and forecasting
data class DailyWeatherState(
    val weatherInfo: DailyWeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)