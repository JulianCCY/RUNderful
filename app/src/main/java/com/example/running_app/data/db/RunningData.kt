package com.example.running_app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration

@Entity
data class RunningData(
    @PrimaryKey(autoGenerate = true)
    val rid: Long, // running record id
    val weatherDesc: String, // the weather desc when users start running
    val temperature: Int, // temperature when users start running
    val startTime: String, // when start running
    val endTime: String, // when end running
    val duration: String, // total running time
    val distance: Double, // total running distance
    val totalStep: Int, // total steps
    val avgSpeed: Double, // average running speed
    val calories: Int, // calories burned
    val avgHR: Int, // average heart rate
    val strideLength: Double, // length of one step, unit in meter
    val cadence: Int, // steps per minute, unit in spm
)
