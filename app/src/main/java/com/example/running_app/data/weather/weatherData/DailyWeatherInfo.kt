package com.example.running_app.data.weather.weatherData

data class DailyWeatherInfo(
    val weatherDataPerDay: Map<Int, List<DailyWeatherData>>,
    val todayWeatherData: DailyWeatherData?
)