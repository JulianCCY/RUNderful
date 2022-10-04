package com.example.running_app.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.running_app.R
import com.example.running_app.ui.theme.Orange1
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun StatScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Title()
        OverviewBox()
    }
}

@Composable
fun Title() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.statistic),
            style = MaterialTheme.typography.h2
        )
    }
}

@Composable
fun OverviewBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopStart)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawLine(
                    start = Offset(x = canvasWidth, y = 0f),
                    end = Offset(x = 0f, y = canvasHeight),
                    color = Orange1,
                    strokeWidth = 5F
                )
            }
        }
        Box(
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.TopStart)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawLine(
                    start = Offset(x = canvasWidth, y = 0f),
                    end = Offset(x = 0f, y = canvasHeight),
                    color = Orange1,
                    strokeWidth = 5F
                )
            }
        }
        OverviewData()
        Box(
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.BottomEnd)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawLine(
                    start = Offset(x = canvasWidth, y = 0f),
                    end = Offset(x = 0f, y = canvasHeight),
                    color = Orange1,
                    strokeWidth = 5F
                )
            }
        }
        Box(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.BottomEnd)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawLine(
                    start = Offset(x = canvasWidth, y = 0f),
                    end = Offset(x = 0f, y = canvasHeight),
                    color = Orange1,
                    strokeWidth = 5F
                )
            }
        }
    }
}

@Composable
fun OverviewData() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 50.dp)
    ) {
        Column {
            Text(
                text = "You have joined XXXX",
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = "since xx/xx/xxxx. (xx days)",
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Number of exercises recorded: X",
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Total steps recorded: xxxxxx",
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Total distance exercised: xxxx km",
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Average velocity: xxx m/s",
                style = MaterialTheme.typography.body1,
            )
        }
    }
}