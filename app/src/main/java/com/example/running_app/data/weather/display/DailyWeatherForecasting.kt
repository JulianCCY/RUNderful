package com.example.running_app.data.weather.display

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.running_app.data.weather.DailyWeatherState

@Composable
fun DailyWeatherForecasting(
    state: DailyWeatherState
){
//    Text(text = "${state.weatherInfo?.weatherDataPerDay?.values?.toList()?.flatten()?.map{it}}")
//    state.weatherInfo?.weatherDataPerDay?.values?.toList()?.flatten()?.map{it}
    state.weatherInfo?.weatherDataPerDay?.values?.toList()?.flatten()?.map{ it }?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            LazyColumn(content = {
                items(it) {data ->
                    SevenDaysForecastingDisplay(
                        data
                    )
                }
            })
        }
    }
}