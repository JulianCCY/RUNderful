package com.example.running_app.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

@Composable
fun StatScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Title()
        OverviewBox()
        GraphSection()
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
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Total distance exercised: xxxx km",
//                style = MaterialTheme.typography.body1,
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Average velocity: xxx m/s",
//                style = MaterialTheme.typography.body1,
//            )
        }
    }
}

@Composable
fun Graph(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(color = Color.Red),
                    LinePlot.Intersection(color = Color.Blue),
                    LinePlot.Highlight(color = Color.Yellow),
                )
            ),
            xAxis = LinePlot.XAxis(steps = 5, unit = 1f, roundToInt = true),
            yAxis = LinePlot.YAxis(steps = 5, roundToInt = true),
            grid = LinePlot.Grid(Color.Red, steps = 4),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    )
}

@Composable
fun GraphSection() {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Distance()
    }
}

@Composable
fun Distance() {
    val testData = listOf(
        DataPoint(1.toFloat(), 870.toFloat()),
        DataPoint(2.toFloat(), 970.toFloat()),
        DataPoint(3.toFloat(), 1070.toFloat()),
        DataPoint(4.toFloat(), 790.toFloat()),
        DataPoint(5.toFloat(), 560.toFloat()),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "Total distance exercised: ",
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = "xxxx ",
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = "m",
                style = MaterialTheme.typography.body2,
            )
        }
        Graph(lines = listOf(testData))
    }
}

@Composable
fun AvgVelocity() {

}

@Composable
fun HistorySection() {

}

@Composable
fun Histories() {

}