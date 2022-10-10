package com.example.running_app.views

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.running_app.data.TrackSuggestionStructure
import com.example.running_app.ui.theme.LightViolet1
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.viewModels.TrackSuggestionViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng

@Composable
fun TrackSuggestionScreen(viewModel: TrackSuggestionViewModel = viewModel() ) {

    val itemIds by viewModel.itemIds.collectAsState()

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            itemsIndexed(viewModel.items.value) { index, item ->
                SuggestionContainer(
                    model = item,
                    onClickItem = { viewModel.onItemClicked(index) },
                    expanded = itemIds.contains(index)
                )
            }
        }
    }
}

@Composable
fun SuggestionContainer(model: TrackSuggestionStructure, onClickItem: () -> Unit, expanded: Boolean) {
    Box {
        Column {
            SuggestionHeader(title = model.title, distance = model.distance, onClickItem = onClickItem)
            ExpandableView(center = model.center, coords = model.coords, isExpanded = expanded)
        }
    }
}

@Composable
fun SuggestionHeader(title: String, distance: Int, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .border(2.dp, Orange1)
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                onClick = onClickItem
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
            )
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = "~ $distance",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSecondary,
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
fun ExpandableView(center: LatLng, coords: List<LatLng>, isExpanded: Boolean) {
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }
    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        plotMap(focus = center, coords = coords)
    }
}