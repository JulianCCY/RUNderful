package com.example.running_app.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                // Last updated
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Sharp.Update,
                        contentDescription = "LastUpdated",
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = state.weatherInfo?.currentWeatherData?.time?.format(DateTimeFormatter.ofPattern("HH:mm"))?: " No state",
                        style = MaterialTheme.typography.h3,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 80.dp)
                ) {
                    // Temperature
                    Text(
                        text = "14",
                        fontFamily = FontFamily(Font(R.font.leaguegothic_regular)),
                        fontSize = 96.sp,
                        color = Orange1,
                    )
                    // Description
                    Text(
                        text = "Partly Cloudy",
                        style = MaterialTheme.typography.subtitle2,
                        color = Orange2,
                    )
                }
                // Air pressure
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
                // Humidity
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
                        text = " 50%",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.surface,
                    )
                }
                // Wind speed
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Sharp.Air,
                        contentDescription = "WindSpeed",
                        tint = MaterialTheme.colors.surface,
                    )
                    Text(
                        text = " 8 km/h",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.surface,
                    )
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