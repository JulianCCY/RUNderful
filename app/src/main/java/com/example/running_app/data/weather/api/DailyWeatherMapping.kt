package com.example.running_app.data.weather.api

import com.example.running_app.data.weather.WeatherType
import com.example.running_app.data.weather.weatherData.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// mapping for daily forecasting
private data class IndexedDailyWeatherData(
    val index: Int,
    val data: DailyWeatherData
)

fun DailyWeatherDataDto.toDailyWeatherDataMap(): Map<Int, List<DailyWeatherData>> {

    val df = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return time.mapIndexed { index, time ->
        val weatherCodeDaily = weatherCodes_daily[index]
        val temperatureDailyMax = temperatures_max[index]
        val temperatureDailyMin = temperatures_min[index]
        val sunrise = sunrise[index]
        val sunset = sunset[index]

        IndexedDailyWeatherData(
            index = index,
            data = DailyWeatherData(
                date = LocalDateTime.of(LocalDate.parse(time, df), LocalDateTime.MIN.toLocalTime()),
                temperatureCelsius_max = temperatureDailyMax,
                temperatureCelsius_min = temperatureDailyMin,
                daily_weatherType = WeatherType.referToWMO(weatherCodeDaily),
                sunrise_time = LocalDateTime.parse(sunrise, DateTimeFormatter.ISO_DATE_TIME),
                sunset_time = LocalDateTime.parse(sunset, DateTimeFormatter.ISO_DATE_TIME),
            )
        )
    }.groupBy {
        it.index
    }.mapValues { it.value.map { it.data } }
}

fun DailyWeatherDto.toDailyWeatherInfo(): DailyWeatherInfo{
    val dailyWeatherDataMap = dailyWeatherData.toDailyWeatherDataMap()
    val now = LocalDateTime.now()
    val todayWeatherData = dailyWeatherDataMap[0]?.find {
        val day = if (now.hour <= 23 && now.minute <= 59 && now.second <= 59) now.dayOfYear else now.plusDays(1).dayOfYear
        it.date.dayOfYear == day
    }
    return DailyWeatherInfo(
        weatherDataPerDay = dailyWeatherDataMap,
        todayWeatherData = todayWeatherData
    )
}
