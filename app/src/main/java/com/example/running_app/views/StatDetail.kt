package com.example.running_app.views

import android.provider.ContactsContract.Data
import android.view.Display
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.running_app.viewModels.StatDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.ui.theme.Gray
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.ui.theme.Orange2

@Composable
fun StatDetail(dataId: Int, viewModel: StatDetailViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        DateDisplay()
        Spacer(modifier = Modifier.height(500.dp))
        StatDisplay()
    }
}

@Composable
fun DateDisplay() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "5/10/2022",
            style = MaterialTheme.typography.subtitle2,
        )
    }
}

@Composable
fun StatDisplay() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        TimeDisplay()
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            StepsDisplay()
            DistanceDisplay()
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AVDisplay()
            AHDisplay()
        }
    }
}

@Composable
fun TimeDisplay() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Sharp.Schedule,
                contentDescription = "Localized description",
                tint = Orange1,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "48",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " mins",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        Row {
            Text(
                text = "10:00 - 10:48",
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Composable
fun StepsDisplay() {
    Row {
        Icon(
            imageVector = Icons.Sharp.DirectionsRun,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(50.dp)
        )
       Column {
           Text(
               text = "Total steps",
               style = MaterialTheme.typography.body2,
               color = MaterialTheme.colors.onSecondary,
           )
           Row(
               verticalAlignment = Alignment.Bottom
           ) {
               Text(
                   text = "9999",
                   style = MaterialTheme.typography.body1,
               )
               Text(
                   text = " steps",
                   style = MaterialTheme.typography.body2,
               )
           }
       }
    }
}

@Composable
fun DistanceDisplay() {
    Row {
        Icon(
            imageVector = Icons.Sharp.LocationOn,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(50.dp)
        )
        Column {
            Text(
                text = "Total distance",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "4321",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " m",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun AVDisplay() {
    Row(
        verticalAlignment = Alignment.Bottom,
    ) {
        Icon(
            imageVector = Icons.Sharp.Speed,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 2.dp)
        )
        Column {
            Text(
                text = "Avg. velocity",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "3.4",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " m/s",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun AHDisplay() {
    Row(
        verticalAlignment = Alignment.Bottom,
    ) {
        Icon(
            imageVector = Icons.Sharp.MonitorHeart,
            contentDescription = "Localized description",
            tint = Orange1,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 2.dp)
        )
        Column {
            Text(
                text = "Avg. heart rate",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "170",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " bpm",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}