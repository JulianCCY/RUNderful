package com.example.running_app.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.sharp.Bluetooth
import androidx.compose.material.icons.sharp.BluetoothConnected
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.R
import com.example.running_app.data.running.heartrate.BLEViewModel
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.ui.theme.Red1
import com.example.running_app.viewModels.RunningViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.absoluteValue

@Composable
fun RunningScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CounterDisplay()
        StatsDisplay()
        Spacer(modifier = Modifier.height(10.dp))
        Buttons()
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

    val strideLength = runningViewModel.sLength

    val velocityFormatter = DecimalFormat("#.##")
    velocityFormatter.roundingMode = RoundingMode.DOWN

    val distanceFormatter = DecimalFormat("#.##")
    distanceFormatter.roundingMode = RoundingMode.DOWN

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 30.dp)
            .padding(vertical = 15.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Total Steps",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = if (steps != null) "$steps" else "0"
                    ,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Total Distance",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = if (distance != null) distanceFormatter.format(distance) + " M" else "0 M",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
        Row() {
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Speed",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = if (velocity != null) velocityFormatter.format(velocity) + " M/S" else "Rest",
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Heart Rate",
                    style = MaterialTheme.typography.body1
                )
                Row(

                ){
                    if (heartRate != null && heartRate != 0) {
                        Text(
                            text = "$heartRate",
                            style = MaterialTheme.typography.subtitle2
                        )
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "My Heart",
                            tint = Red1,
                            modifier = Modifier
                                .size(38.dp)
                        )
                        Text(
                            text = "BPM",
                            style = MaterialTheme.typography.subtitle2
                        )
                    } else {
                        Text(
                            text = "Not In Use",
                            style = MaterialTheme.typography.subtitle2
                        )
                    }

                }

            }
        }

        Row() {
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Stride Length",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = if (runningViewModel.sLength == null) "${runningViewModel.sLength} M" else "0 M",
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Active Calories",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Active Calories",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Composable
fun Buttons(runningViewModel: RunningViewModel = viewModel()) {
    var pauseResume by remember { mutableStateOf("pause") }
    runningViewModel.isRunning = true
    runningViewModel.startRunning(true)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
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
                .width(200.dp)
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
        Button(
            onClick = {
                runningViewModel.stopRunning()
                runningViewModel.unregisterStepCounterSensor()
//                isButtonVisible = true
                Log.d("steps", "stop")
            },
            modifier = Modifier
                .clip(CutCornerShape(10.dp))
                .width(200.dp)
        ) {
            Text(
                text = "Finish",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun MapView() {
    val helsinki = LatLng(60.19, 24.94)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(helsinki, 15f)
    }
    GoogleMap(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = helsinki),
            title = "Helsinki",
            snippet = "Marker in Helsinki",
        )
    }
}
