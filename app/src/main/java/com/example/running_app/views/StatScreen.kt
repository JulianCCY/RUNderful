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
import com.google.android.gms.maps.model.LatLng
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

data class StatOverviewForUI(
    val userName: String,
    val numOfExercises: Int,
    val totalSteps: Int,
    val totalDistance: Int,
    val speedOfLastFive: List<Double>,
    val heartOfLastFive: List<Int>,
)

//Move to backend file / database file later
data class RunRecordForUI(
    val id: Long,
    // Date of the record
    val date: String,
    // Starting time of the record
    val startTime: String,
    // Ending time of the record
    val endTime: String,
    // Total time spend of the record
    val timeSpent: String,
    // Temperature of the record starting time
    val temp: Int,
    // Weather desc for icon
    val weatherDesc: String,
    // Total number of steps
    val steps: Int,
    // Total distance ran in the record
    val distance: Int,
    // Average velocity (m/s)
    val avgSpeed: Double,
    // Average heart rate (bpm)
    val avgHeart: Double,
    // Calories
    val calories: Int,
    // Stride
    val stride: Double,
    // List of coordinates --- Eg. [(Lat, Long), (60.1234, 25.4321), (60.1236, 25.4322)]
    // For creating track on map...
    val coordinates: List<LatLng>
)

@Composable
fun StatScreen(navController: NavController) {
    // Fake data for UI
    val overViewFakeData = StatOverviewForUI(
        "username", 5, 12345, 8102, listOf(3.0,2.5,1.4,3.5,2.4), listOf(160, 164, 183, 172, 178)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        StatTitle()
        OverviewBox(overViewFakeData)
        GraphSection(overViewFakeData)
        Histories(navController = navController)
    }
}

@Composable
fun StatTitle() {
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
fun OverviewBox(data: StatOverviewForUI) {
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
        OverviewData(data)
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
fun OverviewData(data: StatOverviewForUI) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 20.dp)
//            .border(width = 2.dp, color = Orange1, CutCornerShape(60.dp, 10.dp, 60.dp, 10.dp))
            .height(200.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.hi),
                    style = MaterialTheme.typography.body2,
                )
                Text(
                    text = " ${data.userName}!",
                    style = MaterialTheme.typography.body1,
                )
            }
            ExercisesCount(data)
            Steps(data)
            Distance(data)
        }
    }
}

@Composable
fun ExercisesCount(data: StatOverviewForUI) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(bottom = 2.dp)
    ) {
        Text(
            text = stringResource(R.string.number_of_exercises_recorded),
            style = MaterialTheme.typography.body2,
        )
        Text(
            text = " ${data.numOfExercises}",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun Steps(data: StatOverviewForUI) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(bottom = 2.dp)
    ) {
        Text(
            text = stringResource(R.string.total_steps_recorded),
            style = MaterialTheme.typography.body2,
        )
        Text(
            text = " ${data.totalSteps}",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun Distance(data: StatOverviewForUI) {
    Row(
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = stringResource(R.string.total_distance_exercised),
            style = MaterialTheme.typography.body2,
        )
        Text(
            text = " ${data.totalDistance}",
            style = MaterialTheme.typography.body1,
        )
        Text(
            text = " m",
            style = MaterialTheme.typography.body2,
        )
    }
}

@Composable
fun PlotGraph(lines: List<List<DataPoint>>) {
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
fun GraphSection(data: StatOverviewForUI) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        AvgVelocity(data)
        AvgHeartRate(data)
    }
}

@Composable
fun AvgVelocity(data: StatOverviewForUI) {
    val graphData = listOf(
        DataPoint(1.toFloat(), data.speedOfLastFive[0].toFloat()),
        DataPoint(2.toFloat(), data.speedOfLastFive[1].toFloat()),
        DataPoint(3.toFloat(), data.speedOfLastFive[2].toFloat()),
        DataPoint(4.toFloat(), data.speedOfLastFive[3].toFloat()),
        DataPoint(5.toFloat(), data.speedOfLastFive[4].toFloat()),
    )
    val calculated = (data.speedOfLastFive.sum() / 5)

    Column {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = stringResource(R.string.avg_speed_of_past_five_exercises),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = " $calculated",
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = " m/s",
                style = MaterialTheme.typography.body2,
            )
        }
    }
    PlotGraph(lines = listOf(graphData))
}

@Composable
fun AvgHeartRate(data: StatOverviewForUI) {
    val testData = listOf(
        DataPoint(1.toFloat(), data.heartOfLastFive[0].toFloat()),
        DataPoint(2.toFloat(), data.heartOfLastFive[1].toFloat()),
        DataPoint(3.toFloat(), data.heartOfLastFive[2].toFloat()),
        DataPoint(4.toFloat(), data.heartOfLastFive[3].toFloat()),
        DataPoint(5.toFloat(), data.heartOfLastFive[4].toFloat()),
    )
    val calculated = (data.heartOfLastFive.sum() / 5)
    Column {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = stringResource(R.string.avg_heart_of_past_five_exercises),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = " $calculated",
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = " bpm",
                style = MaterialTheme.typography.body2,
            )
        }
    }
    PlotGraph(lines = listOf(testData))
}

@Composable
fun Histories(viewModel: StatViewModel = viewModel(), navController: NavController) {
//    val historyList = viewModel.getAll().observeAsState(listOf())
    val historyList = listOf(
        RunRecordForUI(1, "05-10-2022", "10:00", "10:48", "00:15:31", 13, "Foggy", 9876, 4, 8.0,175.0, 3, 0.52, listOf(
            LatLng(60.178152, 24.989714),
            LatLng(60.178347, 24.991572),
            LatLng(60.178559, 24.992468),
            LatLng(60.178808, 24.993152),
            LatLng(60.178970, 24.994139),
            LatLng(60.179276, 24.996648),
            LatLng(60.179540, 24.997403),
            LatLng(60.180113, 24.998110),
            LatLng(60.180294, 24.999510),
            LatLng(60.180373, 25.000382),
        )),
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
                // Temperature / weather icon later
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
                    // Distance
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = it.distance.toString(),
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