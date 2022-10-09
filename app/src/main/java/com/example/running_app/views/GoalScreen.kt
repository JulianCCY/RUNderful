package com.example.running_app.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.running_app.R
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.ui.theme.Orange2
import com.example.running_app.viewModels.StatViewModel

@Composable
fun GoalScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Goals(navController = navController)
    }
}

data class GoalRecord(
    // ID for database ?? idk if it is needed or not
    val id: Int,
    // Date of the record
    val progress: Int,
    val max_progess: Int,
    // Temperature of the record starting time (Int/Float) I prefer metres but u can use km if u want
    val description: String
)


@Composable
fun Goals(viewModel: StatViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), navController: NavController) {
//    val historyList = viewModel.getAll().observeAsState(listOf())
    val GoalList = listOf(
        GoalRecord(1, 32, 100 ,"Run 100 km"),
        GoalRecord(2, 10, 30,"Run Continuously for a month"),
        GoalRecord(3, 35692, 100000 , "Walk 100000 steps" ),
        GoalRecord(4, 263,1000,"Burn 1000 calories"),
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
                    text = stringResource(id = R.string.Goal_list_title),
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }
        items(GoalList) {
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

                Column {
                    // Date
                    Row {
                        Text(
                            text = it.description,
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
                            text = it.progress.toString() + " / " + it.max_progess.toString() ,
                            style = MaterialTheme.typography.body2,
                        )
                    }

                }
                    //

                }
            }
        }
    }