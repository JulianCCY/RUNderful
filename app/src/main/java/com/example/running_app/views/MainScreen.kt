package com.example.running_app.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.running_app.R
import com.example.running_app.data.running.heartrate.BLEViewModel
import com.example.running_app.views.utils.FeaturesUI
import com.example.running_app.ui.theme.*
import com.example.running_app.viewModels.SettingsViewModel
import com.example.running_app.views.utils.quadFromTo
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    settingsViewModel.getUserWeight()
    Box(
        modifier = Modifier
//            .background(Light)
            .fillMaxSize()
    ) {
        Column {
            Setting(navController)
            Greetings()
            Quotes()
            FeatureSection(
                features = listOf(
                    FeaturesUI("Weather", Icons.Sharp.WbSunny, LightGreen1, LightGreen2, LightGreen3, 1),
                    FeaturesUI("Track Suggestions", Icons.Sharp.FollowTheSigns, LightYellow1, LightYellow2, LightYellow3, 2),
                    FeaturesUI("Statistics", Icons.Sharp.Insights, LightViolet1, LightViolet2, LightViolet3, 3),
                    FeaturesUI("Goals", Icons.Sharp.EmojiEvents, LightOrange1, LightOrange2, LightOrange3, 4),
                ),
                navController
            )
        }
    }
}

@Composable
fun Setting(navController: NavController, bleViewModel: BLEViewModel = viewModel()) {

    val getIsConnected by bleViewModel.isConnected.observeAsState()
    val isConnected = getIsConnected

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 18.dp)
    ) {
        IconButton(
            onClick = {
//                bleViewModel.scanDevices()
                navController.navigate("settings")
            }
        ) {
            Icon(
//                if (isConnected == true) Icons.Sharp.BluetoothConnected else Icons.Sharp.Bluetooth,
                Icons.Sharp.Settings,
                contentDescription = "Settings",
                tint = Orange1,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun Greetings() {
    val sdf1 = SimpleDateFormat("dd - MM - yyyy")
    val sdf2 = SimpleDateFormat("HH:mm")
    val date = sdf1.format(Date())
    val time = sdf2.format(Date())
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ) {
        Column {
            Text(
                text = date,
                fontFamily = FontFamily(Font(R.font.leaguegothic_regular)),
                fontSize = 24.sp,
            )
            Text(
                text = time,
                fontFamily = FontFamily(Font(R.font.leaguegothic_regular)),
                fontSize = 96.sp,
            )
        }
    }
}

@Composable
fun Quotes() {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "What a nice day to exercise.",
            style = MaterialTheme.typography.subtitle2
        )
    }
}
@Composable
fun FeatureSection(features: List<FeaturesUI>, navController: NavController) {
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            FeatureGrids(features = features, navController)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                        shape = CutCornerShape(50.dp)
                        clip = true
                    }
                    .background(Orange1)
                    .selectable(
                        selected = true,
                        onClick = {
                            navController.navigate("startRunning")
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    )
            ) {
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun FeatureGrids(features: List<FeaturesUI>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(features.size) {
            FeatureItem(feature = features[it], navController)
        }
    }
}

@Composable
fun FeatureItem(
    feature: FeaturesUI,
    navController: NavController
) {
    when (feature.type) {
        1 -> {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(10.dp)
                    .aspectRatio(1f)
                    .graphicsLayer {
                        shadowElevation = 4.dp.toPx()
                        shape = CutCornerShape(20.dp, 0.dp, 80.dp, 0.dp)
                        clip = true
                    }
                    .background(feature.darkColor)
                    .selectable(
                        selected = true,
                        onClick = { navController.navigate("weather") }
                    )
            ) {
                val width = constraints.maxWidth
                val height = constraints.maxHeight

                // Medium Color points
                val mp1 = Offset(0f, height * 0.65f)
                val mp2 = Offset(width * 0.2f, height * 0.2f)
                val mp3 = Offset(width * 0.45f, height * 0.6f)
                val mp4 = Offset(width * 0.85f, height * 0.05f)
                val mp5 = Offset(width * 1.3f, height * 1.3f)

                val mediumColorPath = Path().apply {
                    moveTo(mp1.x, mp1.y)
                    quadFromTo(mp1, mp2)
                    quadFromTo(mp2, mp3)
                    quadFromTo(mp3, mp4)
                    quadFromTo(mp4, mp5)
                    lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                    lineTo(-100f, height.toFloat() + 100f)
                    close()
                }
                // Light Color points
                val lp1 = Offset(0f, height * 0.7f)
                val lp2 = Offset(width * 0.2f, height * 0.3f)
                val lp3 = Offset(width * 0.4f, height * 0.85f)
                val lp4 = Offset(width * 0.8f, height * 0.4f)
                val lp5 = Offset(width * 1.4f, height * 1.3f)

                val lightColorPath = Path().apply {
                    moveTo(lp1.x, lp1.y)
                    quadFromTo(lp1, lp2)
                    quadFromTo(lp2, lp3)
                    quadFromTo(lp3, lp4)
                    quadFromTo(lp4, lp5)
                    lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                    lineTo(-100f, height.toFloat() + 100f)
                    close()
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawPath(
                        path = mediumColorPath,
                        color = feature.mediumColor
                    )
                    drawPath(
                        path = lightColorPath,
                        color = feature.lightColor
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    Text(
                        text = feature.title,
                        style = MaterialTheme.typography.body1,
                        lineHeight = 24.sp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                    )
                    Icon(
                        feature.icon,
                        contentDescription = feature.title,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .size(36.dp)
                    )
                }
            }
        }
        2 -> {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(10.dp)
                    .aspectRatio(1f)
                    .graphicsLayer {
                        shadowElevation = 4.dp.toPx()
                        shape = CutCornerShape(0.dp, 20.dp, 0.dp, 80.dp)
                        clip = true
                    }
                    .background(feature.darkColor)
                    .selectable(
                        selected = true,
                        onClick = { navController.navigate("tracks") }
                    )
            ) {
                val width = constraints.maxWidth
                val height = constraints.maxHeight

                // Medium Color points
                val mp1 = Offset(0f, height * 0.4f)
                val mp2 = Offset(width * 0.25f, height * 0.25f)
                val mp3 = Offset(width * 0.45f, height * 0.6f)
                val mp4 = Offset(width * 0.85f, height * 0.2f)
                val mp5 = Offset(width * 1.3f, height * 1.3f)

                val mediumColorPath = Path().apply {
                    moveTo(mp1.x, mp1.y)
                    quadFromTo(mp1, mp2)
                    quadFromTo(mp2, mp3)
                    quadFromTo(mp3, mp4)
                    quadFromTo(mp4, mp5)
                    lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                    lineTo(-100f, height.toFloat() + 100f)
                    close()
                }

                val lp1 = Offset(0f, height * 0.7f)
                val lp2 = Offset(width * 0.2f, height * 0.8f)
                val lp3 = Offset(width * 0.5f, height * 0.85f)
                val lp4 = Offset(width * 0.8f, height * 0.4f)
                val lp5 = Offset(width * 1.4f, height * 1.3f)

                val lightColorPath = Path().apply {
                    moveTo(lp1.x, lp1.y)
                    quadFromTo(lp1, lp2)
                    quadFromTo(lp2, lp3)
                    quadFromTo(lp3, lp4)
                    quadFromTo(lp4, lp5)
                    lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                    lineTo(-100f, height.toFloat() + 100f)
                    close()
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawPath(
                        path = mediumColorPath,
                        color = feature.mediumColor
                    )
                    drawPath(
                        path = lightColorPath,
                        color = feature.lightColor
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    Text(
                        text = feature.title,
                        style = MaterialTheme.typography.body1,
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )
                    Icon(
                        feature.icon,
                        contentDescription = feature.title,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                    )
                }
            }
        }
        3 -> {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(10.dp)
                    .aspectRatio(1f)
                    .graphicsLayer {
                        shadowElevation = 4.dp.toPx()
                        shape = CutCornerShape(0.dp, 80.dp, 0.dp, 20.dp)
                        clip = true
                    }
                    .background(feature.darkColor)
                    .selectable(
                        selected = true,
                        onClick = { navController.navigate("stats") }
                    )
            ) {
                val width = constraints.maxWidth
                val height = constraints.maxHeight

                // Medium Color points
                val mp1 = Offset(0f, height * 0.35f)
                val mp2 = Offset(width * 0.15f, height * 0.45f)
                val mp3 = Offset(width * 0.5f, height * 0.05f)
                val mp4 = Offset(width * 0.7f, height * 0.7f)
                val mp5 = Offset(width * 1.4f, -height.toFloat())

                val mediumColorPath = Path().apply {
                    moveTo(mp1.x, mp1.y)
                    quadFromTo(mp1, mp2)
                    quadFromTo(mp2, mp3)
                    quadFromTo(mp3, mp4)
                    quadFromTo(mp4, mp5)
                    lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                    lineTo(-100f, height.toFloat() + 100f)
                    close()
                }

                val lp1 = Offset(0f, height * 0.4f)
                val lp2 = Offset(width * 0.1f, height * 0.5f)
                val lp3 = Offset(width * 0.4f, height * 0.35f)
                val lp4 = Offset(width * 0.65f, height.toFloat())
                val lp5 = Offset(width * 1.4f, -height.toFloat()/3f)

                val lightColorPath = Path().apply {
                    moveTo(lp1.x, lp1.y)
                    quadFromTo(lp1, lp2)
                    quadFromTo(lp2, lp3)
                    quadFromTo(lp3, lp4)
                    quadFromTo(lp4, lp5)
                    lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                    lineTo(-100f, height.toFloat() + 100f)
                    close()
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawPath(
                        path = mediumColorPath,
                        color = feature.mediumColor
                    )
                    drawPath(
                        path = lightColorPath,
                        color = feature.lightColor
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    Text(
                        text = feature.title,
                        style = MaterialTheme.typography.body1,
                        lineHeight = 24.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                    Icon(
                        feature.icon,
                        contentDescription = feature.title,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(36.dp)
                    )
                }
            }
        }
        4 -> {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(10.dp)
                    .aspectRatio(1f)
                    .graphicsLayer {
                        shadowElevation = 4.dp.toPx()
                        shape = CutCornerShape(80.dp, 0.dp, 20.dp, 0.dp)
                        clip = true
                    }
                    .background(feature.darkColor)
                    .selectable(
                        selected = true,
                        onClick = { navController.navigate("goals") }
                    )
            ) {
                val width = constraints.maxWidth
                val height = constraints.maxHeight

                // Medium Color points
                val mp1 = Offset(0f, height * 0.8f)
                val mp2 = Offset(width * 0.1f, height * 0.5f)
                val mp3 = Offset(width * 0.4f, height * 0.05f)
                val mp4 = Offset(width * 0.7f, height * 0.7f)
                val mp5 = Offset(width * 1.4f, -height.toFloat())

                val mediumColorPath = Path().apply {
                    moveTo(mp1.x, mp1.y)
                    quadFromTo(mp1, mp2)
                    quadFromTo(mp2, mp3)
                    quadFromTo(mp3, mp4)
                    quadFromTo(mp4, mp5)
                    lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                    lineTo(-100f, height.toFloat() + 100f)
                    close()
                }

                val lp1 = Offset(0f, height * 0.85f)
                val lp2 = Offset(width * 0.1f, height * 0.6f)
                val lp3 = Offset(width * 0.3f, height * 0.35f)
                val lp4 = Offset(width * 0.65f, height * 0.9f)
                val lp5 = Offset(width * 1.4f, -height.toFloat()/3f)

                val lightColorPath = Path().apply {
                    moveTo(lp1.x, lp1.y)
                    quadFromTo(lp1, lp2)
                    quadFromTo(lp2, lp3)
                    quadFromTo(lp3, lp4)
                    quadFromTo(lp4, lp5)
                    lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                    lineTo(-100f, height.toFloat() + 100f)
                    close()
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawPath(
                        path = mediumColorPath,
                        color = feature.mediumColor
                    )
                    drawPath(
                        path = lightColorPath,
                        color = feature.lightColor
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    Text(
                        text = feature.title,
                        style = MaterialTheme.typography.body1,
                        lineHeight = 24.sp,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    )
                    Icon(
                        feature.icon,
                        contentDescription = feature.title,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(36.dp)
                    )
                }
            }
        }
    }
}