package com.example.running_app.data.stats

data class GraphStatsData(
    val speedOfLastFive: List<Double>,
    val heartOfLastFive: List<Int>,
    val stepsOfLastTwo: List<Int>,
    val latestSteps: Int,
    val avgSteps: Int,
    val latestDistance: Double,
    val avgDistance: Double,
    val latestSpeed: Double,
    val speedOfLastTwo: List<Double>,
    val avgSpeed: Double,
    val sumOfCaloriesOfLastTwo: List<Int>
)
