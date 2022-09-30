package com.example.running_app.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.running_app.R

// Font 1
val Font1 = FontFamily(
    Font(R.font.homenaje_regular)
)

// Font 2
val Font2 = FontFamily(
    Font(R.font.leaguegothic_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = Font1,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
    ),
    h2 = TextStyle(
        fontFamily = Font1,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
    ),
    h3 = TextStyle(
        fontFamily = Font1,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    h4 = TextStyle(
        fontFamily = Font2,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
    ),
    h5 = TextStyle(
        fontFamily = Font2,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
    ),
    h6 = TextStyle(
        fontFamily = Font2,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = Font1,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Font1,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    body1 = TextStyle(
        fontFamily = Font1,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    body2 = TextStyle(
        fontFamily = Font1,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)