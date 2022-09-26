package com.example.running_app.data.weather.api

import com.example.running_app.data.weather.display.WeatherData
import com.example.running_app.data.weather.WeatherType
import com.example.running_app.data.weather.weatherData.WeatherDataDto
import com.example.running_app.data.weather.weatherData.WeatherDto
import com.example.running_app.data.weather.weatherData.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
//        apparent_temperature,rain,snowfall
        val apparentTemperature = apparentTemperatures[index]
        val rain = rain[index]
        val snowfall = snowfall[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.referToWMO(weatherCode),
                apparentTemperatureCelsius = apparentTemperature,
                rain = rain,
                snowfall = snowfall
            )
        )
    }.groupBy {
       it.index / 24
    }.mapValues {
        it.value.map{ it.data}
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute <= 50 && now.second <= 59) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}