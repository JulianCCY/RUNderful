package com.example.running_app.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.running_app.R
import com.example.running_app.ui.theme.*
import com.example.running_app.viewModels.StatViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

@Composable
fun StatScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Title()
        OverviewBox()
        GraphSection()
        Histories(navController = navController)
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
            .padding(horizontal = 25.dp, vertical = 20.dp)
            .border(width = 2.dp, color = Orange1, CutCornerShape(60.dp, 0.dp, 60.dp, 0.dp))
            .height(200.dp)
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
            ExercisesCount()
            Steps()
            Distance()
        }
    }
}

@Composable
fun ExercisesCount() {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(bottom = 2.dp)
    ) {
        Text(
            text = "Number of exercises recorded: ",
            style = MaterialTheme.typography.body2,
        )
        Text(
            text = "x ",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun Steps() {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(bottom = 2.dp)
    ) {
        Text(
            text = "Total steps recorded: ",
            style = MaterialTheme.typography.body2,
        )
        Text(
            text = "xxxx ",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun Distance() {
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
}

@Composable
fun AVGraph(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(color = MaterialTheme.colors.onSecondary),
                    LinePlot.Intersection(color = MaterialTheme.colors.primary),
                    LinePlot.Highlight(color = Blue1),
                )
            ),
            xAxis = LinePlot.XAxis(steps = 5, unit = 0.35f, roundToInt = true),
            yAxis = LinePlot.YAxis(steps = 4, roundToInt = false),
            grid = LinePlot.Grid(Gray, steps = 4),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(start = 10.dp, end = 50.dp),
    )
}

@Composable
fun GraphSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        AvgVelocity()
        AvgHeartRate()
    }
}

@Composable
fun AvgVelocity() {
        val testData = listOf(
        DataPoint(1.toFloat(), 3.toFloat()),
        DataPoint(2.toFloat(), 2.5.toFloat()),
        DataPoint(3.toFloat(), 1.7.toFloat()),
        DataPoint(4.toFloat(), 3.5.toFloat()),
        DataPoint(5.toFloat(), 2.4.toFloat()),
    )
    Column {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "Average velocity of past 5 exercises: ",
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = "xxxx ",
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = "m/s",
                style = MaterialTheme.typography.body2,
            )
        }
    }
    AVGraph(lines = listOf(testData))
}

@Composable
fun AvgHeartRate() {
    val testData = listOf(
        DataPoint(1.toFloat(), 160.toFloat()),
        DataPoint(2.toFloat(), 167.toFloat()),
        DataPoint(3.toFloat(), 182.toFloat()),
        DataPoint(4.toFloat(), 173.toFloat()),
        DataPoint(5.toFloat(), 187.toFloat()),
    )
    Column {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "Average heart rate of past 5 exercises: ",
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = "xx ",
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = "bpm",
                style = MaterialTheme.typography.body2,
            )
        }
    }
    AVGraph(lines = listOf(testData))
}

//Move to backend file / database file later
data class RunRecord(
    // ID for database ?? idk if it is needed or not
    val id: Int,
    // Date of the record
    val date: String,
    // Temperature of the record starting time (Int/Float) I prefer metres but u can use km if u want
    val temp: Int,
    // Total number of steps
    val steps: Int,
    // Total distance ran in the record (Int/Float)
    val totalDistance: Int,
    // Starting time of the record (Float/String/any time format) I can toString everything cuz this is for display only
    val startTime: String,
    // Ending time of the record (Float/String/any time format)
    val endTime: String,
    // Total time spend of the record (Float?/wtever u store, .tostring)
    val timeSpent: String,
    // Average velocity (m/s)
    val avgVelocity: Double,
    // List of average velocity with TimeStamp or StopwatchStamp --- Eg. [(velocity, minute:seconds), (2.3, 01:00), (2.5, 01:30), (3.1, 02:00)]
    // Or with metres... record once every 100 metres? --- Eg. [(2.1, 100), (2.6, 200), (2.4, 300)]
    // Record every 30 second after the first 1 minute?? (just suggestions)
    // I put Int for temporary use
    // These list r for graphs, if too complicated then we cut it.
    val avgVelocityList: List<Int>,
    // Average heart rate (bpm)
    val avgHeart: Double,
    // List of average heart rate for graph
    val avgHeartList: List<Int>,
    // Calories
    val calories: Int,
    // List of coordinates --- Eg. [(Lat, Long), (60.1234, 25.4321), (60.1236, 25.4322)]
    // Those recorded one by one during the run, so we dont need time... i guess...
    // For creating track on map... idk how 0.0
    val coordinates: List<Int>
)

@Composable
fun Histories(viewModel: StatViewModel = viewModel(), navController: NavController) {
//    val historyList = viewModel.getAll().observeAsState(listOf())
    val historyList = listOf(
        RunRecord(1, "05/10/2022", 10, 10000, 948, "10:00", "10:48", "48", 8.0, listOf(8,9,8,7,8,6,9,8), 175.0, listOf(171,172,173,174,175), 475, listOf(1,2,3,4,5,6)),
        RunRecord(2, "06/10/2022", 11, 9000, 848, "10:00", "10:48", "48", 8.0, listOf(8,9,8,7,8,6,9,8), 175.0, listOf(171,172,173,174,175), 475, listOf(1,2,3,4,5,6)),
        RunRecord(3, "07/10/2022", 12, 8888, 666, "10:00", "10:48", "48", 8.0, listOf(8,9,8,7,8,6,9,8), 175.0, listOf(171,172,173,174,175), 475, listOf(1,2,3,4,5,6)),
        RunRecord(4, "08/10/2022", 13, 8787, 487, "10:00", "10:48", "48", 8.0, listOf(8,9,8,7,8,6,9,8), 175.0, listOf(171,172,173,174,175), 475, listOf(1,2,3,4,5,6)),
    )
    LazyColumn(
        modifier = Modifier
            .height(500.dp)
            .fillMaxWidth()
            .padding(15.dp)
            ) {
        item {
            Row {
                Text(
                    text = stringResource(id = R.string.history_list_title),
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }
        items(historyList) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .border(2.dp, Orange1, CutCornerShape(15.dp, 8.dp, 15.dp, 8.dp))
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable {
                        navController.navigate("statsDetail/${it.id}")
                    }
            ) {
                // Delete button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Sharp.DeleteForever,
                        contentDescription = "Localized description",
                        tint = Orange2,
                        modifier = Modifier
                            .clickable {
//                                viewModel.delete(it.id)
                            },
                    )
                }
                // Temperature
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(end = 10.dp)
                    ) {
                        Text(
                            text = it.temp.toString(),
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            text = "°c",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
                Column {
                    // Date
                    Row {
                        Text(
                            text = it.date,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    // Time and Duration
                    Row(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 2.dp)
                    ) {
                        Text(
                            text = it.startTime,
                            style = MaterialTheme.typography.body2,
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Row(
                            verticalAlignment = Alignment.Bottom,
                        ) {
                            Text(
                                text = it.timeSpent,
                                style = MaterialTheme.typography.body2,
                            )
                            Text(
                                text = " mins",
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                    //
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = it.totalDistance.toString(),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSecondary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = " meters",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    }
}