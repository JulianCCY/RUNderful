package com.example.running_app.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.viewModels.RunningViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

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
fun StatsDisplay(runningViewModel: RunningViewModel = viewModel()) {
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
                    text = "0000",
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
                    text = "00 bpm",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Composable
fun Buttons(runningViewModel: RunningViewModel = viewModel()) {
    var pauseResume by remember { mutableStateOf("pause") }
    runningViewModel.startCountTime()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                if (pauseResume == "pause") {
                    runningViewModel.pauseCountTime()
                    pauseResume = "resume"
                } else if (pauseResume == "resume") {
                    runningViewModel.startCountTime()
                    pauseResume = "pause"
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