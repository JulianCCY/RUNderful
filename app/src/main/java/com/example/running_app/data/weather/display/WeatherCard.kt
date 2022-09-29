package com.example.running_app.data.weather

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    dailyState: DailyWeatherState,
){
    state.weatherInfo?.currentWeatherData?.let {
        Card(
            backgroundColor = Color.DarkGray,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Today ${it.time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                    modifier = Modifier.align(Alignment.End),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${it.temperatureCelsius.roundToInt()}°",
                    fontSize = 25.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "apparent ${it.apparentTemperatureCelsius.roundToInt()}°",
                    fontSize = 15.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(){
                    Text(
                        text = "sunrise " + "${dailyState.weatherInfo?.todayWeatherData?.sunrise_time?.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        fontSize = 15.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "sunrise " + "${dailyState.weatherInfo?.todayWeatherData?.sunset_time?.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        fontSize = 15.sp,
                        color = Color.White
                    )


                }
                Spacer(modifier = Modifier.height(16.dp))
                Row() {
                    Text(
                        text = "min " + "${dailyState.weatherInfo?.todayWeatherData?.temperatureCelsius_min?.roundToInt()}°",
                        fontSize = 15.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "max " + "${dailyState.weatherInfo?.todayWeatherData?.temperatureCelsius_max?.roundToInt()}°",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it.weatherType.weatherDesc,
                    fontSize = 15.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "daily ${dailyState.weatherInfo?.todayWeatherData?.daily_weatherType?.weatherDesc}",
                    fontSize = 15.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "air pressure " + it.pressure.roundToInt().toString() + "hpa",
                    fontSize = 15.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "relative humidity " + it.humidity.roundToInt().toString() + "%",
                    fontSize = 15.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "wind speed " + it.windSpeed.roundToInt().toString() + "km/h",
                    fontSize = 15.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "rainfall " + "${(it.rain * 10).roundToInt()} " + "mm",
                    fontSize = 15.sp,
                    color = Color.White
                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = "snowfall " + it.snowfall.toString() + "cm inch",
//                    fontSize = 20.sp,
//                    color = Color.White
//                )


            }
        }
    }
}