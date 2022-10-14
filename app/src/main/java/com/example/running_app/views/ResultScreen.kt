package com.example.running_app.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.ui.theme.Orange1
import com.google.android.gms.maps.model.LatLng
import com.example.running_app.R
import com.example.running_app.data.result.RunRecordForUI
import com.example.running_app.viewModels.RunningViewModel
import kotlin.math.roundToInt

@Composable
fun ResultScreen(
    navController: NavController,
    viewModel: RunningViewModel = viewModel(),
) {

    val tag = "result screen"
    val record = viewModel.getRecordForResult().observeAsState().value
    Log.d(tag, "record $record")


    if (record != null){

        val route  = viewModel.getRouteForResult(record.rid).observeAsState().value?.toList()?.map { LatLng(it.latitude, it.longitude) }

        if (!route.isNullOrEmpty()) {
            Log.d(tag, "route 1 $route")

            val data = RunRecordForUI(
                record.rid,
                record.date,
                record.startTime,
                record.endTime,
                record.duration,
                record.temperature,
                record.weatherDesc,
                record.totalStep,
                record.distance,
                record.avgSpeed,
                record.avgHR,
                record.calories,
                record.avgStrideLength,
                route
            )

            Log.d(tag, "route 2 $route")

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Summary()
                DateDisplay(data)
                Divider(
                    thickness = 2.dp,
                    color = Orange1,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth()
                )
                PlotMapWithStartEnd(startCoord = route.first(), endCoord = route.last(), coords = route)
                StatDisplay(data)
                MainScreenButton(navController)
            }
        }
    }
}

@Composable
fun Summary() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.summary),
            style = MaterialTheme.typography.h2,
        )
    }
}

@Composable
fun MainScreenButton(navController: NavController) {
    Button(onClick = { navController.navigate("main") }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Sharp.KeyboardArrowLeft,
                contentDescription = "ArrowLeft",
            )
            Text(text = "Back to main screen")
        }
    }
}