package com.example.running_app.views

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.running_app.CHANNEL_ID
import com.example.running_app.MainActivity
import com.example.running_app.R
import com.example.running_app.data.running.heartrate.BLEViewModel
import com.example.running_app.data.weather.WeatherState
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.ui.theme.Red1
import com.example.running_app.viewModels.RunningViewModel
import com.example.running_app.viewModels.WeatherViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun RunningScreen(navController: NavController, weatherViewModel: WeatherViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
    ) {
        CounterDisplay()
        StatsDisplay()
        Spacer(modifier = Modifier.height(10.dp))
        Buttons(navController = navController, state = weatherViewModel.state)
        MapView()
    }
}

@Composable
fun CounterDisplay(runningViewModel: RunningViewModel = viewModel()) {
    val time = runningViewModel.time.observeAsState("00:00:00")
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = time.value,
            fontFamily = FontFamily(Font(R.font.leaguegothic_regular)),
            fontSize = 96.sp,
            color = Orange1,
        )
    }
}

@Composable
fun StatsDisplay(runningViewModel: RunningViewModel = viewModel(), bleViewModel: BLEViewModel = viewModel()) {

    // Getting data from the viewModel
    val getSteps by runningViewModel.steps.observeAsState()
    val steps  = getSteps?.absoluteValue

    val getHeartRate by runningViewModel.mBPM.observeAsState()
    val heartRate = getHeartRate?.absoluteValue

    val getVelocity by runningViewModel.velocity.observeAsState()
    val velocity = getVelocity?.absoluteValue

    val getDistance by runningViewModel.distance.observeAsState()
    val distance = getDistance?.absoluteValue

    val getCalories by runningViewModel.calories.observeAsState()
    val calories = getCalories?.absoluteValue

    val getSLength by runningViewModel.sLength.observeAsState()
    val sLength = getSLength?.absoluteValue

    val velocityFormatter = DecimalFormat("#.##")
    velocityFormatter.roundingMode = RoundingMode.DOWN

    val distanceFormatter = DecimalFormat("#.##")
    distanceFormatter.roundingMode = RoundingMode.DOWN

    val caloriesFormatter = DecimalFormat("#.#")
    caloriesFormatter.roundingMode = RoundingMode.DOWN

    val sLengthFormatter = DecimalFormat("#.##")
    sLengthFormatter.roundingMode = RoundingMode.DOWN


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Steps
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
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = if (steps != null) "$steps " else "0 ",
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            text = stringResource(R.string.steps),
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
            }
            // Distance
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
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = if (distance != null) distanceFormatter.format(distance) else "0",
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            text = " m",
                            style = MaterialTheme.typography.body1,
                        )

                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            // Speed
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
                        text = stringResource(R.string.speed),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSecondary,
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = if (velocity != null) velocityFormatter.format(velocity) else "Rest",
                            style = MaterialTheme.typography.body1,
                        )
                        if (velocity != null) {
                            Text(
                                text = " m/s",
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                }
            }
            // Heart beat
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .width(170.dp),
            ) {
                Icon(
                    imageVector = Icons.Sharp.MonitorHeart,
                    contentDescription = "Localized description",
                    tint = Orange1,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(start = 4.dp, end = 6.dp)
                )
                Column {
                    Text(
                        text = stringResource(R.string.heart_rate),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSecondary,
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        if (heartRate != null && heartRate != 0) {
                            Text(
                                text = "$heartRate",
                                style = MaterialTheme.typography.body1,
                            )
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Heart",
                                tint = Red1,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Text(
                                text = "bpm",
                                style = MaterialTheme.typography.body2,
                            )
                        } else {
                            Text(
                                text = "Not In Use",
                                style = MaterialTheme.typography.body2,
                            )
                        }

                    }

                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            // Stride
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
                        .padding(end = 6.dp)
                )
                Column {
                    Text(
                        text = stringResource(R.string.stride_length),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSecondary,
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = if (sLength != null) sLengthFormatter.format(sLength) else "0",
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            text = " m",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
            // Calories
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
                )
                Column {
                    Text(
                        text = stringResource(R.string.calories),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSecondary,
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = if (calories != null && calories < 1000) "$calories"
                            else if (calories!= null && calories > 1000) caloriesFormatter.format(calories/1000)
                            else "0",
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            text = if (calories != null && calories < 1000) " cal" else " kcal",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Buttons(
    state: WeatherState,
    runningViewModel: RunningViewModel = viewModel(),
    navController: NavController
) {

    var pauseResume by remember { mutableStateOf("pause") }

    runningViewModel.isRunning = true
    runningViewModel.startRunning(true)

    if (state.weatherInfo?.currentWeatherData?.weatherType?.weatherDesc != null){
        runningViewModel.weather = state.weatherInfo.currentWeatherData.weatherType.weatherDesc
        runningViewModel.temperature = state.weatherInfo.currentWeatherData.temperatureCelsius.roundToInt()
    }


//    val testing = runningViewModel.getAllRecords().observeAsState()
//    Log.d("Room Running", "${testing.value}")

//
//    val testing2 = runningViewModel.getAllCoordinates().observeAsState()
//    Log.d("Room Running", "${testing2.value}")
    val context = LocalContext.current
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0 , intent, 0)

    val notify = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Exercise complete!")
        .setContentText("Good job! Stay hydrated and take a rest.")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(150.dp)
    ) {
        // Pause Resume Button
        Button(
            onClick = {
                if (pauseResume == "pause") {
                    runningViewModel.isRunning = false
                    runningViewModel.pauseRunning()
                    runningViewModel.unregisterStepCounterSensor()
                    pauseResume = "resume"
                    Log.d("steps", "pause")
                } else if (pauseResume == "resume") {
                    runningViewModel.isRunning = true
                    runningViewModel.startRunning()
                    runningViewModel.registerStepCounterSensor()
                    pauseResume = "pause"
                    Log.d("steps", "resume")
                }
            },
            modifier = Modifier
                .clip(CutCornerShape(10.dp))
                .size(100.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (pauseResume == "pause") {
                    Text(
                        text = "Pause",
                        style = MaterialTheme.typography.body1,
                    )
                } else if (pauseResume == "resume") {
                    Text(
                        text = "Resume",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(30.dp))
        // Finish button
        Button(
            onClick = {
                if (runningViewModel.latitude.isNotEmpty() && runningViewModel.longitude.isNotEmpty()) {
                    runningViewModel.stopRunning()
                    runningViewModel.unregisterStepCounterSensor()
                    navController.navigate("result")
                    Log.d("steps", "stop")
                    NotificationManagerCompat.from(context).notify(123,notify)
                }
            },
            modifier = Modifier
                .clip(CutCornerShape(10.dp))
                .size(100.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Finish",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
//    Column(
//        verticalArrangement = Arrangement.SpaceAround,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth()
//            .height(150.dp)
//    ) {
//        // Pause Resume Button
//        Button(
//            onClick = {
//                if (pauseResume == "pause") {
//                    runningViewModel.isRunning = false
//                    runningViewModel.pauseRunning()
//                    runningViewModel.unregisterStepCounterSensor()
//                    pauseResume = "resume"
//                    Log.d("steps", "pause")
//                } else if (pauseResume == "resume") {
//                    runningViewModel.isRunning = true
//                    runningViewModel.startRunning()
//                    runningViewModel.registerStepCounterSensor()
//                    pauseResume = "pause"
//                    Log.d("steps", "resume")
//                }
//            },
//            modifier = Modifier
//                .clip(CutCornerShape(10.dp))
//                .width(200.dp)
//        ) {
//            if (pauseResume == "pause") {
//                Text(
//                    text = "Pause",
//                    style = MaterialTheme.typography.body1,
//                )
//            } else if (pauseResume == "resume") {
//                Text(
//                    text = "Resume",
//                    style = MaterialTheme.typography.body1,
//                )
//            }
//        }
//        // Finish button
//        Button(
//            onClick = {
//                if (runningViewModel.latitude.isNotEmpty() && runningViewModel.longitude.isNotEmpty()) {
//                    runningViewModel.stopRunning()
//                    runningViewModel.unregisterStepCounterSensor()
////                isButtonVisible = true
//                    navController.navigate("result")
//                    Log.d("steps", "stop")
//                }
//            },
//            modifier = Modifier
//                .clip(CutCornerShape(10.dp))
//                .width(200.dp)
//        ) {
//            Text(
//                text = "Finish",
//                style = MaterialTheme.typography.body1
//            )
//        }
//    }
}

@Composable
fun MapView(runningViewModel: RunningViewModel = viewModel()) {

    // live update the marker location
    val getCurrentLat by runningViewModel.runLat.observeAsState()
    val currentLat = getCurrentLat?.absoluteValue

    val getCurrentLong by runningViewModel.runLong.observeAsState()
    val currentLong = getCurrentLong?.absoluteValue

    if (getCurrentLat != null && getCurrentLong != null) {
        val myCurrentLocation = LatLng(currentLat!!, currentLong!!)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(myCurrentLocation, 15f)
        }
        GoogleMap(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            onMapClick = {cameraPositionState.move(CameraUpdateFactory.newLatLng(myCurrentLocation))}
        ) {
            Marker(
                state = MarkerState(
                    position = myCurrentLocation
                ),
                title = runningViewModel.getAddressWhenRunning(currentLat, currentLong),
                snippet = "Current position",
            )
        }
    }
}

