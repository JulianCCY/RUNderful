package com.example.running_app.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import kotlin.math.roundToInt

@Composable
fun GoalsScreen(goalsViewModel: GoalsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoalsTitle()
        GoalsSection(goalsViewModel)
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
        TotalDistanceGoal(viewModel)
        VelocityGoal(viewModel)
        ContinuousGoal(viewModel)
        ExercisesGoal(viewModel)
    }
}

@Composable
fun TotalDistanceGoal(viewModel: GoalsViewModel) {
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
                text = "Total running distance[Lv.${viewModel.level_list[0]}]",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = ((viewModel.get_total_distance()*1000).roundToInt()/1000.0/viewModel.max_list[0].toFloat()).toFloat(),
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
                    text = ((viewModel.get_total_distance()*1000).roundToInt()/1000.0).toString()+ "/"+ viewModel.max_list[0] + " ",
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
fun VelocityGoal(viewModel: GoalsViewModel) {
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
                text = "Reach 5 meters per second [Lv.${viewModel.level_list[1]}]",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (((viewModel.get_highest_velocity()*100).roundToInt())/100.0/(viewModel.max_list[1]*(viewModel.level_list[1]+1)).toFloat()).toFloat(),
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
                    text = (((viewModel.get_highest_velocity()*100).roundToInt())/100.0).toString() + "/"+ viewModel.max_list[1]*(viewModel.level_list[1]+1) + " ",
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
fun ContinuousGoal(viewModel: GoalsViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .border(2.dp, Orange1, CutCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Icon(
            Icons.Sharp.LocalFireDepartment,
            contentDescription = "Calories",
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
                text = "Burn 1000 kcal calories[Lv.${viewModel.level_list[2]}]",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (viewModel.get_calories_burnt()/viewModel.max_list[2].toFloat()).toFloat(),
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
                    text = viewModel.get_calories_burnt().toString() +"/" + viewModel.max_list[2] + " ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = "kcal",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun ExercisesGoal(viewModel: GoalsViewModel) {
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
                text = "Record for a total of 50 times[Lv.${viewModel.level_list[3]}]" ,
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (viewModel.get_total_record()/viewModel.max_list[3].toFloat()).toFloat(),
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
                    text = viewModel.get_total_record().toString()+ "/"+ viewModel.max_list[3] + " ",
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