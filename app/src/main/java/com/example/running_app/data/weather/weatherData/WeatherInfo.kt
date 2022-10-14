package com.example.running_app.data.weather.weatherData

data class WeatherInfo(
    val weatherDataPerHour: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
)
