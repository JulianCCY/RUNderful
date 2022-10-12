package com.example.running_app.data.result

import com.google.android.gms.maps.model.LatLng

data class RunRecordForUI(
    var id: Long,
    // Date of the record
    val date: String,
    // Starting time of the record
    val startTime: String,
    // Ending time of the record
    val endTime: String,
    // Total time spend of the record
    val timeSpent: String,
    // Temperature of the record starting time
    val temp: Int,
    // Weather desc for icon
    val weatherDesc: String,
    // Total number of steps
    val steps: Int,
    // Total distance ran in the record
    val distance: Double,
    // Average velocity (m/s)
    val avgSpeed: Double,
    // Average heart rate (bpm)
    val avgHeart: Int,
    // Calories
    val calories: Int,
    // Stride
    val stride: Double,
    // List of coordinates --- Eg. [(Lat, Long), (60.1234, 25.4321), (60.1236, 25.4322)]
    // For creating track on map...
    val coordinates: List<LatLng>
)
