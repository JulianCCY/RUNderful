package com.example.running_app.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.running_app.R
import com.example.running_app.viewModels.GoalsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.ui.theme.Orange1

@Composable
fun GoalsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoalsTitle()
        GoalsSection()
    }
}

@Composable
fun GoalsTitle() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.goals),
            style = MaterialTheme.typography.h2
        )
    }
}

@Composable
fun GoalsSection(viewModel: GoalsViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        TotalDistanceGoal()
        VelocityGoal()
        ContinuousGoal()
        ExercisesGoal()
    }
}

@Composable
fun TotalDistanceGoal() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .border(2.dp, Orange1, CutCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Icon(
            Icons.Sharp.DirectionsRun,
            contentDescription = "Run",
            tint = Orange1,
            modifier = Modifier
                .size(60.dp)
                .padding(start = 10.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Total running distance",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = 0.1f,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "0.1/1 ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = "Km",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun VelocityGoal() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .border(2.dp, Orange1, CutCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Icon(
            Icons.Sharp.Speed,
            contentDescription = "Speed",
            tint = Orange1,
            modifier = Modifier
                .size(60.dp)
                .padding(start = 10.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Reach 5 meters per second once",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = 0.54f,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "2.7/5 ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = "m/s",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun ContinuousGoal() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .border(2.dp, Orange1, CutCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Icon(
            Icons.Sharp.CalendarMonth,
            contentDescription = "Calendar",
            tint = Orange1,
            modifier = Modifier
                .size(60.dp)
                .padding(start = 10.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Exercise for 5 days continuously",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = 0.2f,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "1/5 ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = "days",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun ExercisesGoal() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .border(2.dp, Orange1, CutCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Icon(
            Icons.Sharp.WorkspacePremium,
            contentDescription = "Medal",
            tint = Orange1,
            modifier = Modifier
                .size(60.dp)
                .padding(start = 10.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Record for a total of 50 times",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = 0.02f,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "1/50 ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = "times",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}