package com.example.running_app.views

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import com.example.running_app.viewModels.StatDetailViewModel
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import kotlin.math.roundToInt

@Composable
fun StatScreen(
    navController: NavController,
    stats: StatViewModel = viewModel(),
    statsD: StatDetailViewModel = viewModel(),
    settings: SettingsViewModel = viewModel(),
) {
    // get username
    val userName = settings.getUser().observeAsState().value?.name
    // get num of exercise, total steps of all time, total distance of all time'
    val numOfExercises = stats.getNumExe().observeAsState().value
    val totalSteps = stats.getTS().observeAsState().value
    val totalDistance = stats.getTD().observeAsState().value?.roundToInt()

    // average speed of last 5 record
    val lastFiveAvgSpeed = if (!stats.getL5AS().observeAsState().value.isNullOrEmpty()) stats.getL5AS().observeAsState().value else listOf(0.0)
    // average heart rate of last 5 record
    val lastFiveAvgHR = if(!stats.getL5HR().observeAsState().value.isNullOrEmpty()) stats.getL5HR().observeAsState().value else listOf(0)
    // total steps of last 2 record
    val lastTwoSteps = if (!statsD.compareTwoLatestSteps().observeAsState().value.isNullOrEmpty()) statsD.compareTwoLatestSteps().observeAsState().value else listOf(0,0)
    // total steps of latest and average
    val latestSteps = if (statsD.getLatestSteps().observeAsState().value != null) statsD.getLatestSteps().observeAsState().value else 0
    val avgSteps = if (statsD.getAverageSteps().observeAsState().value != null) statsD.getAverageSteps().observeAsState().value else 0
    // latest and average distance
    val latestDistance = if (statsD.getLatestDistance().observeAsState().value != null) statsD.getLatestDistance().observeAsState().value else 0.0
    val avgDistance = if (statsD.getAverageDistance().observeAsState().value != null) statsD.getAverageDistance().observeAsState().value else 0.0
    // latest speed, two latest speed, average speed
    val latestSpeed = if (statsD.getLatestSpeed().observeAsState().value != null) statsD.getLatestSpeed().observeAsState().value else 0.0
    val lastTwoSpeed = if (!statsD.compareTwoLatestSpeed().observeAsState().value.isNullOrEmpty()) statsD.compareTwoLatestSpeed().observeAsState().value else listOf(0.0, 0.0)
    val avgSpeed = if (statsD.getAverageSpeed().observeAsState().value != null) statsD.getAverageSpeed().observeAsState().value else 0.0

    val lastTwoCalories = if (!statsD.compareTwoLatestCalories().observeAsState().value.isNullOrEmpty()) statsD.compareTwoLatestCalories().observeAsState().value else listOf(0, 0)

    val generalData = StatGeneral(
        userName ?: "Username",
        numOfExercises ?: 0,
        totalSteps ?: 0,
        totalDistance ?: 0,
    )

    val graphData = GraphStatsData(
        lastFiveAvgSpeed ?: listOf(0.0),
        lastFiveAvgHR ?: listOf(0),
        lastTwoSteps ?: listOf(0, 0),
        latestSteps ?: 0,
        avgSteps ?: 0,
        latestDistance ?: 0.0,
        avgDistance ?: 0.0,
        latestSpeed ?: 0.0,
        lastTwoSpeed ?: listOf(0.0, 0.0),
        avgSpeed ?: 0.0,
        lastTwoCalories ?: listOf(0, 0)

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StatTitle()
        OverviewBox(generalData)
        StatSwitch(stats)
        Stat(stats, navController, graphData)
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
            .height(150.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.hi),
                    style = MaterialTheme.typography.body1,
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
            style = MaterialTheme.typography.body1,
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
            style = MaterialTheme.typography.body1,
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
            style = MaterialTheme.typography.body1,
        )
        Text(
            text = " ${data.totalDistance}",
            style = MaterialTheme.typography.body1,
        )
        Text(
            text = " M",
            style = MaterialTheme.typography.body2,
        )
    }
}


@Composable
fun StatSwitch(statViewModel: StatViewModel) {
    val switch by remember { statViewModel.switch }
    Column (
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Text(
                text = "Record History",
                style = MaterialTheme.typography.body1,
                fontWeight = if (switch == "records") FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .selectable(
                        selected = true,
                        onClick = { statViewModel.switch.value = "records" }
                    )
            )
            Text(
                text = "Data Insight",
                style = MaterialTheme.typography.body1,
                fontWeight = if (switch == "insight") FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .selectable(
                        selected = true,
                        onClick = {
                            statViewModel.switch.value = "insight"
                        }
                    )
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Divider(
            color = Orange1,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun Stat(statViewModel: StatViewModel, navController: NavController, graphData: GraphStatsData){
    val switch by remember {statViewModel.switch}
    if (switch == "records") {
        Histories(navController = navController)
    }
    if (switch == "insight") {
        GraphSection(graphData)
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
                    LinePlot.AreaUnderLine(color = Red1, 0.3f),
                ),
            ),

            xAxis = LinePlot.XAxis(steps = 5, unit = 0.35f, roundToInt = true),
            yAxis = LinePlot.YAxis(steps = 3, roundToInt = false),
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
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AvgVelocity(data)
        AvgHeartRate(data)
        StepsTitle(data)
        LastTwoSteps(data)
        LatestAvgSteps(data)
        DistanceTitle(data)
        LatestAvgDistance(data)
        SpeedTitle(data)
        LastTwoSpeed(data)
        LatestAvgSpeed(data)
        CaloriesTitle(data)
        LastTwoCalories(data)
    }
}

@Composable
fun StepsTitle(data: GraphStatsData){
    if (data.stepsOfLastTwo.sum()!= 0 || data.avgSteps != 0){
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = "Running Steps",
                style = MaterialTheme.typography.body1,
                color = LightOrange1
            )
        }
    }
}

@Composable
fun DistanceTitle(data: GraphStatsData){
    if (data.avgDistance != 0.0){
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = "Running Distance",
                style = MaterialTheme.typography.body1,
                color = LightOrange1
            )
        }
    }
}

@Composable
fun SpeedTitle(data: GraphStatsData){
    if (data.speedOfLastTwo.sum()!= 0.0 || (data.avgSpeed != 0.0 && data.latestSpeed != 0.0)){
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = "Running Speed",
                style = MaterialTheme.typography.body1,
                color = LightOrange1
            )
        }
    }

}

@Composable
fun CaloriesTitle(data: GraphStatsData){
    if (data.sumOfCaloriesOfLastTwo.sum() != 0){
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = "Active calories",
                style = MaterialTheme.typography.body1,
                color = LightOrange1
            )
        }
    }
}



@Composable
fun AvgVelocity(data: GraphStatsData) {
    if (data.speedOfLastFive.size >= 5 && data.speedOfLastFive.sum() != 0.0) {
        val graphData = data.speedOfLastFive.toList().reversed().mapIndexed{ i, e -> DataPoint(i.toFloat(), e.toFloat()) }
        val calculated = (data.speedOfLastFive.sum() / data.speedOfLastFive.size)

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = stringResource(R.string.avg_speed_of_past_five_exercises),
                    style = MaterialTheme.typography.body2,
                )
                Text(
                    text = " ${String.format("%.2f", calculated)}",
                    style = MaterialTheme.typography.body2,
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
    if (data.heartOfLastFive.size >= 5 && data.heartOfLastFive.sum() != 0) {
        val graphData = data.heartOfLastFive.toList().reversed().mapIndexed { i, e -> DataPoint(i.toFloat(), e.toFloat()) }
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
                    style = MaterialTheme.typography.body2,
                )
                Text(
                    text = " BPM",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        PlotGraph(lines = listOf(graphData))
    }
}

@Composable
fun LastTwoSteps(data: GraphStatsData){
    if (data.stepsOfLastTwo.sum()!= 0) {
        val latest = data.stepsOfLastTwo.first()
        val previous = data.stepsOfLastTwo.last()

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = if (latest > previous) "You took steps in latest run than last time"
            else "You took fewer steps in latest run than last time",
                style = MaterialTheme.typography.body2,

            )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$latest",
                    style = MaterialTheme.typography.body1,
                    color = Orange2
                )
                Text(
                    text = " steps(latest)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > previous) latest/latest.toFloat() else latest/previous.toFloat(),
                color = Orange2,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$previous",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " steps(previous)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > previous) previous/latest.toFloat() else previous/previous.toFloat(),
                color = Gray,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
        }
    }
}

@Composable
fun LatestAvgSteps(data: GraphStatsData){
    if (data.avgSteps != 0) {
        val latest = data.latestSteps
        val average = data.avgSteps

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = if (latest > average) "You took more steps in latest run than average time"
                else "You took fewer steps in latest run than average time",
                style = MaterialTheme.typography.body2,

                )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$latest",
                    style = MaterialTheme.typography.body1,
                    color = Orange2
                )
                Text(
                    text = " steps(latest)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > average) latest/latest.toFloat() else latest/average.toFloat(),
                color = Orange2,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$average",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " steps(average)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > average) average/latest.toFloat() else average/average.toFloat(),
                color = Gray,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )


        }
    }
}


@Composable
fun LatestAvgDistance(data: GraphStatsData){
    if (data.avgDistance != 0.0) {
        val latest = data.latestDistance
        val average = data.avgDistance

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = if (latest > average) "You took more distance in latest run than average time"
                else "You took fewer distance in latest run than average time",
                style = MaterialTheme.typography.body2,

                )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$latest",
                    style = MaterialTheme.typography.body1,
                    color = Orange2
                )
                Text(
                    text = " m(latest)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > average) latest.roundToInt()/latest.roundToInt().toFloat() else latest.roundToInt()/average.roundToInt().toFloat(),
                color = Orange2,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$average",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " m(average)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > average) latest.roundToInt()/latest.roundToInt().toFloat() else average.roundToInt()/average.roundToInt().toFloat(),
                color = Gray,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )


        }
    }
}

@Composable
fun LastTwoSpeed(data: GraphStatsData){
    if (data.speedOfLastTwo.sum()!= 0.0) {
        val latest = data.speedOfLastTwo.first()
        val previous = data.speedOfLastTwo.last()
        val l = (latest*1000).roundToInt()
        val p = (previous*1000).roundToInt()

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = if (latest > previous) "You ran faster in latest run than last time"
                else "You ran slower in latest run than last time",
                style = MaterialTheme.typography.body2,
                )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$latest",
                    style = MaterialTheme.typography.body1,
                    color = Orange2
                )
                Text(
                    text = " m/s(latest)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }

            LinearProgressIndicator(
                progress = if (latest > previous) l/l.toFloat() else l/p.toFloat(),
                color = Orange2,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$previous",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " m/s(previous)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > previous) p/l.toFloat() else p/p.toFloat(),
                color = Gray,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )


        }
    }
}

@Composable
fun LatestAvgSpeed(data: GraphStatsData){
    if (data.avgSpeed != 0.0 && data.latestSpeed != 0.0) {
        val latest = data.latestSpeed
        val average = data.avgSpeed
        val l = (latest*1000).roundToInt()
        val a = (average*1000).roundToInt()

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = if (latest > average) "You ran faster in latest run than average time"
                else "You're running slower in latest run than average time",
                style = MaterialTheme.typography.body2,
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$latest",
                    style = MaterialTheme.typography.body1,
                    color = Orange2
                )
                Text(
                    text = " m/s(latest)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }

            LinearProgressIndicator(
                progress = if (latest > average) l/l.toFloat() else l/a.toFloat(),
                color = Orange2,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$average",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " m/s(average)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > average) a/l.toFloat() else a/a.toFloat(),
                color = Gray,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
        }
    }
}

@Composable
fun LastTwoCalories(data: GraphStatsData){
    if (data.sumOfCaloriesOfLastTwo.sum() != 0){

        val latest = data.sumOfCaloriesOfLastTwo.first()
        val previous = data.sumOfCaloriesOfLastTwo.last()

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = if (latest > previous) "You burned more calories in latest training day than last time"
                else "You burned fewer calories in latest training day than last time",
                style = MaterialTheme.typography.body2,

                )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$latest",
                    style = MaterialTheme.typography.body1,
                    color = Orange2
                )
                Text(
                    text = " kcal(latest)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > previous) latest/latest.toFloat() else latest/previous.toFloat(),
                color = Orange2,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ){
                Text(
                    text = "$previous",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " kcal(previous)",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
            }
            LinearProgressIndicator(
                progress = if (latest > previous) previous/latest.toFloat() else previous/previous.toFloat(),
                color = Gray,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(3.dp)
                    .height(25.dp)
            )
        }

    }
}

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
                            modifier = Modifier.clickable {
                                viewModel.delARecord(it.id)
                            },
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