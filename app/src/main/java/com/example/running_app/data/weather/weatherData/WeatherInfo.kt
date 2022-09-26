package com.example.running_app.data.weather.weatherData

import com.example.running_app.data.weather.display.WeatherData

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
)
