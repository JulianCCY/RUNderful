package com.example.running_app.Views.Utils

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class FeaturesUI(
    val title: String,
    val icon: ImageVector,
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color,
    val type: Int,
)
