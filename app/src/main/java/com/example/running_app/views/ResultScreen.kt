package com.example.running_app.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.running_app.viewModels.ResultViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.ui.theme.Orange1
import com.google.android.gms.maps.model.LatLng
import com.example.running_app.R

@Composable
fun ResultScreen(navController: NavController, viewModel: ResultViewModel = viewModel()) {
//    val data = viewModel.getSummary().observeAsState()
    // fake data
    val data = RunRecordForUI(1, "05-10-2022", "10:00", "10:48", "00:15:31", 13, "Foggy", 9876, 4, 8.0,175.0, 3, 0.52, listOf(
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
    ))
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
        PlotMapWithStartEnd(startCoord = data.coordinates.first(), endCoord = data.coordinates.last(), coords = data.coordinates)
        StatDisplay(data)
        MainScreenButton(navController)
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
//                tint = MaterialTheme.colors.primaryVariant,
//                modifier = Modifier
//                    .size(20.dp)
            )
            Text(text = "Back to main screen")
        }
    }
}