package com.example.running_app.data.db

import androidx.room.Embedded
import androidx.room.Relation

// This class is to make relationship between Running table and Coordinates table

class RunningCoordinates {
    @Embedded
    var running: Running? = null
    @Relation(parentColumn = "rid", entityColumn = "runningId")
    var coordinates: List<Coordinates>? = null
}