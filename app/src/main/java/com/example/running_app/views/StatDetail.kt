package com.example.running_app.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.running_app.viewModels.StatDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.R
import com.example.running_app.viewModels.RunningViewModel
import com.google.android.gms.maps.model.LatLng
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

@Composable
fun StatDetail(runningId: Long, running: RunningViewModel = viewModel()) {
//    val data = viewModel.getDataById(dataId)

    val singleRecord = running.getRecordById(runningId).observeAsState().value

    if (singleRecord != null) {
        val route = running.getRouteForResult(runningId).observeAsState().value?.toList()?.map { LatLng(it.latitude, it.longitude) }

        if (!route.isNullOrEmpty()) {
            val data = RunRecordForUI(
                singleRecord.rid,
                singleRecord.date,
                singleRecord.startTime,
                singleRecord.endTime,
                singleRecord.duration,
                singleRecord.temperature,
                singleRecord.weatherDesc,
                singleRecord.totalStep,
                singleRecord.distance.roundToInt(),
                singleRecord.avgSpeed,
                singleRecord.avgHR,
                singleRecord.calories,
                singleRecord.avgStrideLength,
                route,
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                DateDisplay(data)
                Divider(
                    thickness = 2.dp,
                    color = Orange1,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth()
                )
                PlotMapWithStartEnd(startCoord = route.first(), endCoord = route.last(), coords = route)
                StatDisplay(data)
            }
        }
    }
}

@Composable
fun DateDisplay(data: RunRecordForUI) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = data.date,
            style = MaterialTheme.typography.subtitle2,
        )
    }
}

@Composable
fun StatDisplay(data: RunRecordForUI) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        TimeWeatherDisplay(data)
        Divider(
            modifier = Modifier
                .width(250.dp)
                .align(CenterHorizontally)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            StepsDisplay(data)
            DistanceDisplay(data)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AVDisplay(data)
            AHDisplay(data)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            StrideDisplay(data)
            CaloriesDisplay(data)
        }
    }
}

@Composable
fun TimeWeatherDisplay(data: RunRecordForUI) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Sharp.Schedule,
                    contentDescription = "Localized description",
                    tint = Orange1,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = data.timeSpent,
                        style = MaterialTheme.typography.body2,
                    )
//                Text(
//                    text = " mins",
//                    style = MaterialTheme.typography.body2,
//                )
                }
            }
            Row {
                Text(
                    text = "${data.startTime} - ${data.endTime}",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(end = 10.dp)
        ) {
            Text(
                text = data.temp.toString(),
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = "°c  ",
                style = MaterialTheme.typography.body2,
            )
            WeatherIconByDesc(data.weatherDesc)
        }
    }
}

@Composable
fun StepsDisplay(data: RunRecordForUI) {
    Row(
        modifier = Modifier
            .width(170.dp)
    ) {
        Icon(
            imageVector = Icons.Sharp.DirectionsRun,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(50.dp)
        )
       Column {
           Text(
               text = stringResource(R.string.total_steps),
               style = MaterialTheme.typography.body2,
               color = MaterialTheme.colors.onSecondary,
           )
           Row(
               verticalAlignment = Alignment.Bottom
           ) {
               Text(
                   text = "${data.steps}",
                   style = MaterialTheme.typography.body1,
               )
               Text(
                   text = " ${stringResource(R.string.steps)}",
                   style = MaterialTheme.typography.body2,
               )
           }
       }
    }
}

@Composable
fun DistanceDisplay(data: RunRecordForUI) {
    Row(
        modifier = Modifier
            .width(170.dp)
    ) {
        Icon(
            imageVector = Icons.Sharp.LocationOn,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(50.dp)
        )
        Column {
            Text(
                text = stringResource(R.string.total_distance),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${data.distance}",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " m",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun AVDisplay(data: RunRecordForUI) {
    val velocityFormatter = DecimalFormat("#.##")
    velocityFormatter.roundingMode = RoundingMode.DOWN
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .width(170.dp)
    ) {
        Icon(
            imageVector = Icons.Sharp.Speed,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 2.dp)
        )
        Column {
            Text(
                text = stringResource(R.string.avg_speed),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = velocityFormatter.format(data.avgSpeed),
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " m/s",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun AHDisplay(data: RunRecordForUI) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .width(170.dp)
    ) {
        Icon(
            imageVector = Icons.Sharp.MonitorHeart,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 2.dp)
        )
        Column {
            Text(
                text = stringResource(R.string.avg_heart_rate),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = if (data.avgHeart != 0) "${data.avgHeart}" else "Not In Use",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = if (data.avgHeart != 0) " bpm" else "",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun StrideDisplay(data: RunRecordForUI) {
    val sLengthFormatter = DecimalFormat("#.##")
    sLengthFormatter.roundingMode = RoundingMode.DOWN
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .width(170.dp)
    ) {
        Icon(
            imageVector = Icons.Sharp.Straighten,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 4.dp)
        )
        Column {
            Text(
                text = stringResource(R.string.stride_length),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = sLengthFormatter.format(data.stride).toString(),
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " m",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun CaloriesDisplay(data: RunRecordForUI) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .width(170.dp)
    ) {
        Icon(
            imageVector = Icons.Sharp.LocalFireDepartment,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 2.dp)
        )
        Column {
            Text(
                text = stringResource(R.string.calories),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = data.calories.toString(),
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = if (data.calories < 1000) " cal" else " kcal",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}