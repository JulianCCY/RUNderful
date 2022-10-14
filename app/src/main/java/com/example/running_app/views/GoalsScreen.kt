package com.example.running_app.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
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
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        TotalDistanceGoal(viewModel)
        TotalStepsGoal(viewModel)
        TotalHours(viewModel)
        VelocityGoal(viewModel)
        StrideLengthGoal(viewModel)
        CaloriesGoal(viewModel)
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
                text = "Total running distance[Lv.${viewModel.level[0]}]",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = ((viewModel.getTotalDistance()*1000).roundToInt()/1000.0/viewModel.target[0].toFloat()).toFloat(),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
                    .height(25.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = ((viewModel.getTotalDistance()*1000).roundToInt()/1000.0).toString()+ "/"+ viewModel.target[0] + " ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = "KM",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Composable
fun TotalStepsGoal(viewModel: GoalsViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .border(2.dp, Orange1, CutCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Icon(
            Icons.Sharp.DirectionsWalk,
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
                text = "Total steps [Lv.${viewModel.level[1]}]",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (viewModel.getTotalSteps()/(viewModel.target[1]).toFloat()),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
                    .height(25.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = viewModel.getTotalSteps().toString() + "/"+ viewModel.target[1]*(viewModel.level[1]+1) + " ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " Steps",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Composable
fun TotalHours(viewModel: GoalsViewModel){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .border(2.dp, Orange1, CutCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Icon(
            Icons.Sharp.Schedule,
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
                text = "Total running hours [Lv.${viewModel.level[2]}]" ,
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (viewModel.getTotalHours()/viewModel.target[2].toFloat()),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
                    .height(25.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = viewModel.getTotalHours().toString()+ "/"+ viewModel.target[2] + " ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " Hours",
                    style = MaterialTheme.typography.body1,
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
                text = "Reach avg. speed 5 m/s [Lv.${viewModel.level[3]}]",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (((viewModel.getHighestVelocity()*100).roundToInt())/100.0/(viewModel.target[3]*(viewModel.level[3]+1)).toFloat()).toFloat(),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
                    .height(25.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = (((viewModel.getHighestVelocity()*100).roundToInt())/100.0).toString() + "/"+ viewModel.target[3]*(viewModel.level[3]+1) + " ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " M/S",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Composable
fun StrideLengthGoal(viewModel: GoalsViewModel){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .border(2.dp, Orange1, CutCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Icon(
            Icons.Sharp.Straighten,
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
                text = "Reach avg. stride length 3 m [Lv.${viewModel.level[4]}]",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (((viewModel.getHighestStrideLength()*100).roundToInt())/100.0/(viewModel.target[4]*(viewModel.level[4]+1)).toFloat()).toFloat(),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
                    .height(25.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = (((viewModel.getHighestStrideLength()*100).roundToInt())/100.0).toString() + "/"+ viewModel.target[4]*(viewModel.level[4]+1) + " ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " M",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }

}

@Composable
fun CaloriesGoal(viewModel: GoalsViewModel) {
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
                text = "Burn 10000 calories [Lv.${viewModel.level[5]}]",
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (viewModel.getCaloriesBurnt()/viewModel.target[5].toFloat()),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
                    .height(25.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = viewModel.getCaloriesBurnt().toString() + "/" + viewModel.target[5] + " ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " KCAL",
                    style = MaterialTheme.typography.body1,
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
                text = "Total running times [Lv.${viewModel.level[6]}]" ,
                style = MaterialTheme.typography.body1,
            )
            LinearProgressIndicator(
                progress = (viewModel.getTotalRecord()/viewModel.target[6].toFloat()),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(5.dp)
                    .height(25.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = viewModel.getTotalRecord().toString()+ "/"+ viewModel.target[6] + " ",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = " Times",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}