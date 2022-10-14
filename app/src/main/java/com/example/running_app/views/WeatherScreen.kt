package com.example.running_app.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.SevereCold
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.running_app.R
import com.example.running_app.data.weather.DailyWeatherState
import com.example.running_app.data.weather.WeatherState
import com.example.running_app.ui.theme.*
import com.example.running_app.viewModels.DailyWeatherViewModel
import com.example.running_app.viewModels.WeatherViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel, dailyWeatherViewModel: DailyWeatherViewModel) {
    // update weather screen everytime you access it
    weatherViewModel.loadWeatherInfo()
    dailyWeatherViewModel.loadDailyWeatherInfo()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CurrentWeather(weatherViewModel.state, dailyWeatherViewModel.dailyState, weatherViewModel)
        ForecastSwitch(weatherViewModel)
        Forecast(weatherViewModel, dailyWeatherViewModel)
    }
}

@Composable
fun CurrentWeather(
    state: WeatherState,
    dailyState: DailyWeatherState,
    viewModel: WeatherViewModel,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 30.dp)
                ) {
                    // Current City
                    val address by viewModel.address.observeAsState()
                    if (address != null){
                        Text(
                            text = "$address",
                            style = MaterialTheme.typography.h2,
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

                    // Temperature
                    val temperature = state.weatherInfo?.currentWeatherData?.temperatureCelsius
                    if (temperature != null) {
                        Text(
                            text = "${state.weatherInfo.currentWeatherData.temperatureCelsius.roundToInt()}°",
                            fontFamily = FontFamily(Font(R.font.leaguegothic_regular)),
                            fontSize = 94.sp,
                            color = Orange1,
                            modifier = Modifier
                                .padding(start = 20.dp)
                        )
                    } else {
                        Text(
                            text = "——",
                            fontFamily = FontFamily(Font(R.font.leaguegothic_regular)),
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onPrimary,
                        )
                    }

                    // Description
                    val desc = state.weatherInfo?.currentWeatherData?.weatherType?.weatherDesc
                    if (desc != null) {
                        Text(
                            text = state.weatherInfo.currentWeatherData.weatherType.weatherDesc,
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

                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ){
                        // Highest temperature of the day
                        val maxTemperature = dailyState.weatherInfo?.todayWeatherData?.temperatureCelsius_max
                        if (maxTemperature != null) {
                            Text(
                                text = "H: ${dailyState.weatherInfo.todayWeatherData.temperatureCelsius_max.roundToInt()}°",
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
                        Spacer(modifier = Modifier.padding(horizontal = 15.dp))
                        // Lowest temperature of the day
                        val minTemperature = dailyState.weatherInfo?.todayWeatherData?.temperatureCelsius_min
                        if (minTemperature != null) {
                            Text(
                                text = "L: ${dailyState.weatherInfo.todayWeatherData.temperatureCelsius_min.roundToInt()}°",
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
                }

                // Air pressure
                val airPressure = state.weatherInfo?.currentWeatherData?.pressure
                if (airPressure != null) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Sharp.Air,
                            contentDescription = "AirPressure",
                            tint = MaterialTheme.colors.onPrimary,
                        )
                        Text(
                            text = airPressure.roundToInt().toString() + " hpa",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onPrimary,
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
                            tint = MaterialTheme.colors.onPrimary,
                        )
                        Text(
                            text = humidity.roundToInt().toString() + "%",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onPrimary,
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
                            Icons.Sharp.WindPower,
                            contentDescription = wind.roundToInt().toString() + "km/h",
                            tint = MaterialTheme.colors.onPrimary,
                        )
                        Text(
                            text = " 8 km/h",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onPrimary,
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
fun ForecastSwitch(weatherViewModel: WeatherViewModel) {
    val switch by remember { weatherViewModel.switch }
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
                text = "Next 24 hours",
                style = MaterialTheme.typography.body1,
                fontWeight = if (switch == "hours") FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .selectable(
                        selected = true,
                        onClick = { weatherViewModel.switch.value = "hours" }
                    )
            )
            Text(
                text = "Following week",
                style = MaterialTheme.typography.body1,
                fontWeight = if (switch == "week") FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .selectable(
                        selected = true,
                        onClick = {
                            weatherViewModel.switch.value = "week"
                        }
                    )
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Divider(
            color = Orange1,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun Forecast(weatherViewModel: WeatherViewModel, dailyWeatherViewModel: DailyWeatherViewModel) {
    val switch by remember { weatherViewModel.switch }
    if (switch == "hours") {
        HourWeather(weatherViewModel.state)
    }
    if (switch == "week") {
        WeekWeather(dailyWeatherViewModel.dailyState)
    }
}

@Composable
fun HourWeather(
    state: WeatherState,
) {
    state.weatherInfo?.weatherDataPerHour?.values?.toList()?.flatten()?.map { it }?.filter { it.time.hour >= LocalDateTime.now().hour || it.time.isAfter(
        LocalDateTime.now()) }?.slice(0..23)?.let {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            LazyColumn(content = {
                items(it) { data ->
                    val timeFormatter = remember(data) {
                        data.time.format(
                            DateTimeFormatter.ofPattern("HH:mm")
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(start = 20.dp, end = 25.dp, bottom = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(150.dp)
                        ){
                            Row(horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .width(75.dp)
                            ){
                                Text(
                                    text = if (data.time.hour == LocalDateTime.now().hour && data.time.dayOfMonth == LocalDateTime.now().dayOfMonth) "Current"
                                    else timeFormatter,
                                    style = MaterialTheme.typography.body1,
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .width(50.dp)
                            ) {
                                WeatherIconHourly(state)
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .width(100.dp)
                        ){
                            Text(
                                text = "${data.temperatureCelsius.roundToInt()}°",
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            })
        }
    }
}


@Composable
fun WeekWeather(
    state: DailyWeatherState
) {
    state.weatherInfo?.weatherDataPerDay?.values?.toList()?.flatten()?.map{ it }?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            LazyColumn(content = {
                items(it) { data ->
                    val timeFormatter = remember(data) {
                        data.date.dayOfWeek.name.uppercase().slice(0..2)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp, bottom = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(150.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .width(75.dp)
                            ) {
                                Text(
                                    text = if(data.date.dayOfYear == LocalDateTime.now().dayOfYear) "Today" else timeFormatter,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .width(50.dp)
                            ) {
                                WeatherIcon(state)
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .width(100.dp)
                        ) {
                            Text(
                                text = "${data.temperatureCelsius_min.roundToInt()}° - ${data.temperatureCelsius_max.roundToInt()}°",
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }
                }
            })
        }
    }
}

@Composable
fun WeatherIcon(dailyState: DailyWeatherState) {
    // Weather Icon
    when (dailyState.weatherInfo?.todayWeatherData?.daily_weatherType?.weatherDesc) {
        "Clear sky", "Mainly clear" -> {
            Icon(
                Icons.Sharp.WbSunny,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Partly cloudy", "Overcast", "Foggy", "Depositing rime fog" -> {
            Icon(
                Icons.Sharp.Cloud,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Slight snow fall", "Moderate snow fall", "Snow grains", "Light snow showers" -> {
            Icon(
                Icons.Filled.AcUnit,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Heavy snow fall", "Heavy snow showers" -> {
            Icon(
                Icons.Filled.SevereCold,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Moderate thunderstorm", "Thunderstorm with slight hail", "Thunderstorm with heavy hail" -> {
            Icon(
                Icons.Sharp.Thunderstorm,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        else -> {
            Icon(
                Icons.Sharp.Umbrella,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun WeatherIconByDesc(desc: String) {
    // Weather Icon
    when (desc) {
        "Clear sky", "Mainly clear" -> {
            Icon(
                Icons.Sharp.WbSunny,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Partly cloudy", "Overcast", "Foggy", "Depositing rime fog" -> {
            Icon(
                Icons.Sharp.Cloud,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Slight snow fall", "Moderate snow fall", "Snow grains", "Light snow showers" -> {
            Icon(
                Icons.Filled.AcUnit,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Heavy snow fall", "Heavy snow showers" -> {
            Icon(
                Icons.Filled.SevereCold,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Moderate thunderstorm", "Thunderstorm with slight hail", "Thunderstorm with heavy hail" -> {
            Icon(
                Icons.Sharp.Thunderstorm,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        else -> {
            Icon(
                Icons.Sharp.Umbrella,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun WeatherIconHourly(state: WeatherState) {
    // Weather Icon
    when (state.weatherInfo?.currentWeatherData?.weatherType?.weatherDesc) {
        "Clear sky", "Mainly clear" -> {
            Icon(
                Icons.Sharp.WbSunny,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Partly cloudy", "Overcast", "Foggy", "Depositing rime fog" -> {
            Icon(
                Icons.Sharp.Cloud,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Slight snow fall", "Moderate snow fall", "Snow grains", "Light snow showers" -> {
            Icon(
                Icons.Filled.AcUnit,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Heavy snow fall", "Heavy snow showers" -> {
            Icon(
                Icons.Filled.SevereCold,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        "Moderate thunderstorm", "Thunderstorm with slight hail", "Thunderstorm with heavy hail" -> {
            Icon(
                Icons.Sharp.Thunderstorm,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        else -> {
            Icon(
                Icons.Sharp.Umbrella,
                contentDescription = "WeatherIcon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}