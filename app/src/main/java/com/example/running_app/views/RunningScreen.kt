package com.example.running_app.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.R
import com.example.running_app.data.running.heartrate.BLEViewModel
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.viewModels.RunningViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.math.absoluteValue

@Composable
fun RunningScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CounterDisplay()
        StatsDisplay()
        Spacer(modifier = Modifier.height(10.dp))
        Buttons()
//        MapView()
    }
}

@Composable
fun CounterDisplay(runningViewModel: RunningViewModel = viewModel()) {
    val time = runningViewModel.time.observeAsState("00:00:000")
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

    val getSteps by runningViewModel.steps.observeAsState()
    val steps  = getSteps?.absoluteValue

    val getHeartRate by bleViewModel.mBPM.observeAsState()
    val heartRate = getHeartRate?.absoluteValue

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Row(

        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Total steps",
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
                    text = "Distance travelled",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "00 km",
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
                    text = "Avg. velocity",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "00 m/s",
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Avg. heart rate",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = if (heartRate != null && heartRate != 0) "$heartRate bpm" else "Not In Use",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Composable
fun Buttons(runningViewModel: RunningViewModel = viewModel(), bleViewModel: BLEViewModel = viewModel()) {
    var pauseResume by remember { mutableStateOf("pause") }
//    runningViewModel.startCountTime()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        var isButtonVisible by remember { mutableStateOf(true) }
        Button(
            onClick = {
                runningViewModel.isActive = true
                runningViewModel.startCountTime(startOrResume = true)
                isButtonVisible = false
                Log.d("steps", "start")

            }, modifier = Modifier
                .alpha(if (isButtonVisible) 1f else 0f)
                .clip(CutCornerShape(10.dp))
                .width(200.dp)
        ) {
            Text(
                text = "Start",
                style = MaterialTheme.typography.body1
            )
        }
        Button(
            onClick = {
                if (pauseResume == "pause") {
                    runningViewModel.pauseCountTime()
                    runningViewModel.unregisterStepCounterSensor()
                    pauseResume = "resume"
                    Log.d("steps", "pause")
                } else if (pauseResume == "resume") {
                    runningViewModel.isActive = true
                    runningViewModel.startCountTime()
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
                runningViewModel.stopCountTime()
                runningViewModel.unregisterStepCounterSensor()
                isButtonVisible = true
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
        Button(
            onClick = {
                bleViewModel.scanDevices()
            },
            modifier = Modifier
                .clip(CutCornerShape(10.dp))
                .width(200.dp)
        ) {
            Text(
                text = "Connect HR",
                style = MaterialTheme.typography.body1
            )
        }

    }
}

@Composable
fun MapView() {
    val helsinki = LatLng(60.19, 24.94)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(helsinki, 10f)
    }
    GoogleMap(
        modifier = Modifier
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