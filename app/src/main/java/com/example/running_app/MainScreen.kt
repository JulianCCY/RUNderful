package com.example.running_app

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.running_app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .background(LightBackground1)
            .fillMaxSize()
    ) {
        Column {
            Setting()
            Greetings()
            Quotes()
            FeatureGrids(
                features = listOf(
                    FeaturesUI("Weather", Icons.Sharp.WbSunny, Blue1, Blue2, Blue3), 
                    FeaturesUI("Track Suggestions", Icons.Sharp.FollowTheSigns, Blue1, Blue2, Blue3),
                    FeaturesUI("Statistics", Icons.Sharp.Insights, Blue1, Blue2, Blue3),
                    FeaturesUI("Trainings", Icons.Sharp.FitnessCenter, Blue1, Blue2, Blue3),
                )
            )
        }
    }
}

@Composable
fun Setting() {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp, end = 18.dp)
    ) {
        Icon(
            Icons.Sharp.Settings,
            contentDescription = "Settings",
            tint = Orange1,
            modifier = Modifier
                .size(24.dp)
        )
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
                fontSize = 18.sp,
                color = Orange1
            )
            Text(
                text = time,
                fontSize = 90.sp,
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
            fontSize = 18.sp,
        )
    }
}

@Composable
fun FeatureGrids(features: List<FeaturesUI>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp),
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(features.size) {
            FeatureItem(feature = features[it])
        }
    }
}

@Composable
fun FeatureItem(
    feature: FeaturesUI
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(10.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // Medium Color points
        val mp1 = Offset(0f, height * 0.3f)
        val mp2 = Offset(width * 0.1f, height * 0.35f)
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

        val lp1 = Offset(0f, height * 0.35f)
        val lp2 = Offset(width * 0.1f, height * 0.4f)
        val lp3 = Offset(width * 0.3f, height * 0.35f)
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
                lineHeight = 24.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            Icon(
                feature.icon,
                contentDescription = feature.title,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
        }
    }
}