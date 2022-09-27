package com.example.running_app.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Air
import androidx.compose.material.icons.sharp.Thermostat
import androidx.compose.material.icons.sharp.WaterDrop
import androidx.compose.material.icons.sharp.Waves
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.running_app.R
import com.example.running_app.ui.theme.*


@Composable
fun WeatherScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CurrentWeather()
//        Toggle(
//            selection = "24hr",
//            states = listOf(
//                "24hr", "7days"
//            )
//        )
    }
}

@Composable
fun CurrentWeather() {
    Box(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clip(CutCornerShape(10.dp))
            .border(4.dp, Orange1, CutCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            // Last updated
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Last updated: ",
                    style = MaterialTheme.typography.h3,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 80.dp)
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
fun Toggle(
    selection: String,
    states: List<String>,
    onToggleChange: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(CutCornerShape(10.dp))
            .border(4.dp, Orange1)
    ) {
        states.forEachIndexed { index, state ->
            val isSelected = selection.lowercase() == state.lowercase()
            val backgroundTint = if (isSelected) Blue1 else Blue2

            if (index != 0) {
                Divider(
                    color = Orange1,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                )
            }
            Row(
                modifier = Modifier
                    .background(backgroundTint)
                    .padding(vertical = 6.dp, horizontal = 8.dp)
                    .toggleable(
                        value = isSelected,
                        enabled = true,
                        onValueChange = { selected ->
                            if (selected) {
                                onToggleChange(state)
                            }
                        }
                    )
            ) {
                Text(
                    text = state,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun WeatherForecast() {

}

@Composable
fun DayWeather() {

}

@Composable
fun WeekWeather() {

}