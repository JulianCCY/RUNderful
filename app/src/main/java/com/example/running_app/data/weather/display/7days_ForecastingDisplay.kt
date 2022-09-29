package com.example.running_app.data.weather.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.running_app.data.weather.weatherData.DailyWeatherData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

@Composable
fun SevenDaysForecastingDisplay(
    weatherData: DailyWeatherData,
    modifier: Modifier = Modifier
){

    val timeFormatter = remember(weatherData) {
        weatherData.date.dayOfWeek.name.lowercase().replaceFirstChar(Char::titlecase)
    }

    Row(
        modifier = Modifier,
    ) {

        Text(
            text = if(weatherData.date.dayOfYear == LocalDateTime.now().dayOfYear) "Today" else timeFormatter,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = weatherData.daily_weatherType.weatherDesc,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "${weatherData.temperatureCelsius_min.roundToInt()}°C",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "${weatherData.temperatureCelsius_max.roundToInt()}°C",
            fontWeight = FontWeight.Bold
        )
    }

}