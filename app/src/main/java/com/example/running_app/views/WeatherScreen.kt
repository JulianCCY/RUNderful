package com.example.running_app.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.running_app.R
import com.example.running_app.data.weather.DailyWeatherState
import com.example.running_app.data.weather.WeatherState
import com.example.running_app.ui.theme.*
import com.example.running_app.viewModels.DailyWeatherViewModel
import com.example.running_app.viewModels.WeatherViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel, dailyWeatherViewModel: DailyWeatherViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CurrentWeather(weatherViewModel.state, dailyWeatherViewModel.dailyState)
        Switch()
    }
}

@Composable
fun CurrentWeather(
    state: WeatherState,
    dailyState: DailyWeatherState,
) {
//    state.weatherInfo?.currentWeatherData?.let {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
//            .clip(CutCornerShape(20.dp))
//            .border(4.dp, Orange1, CutCornerShape(20.dp))
        ) {
            dailyState.weatherInfo?.todayWeatherData?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp)
                ) {
                    // Weather Icon
                    Icon(
                        Icons.Sharp.NightsStay,
                        contentDescription = "Night",
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .size(64.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                // Last updated
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val updated = state.weatherInfo?.currentWeatherData?.time
                    if (updated != null) {
                        Icon(
                            Icons.Sharp.Update,
                            contentDescription = "LastUpdated",
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Text(
                            text = state.weatherInfo.currentWeatherData.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                            style = MaterialTheme.typography.body2,
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(24.dp)
                                .background(Gray)
                        ) { }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 50.dp)
                ) {
                    // Temperature
                    val temperature = state.weatherInfo?.currentWeatherData?.temperatureCelsius
                    if (temperature != null) {
                        Text(
                            text = "${state.weatherInfo.currentWeatherData.temperatureCelsius.roundToInt()}°",
                            fontFamily = FontFamily(Font(R.font.leaguegothic_regular)),
                            fontSize = 96.sp,
                            color = Orange1,
                        )
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(128.dp)
                                .padding(15.dp),
                            strokeWidth = 8.dp,
                        )
                    }
                    // Description
                    val desc = dailyState.weatherInfo?.todayWeatherData?.daily_weatherType?.weatherDesc
                    if (desc != null) {
                        Text(
                            text = dailyState.weatherInfo.todayWeatherData.daily_weatherType.weatherDesc,
                            style = MaterialTheme.typography.body1,
                            color = Orange2,
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(28.dp)
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp)
                            ) {
                                drawRect(Gray)
                            }
                        }
                    }
                }
                // Air pressure
                val airPressure = state.weatherInfo?.currentWeatherData?.pressure
                if (airPressure != null) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Sharp.Waves,
                            contentDescription = "AirPressure",
                            tint = MaterialTheme.colors.surface,
                        )
                        Text(
                            text = " 900 hpa",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.surface,
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(30.dp)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        ) {
                            drawRect(Gray)
                        }
                    }
                }
                // Humidity
                val humidity = state.weatherInfo?.currentWeatherData?.humidity
                if (humidity != null) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Sharp.WaterDrop,
                            contentDescription = "Humidity",
                            tint = MaterialTheme.colors.surface,
                        )
                        Text(
                            text = humidity.roundToInt().toString() + "%",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.surface,
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(28.dp)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        ) {
                            drawRect(Gray)
                        }
                    }
                }

                // Wind speed
                val wind = state.weatherInfo?.currentWeatherData?.windSpeed
                if (wind != null) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Sharp.Air,
                            contentDescription = wind.roundToInt().toString() + "km/h",
                            tint = MaterialTheme.colors.surface,
                        )
                        Text(
                            text = " 8 km/h",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.surface,
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(28.dp)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        ) {
                            drawRect(Gray)
                        }
                    }
                }
            }
//        }
    }
}

@Composable
fun Switch() {
    Column (
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Text(
                text = "Hours",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .selectable(
                        selected = true,
                        onClick = {}
                    )
            )
            Text(
                text = "Week",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .selectable(
                        selected = true,
                        onClick = {}
                    )
            )
        }
        Divider(
            color = Orange1,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

//@Composable
//fun WeatherForecast(weatherViewModel: WeatherViewModel) {
//    val forecastData = weatherViewModel.forecastData.observeAsState(listOf())
//    LazyColumn{
//        items(forecastData.value) {
//
//        }
//    }
//}

@Composable
fun DayWeather() {

}

@Composable
fun WeekWeather() {

}