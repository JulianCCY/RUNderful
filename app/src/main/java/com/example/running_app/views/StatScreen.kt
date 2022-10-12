package com.example.running_app.views

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.running_app.data.result.RunRecordForUI
import com.example.running_app.data.stats.GraphStatsData
import com.example.running_app.data.stats.StatGeneral
import com.example.running_app.viewModels.SettingsViewModel
import com.google.android.gms.maps.model.LatLng
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

//Move to backend file / database file later
// Moved your data class to data folder -> result and stats

@Composable
fun StatScreen(
    navController: NavController,
    stats: StatViewModel = viewModel(),
    settings: SettingsViewModel = viewModel(),
) {
    val TAG = "stats screen"
    // get username
    val userName = settings.getUser().observeAsState().value?.name
    Log.d(TAG, "username: $userName")
    // get num of exercise, total steps of all time, total distance of all time'
    val numOfExercises = stats.getNumExe().absoluteValue
    Log.d(TAG, "exercise: $numOfExercises")
    val totalSteps = stats.getTS().absoluteValue
    Log.d(TAG, "total steps: $totalSteps")
    val totalDistance = stats.getTD().absoluteValue.roundToInt()
    Log.d(TAG, "distance: $totalDistance")

    // average speed of last 5 record
//    val lastFiveAvgSpeed = if(!running.getL5AS().observeAsState().value.isNullOrEmpty()) running.getL5AS().observeAsState().value!! else listOf<Double>()
//    Log.d(TAG, "l5avg speed, $lastFiveAvgSpeed")
//
//    // average heart rate of last 5 record
//    val lastFiveAvgHR = if(!running.getL5HR().observeAsState().value.isNullOrEmpty())running.getL5HR().observeAsState().value!! else listOf<Int>()
//    Log.d(TAG, "l5avg heart $lastFiveAvgHR")


    val generalData = StatGeneral(
        userName ?: "Username",
        numOfExercises,
        totalSteps,
        totalDistance,
//        listOf(3.0,2.5,1.4,3.5,2.4),
//        listOf(160, 164, 183, 172, 178)
    )

//    val graphData = StatGraph(
//        lastFiveAvgSpeed,
//        lastFiveAvgHR,
//    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        StatTitle()
        OverviewBox(generalData)
//        GraphSection(graphData)
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
fun OverviewBox(data: StatGeneral) {
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

// number of exe, total steps, total distance
@Composable
fun OverviewData(data: StatGeneral) {
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
fun ExercisesCount(data: StatGeneral) {
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
fun Steps(data: StatGeneral) {
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
fun Distance(data: StatGeneral) {
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
fun GraphSection(data: GraphStatsData) {
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
fun AvgVelocity(data: GraphStatsData) {
    if (data.speedOfLastFive.isEmpty()) {

        val graphData = data.speedOfLastFive.toList().mapIndexed{ i, e -> DataPoint(i.toFloat(), e.toFloat()) }
//        val graphData = listOf(
//            DataPoint(1.toFloat(), data.speedOfLastFive[0].toFloat()),
//            DataPoint(2.toFloat(), data.speedOfLastFive[1].toFloat()),
//            DataPoint(3.toFloat(), data.speedOfLastFive[2].toFloat()),
//            DataPoint(4.toFloat(), data.speedOfLastFive[3].toFloat()),
//            DataPoint(5.toFloat(), data.speedOfLastFive[4].toFloat()),
//        )
        val calculated = (data.speedOfLastFive.sum() / data.speedOfLastFive.size)

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
}

@Composable
fun AvgHeartRate(data: GraphStatsData) {

    if (data.heartOfLastFive.isEmpty()) {
        val graphData = data.heartOfLastFive.toList().mapIndexed { i, e -> DataPoint(i.toFloat(), e.toFloat()) }

//        val testData = listOf(
//            DataPoint(1.toFloat(), data.heartOfLastFive[0].toFloat()),
//            DataPoint(2.toFloat(), data.heartOfLastFive[1].toFloat()),
//            DataPoint(3.toFloat(), data.heartOfLastFive[2].toFloat()),
//            DataPoint(4.toFloat(), data.heartOfLastFive[3].toFloat()),
//            DataPoint(5.toFloat(), data.heartOfLastFive[4].toFloat()),
//        )
        val calculated = (data.heartOfLastFive.sum() / data.heartOfLastFive.size)
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
        PlotGraph(lines = listOf(graphData))
    }
}

//listOf(
//            LatLng(60.178152, 24.989714),
//            LatLng(60.178347, 24.991572),
//            LatLng(60.178559, 24.992468),
//            LatLng(60.178808, 24.993152),
//            LatLng(60.178970, 24.994139),
//            LatLng(60.179276, 24.996648),
//            LatLng(60.179540, 24.997403),
//            LatLng(60.180113, 24.998110),
//            LatLng(60.180294, 24.999510),
//            LatLng(60.180373, 25.000382),


@Composable
fun Histories(viewModel: StatViewModel = viewModel(), navController: NavController) {

    val tag = "history"

    val getAllRecord = viewModel.getAllRecords().observeAsState().value?.toList()?.map {
        RunRecordForUI(
            it.rid,
            it.date,
            it.startTime,
            it.endTime,
            it.duration,
            it.temperature,
            it.weatherDesc,
            it.totalStep,
            it.distance,
            it.avgSpeed,
            it.avgHR,
            it.calories,
            it.avgStrideLength,
            // avoid unnecessary data flow
            listOf()
        )
    }
    Log.d(tag, "record $getAllRecord")

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

        if (getAllRecord != null) {
            items(getAllRecord) {
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
                            modifier = Modifier.clickable { viewModel.delARecord(it.id) },
                        )
                    }
                    // Temperature / weather icon later
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .padding(end = 10.dp)
                        ) {
                            Text(
                                text = it.temp.toString(),
                                style = MaterialTheme.typography.body2,
                            )
                            Text(
                                text = "°c  ",
                                style = MaterialTheme.typography.body2,
                            )
                            WeatherIconByDesc(it.weatherDesc)
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
                                val timeController = it.timeSpent.split(":")
                                val minute = timeController[0].toInt()
                                val second = timeController[1].toInt()
                                val hour = String.format("%.1f", minute.toDouble() / 60)

                                Text(
                                    text = if (minute > 60) hour else if (minute < 1) "$second" else "$minute",
                                    style = MaterialTheme.typography.body2,
                                )
                                Text(
                                    text = if (minute > 60) " hour(s)" else if (minute < 1) " seconds" else " minutes",
                                    style = MaterialTheme.typography.body2,
                                )
                            }
                        }
                        // Distance
                        Row(
                            verticalAlignment = Alignment.Bottom,
                        ) {
                            Text(
                                text = if (it.distance < 1000.0) String.format("%.2f", it.distance) else String.format("%.2f", it.distance / 1000.0),
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSecondary,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = if (it.distance > 1000.0) " kilometers" else " meters",
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                }
            }
        }
    }
}