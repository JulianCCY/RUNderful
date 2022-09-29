package com.example.running_app.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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

@Composable
fun RunningScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CounterDisplay()
        Buttons()
    }
}

@Composable
fun CounterDisplay(runningViewModel: RunningViewModel = viewModel()) {
    val time = runningViewModel.time.observeAsState("00:00:000")
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 15.dp)
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
fun Buttons(runningViewModel: RunningViewModel = viewModel()) {
    var pauseResume by remember { mutableStateOf("pause") }
    runningViewModel.startCountTime()
    Column(
        modifier = Modifier
            .padding(10.dp)
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
        ) {
            if (pauseResume == "pause") {
                Text(text = "Pause")
            } else if (pauseResume == "resume") {
                Text(text = "Resume")
            }
        }
        Button(
            onClick = {
            runningViewModel.stopCountTime()
            },
            modifier = Modifier
                .clip(CutCornerShape(10.dp))
        ) {
            Text(text = "Finish")
        }
    }
}