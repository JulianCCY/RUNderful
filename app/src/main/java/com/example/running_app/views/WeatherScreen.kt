package com.example.running_app.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.running_app.R
import com.example.running_app.ui.theme.*
import com.example.running_app.viewModels.WeatherViewModel


@Composable
fun WeatherScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CurrentWeather()
        Switch()
    }
}

@Composable
fun CurrentWeather() {
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
            Icon(
                Icons.Sharp.NightsStay,
                contentDescription = "Night",
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
//                Text(
//                    text = "Last updated: ",
//                    style = MaterialTheme.typography.h3,
//                )
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
                )
                // Description
                Text(
                    text = "Partly Cloudy",
                    style = MaterialTheme.typography.body1,
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
//                    tint = Orange1,
                )
                Text(
                    text = "900 hpa",
                    style = MaterialTheme.typography.body2,
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
//                    tint = Orange1,
                )
                Text(
                    text = "50%",
                    style = MaterialTheme.typography.body2,
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
//                    tint = Orange1,
                )
                Text(
                    text = "8 km/h",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
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
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .selectable(
                        selected = true,
                        onClick = {}
                    )
            )
            Text(
                text = "Week",
                style = MaterialTheme.typography.body2,
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