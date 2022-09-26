package com.example.running_app.data.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.running_app.data.weather.display.WeatherData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun TwentyFourHoursForecastingDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier
) {

    val timeFormatter = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            if ( weatherData.time.hour == LocalDateTime.now().hour && weatherData.time.dayOfMonth == LocalDateTime.now().dayOfMonth)
                "current"
            else
                timeFormatter,
            color = Color.LightGray
        )
        Text(
            text = "${weatherData.temperatureCelsius.roundToInt()}°C",
            fontWeight = FontWeight.Bold
        )
    }
}