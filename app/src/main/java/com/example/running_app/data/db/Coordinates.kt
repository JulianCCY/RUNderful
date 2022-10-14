package com.example.running_app.data.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = [ForeignKey(
    entity = Running::class,
    onDelete = CASCADE,
    parentColumns = ["rid"],
    childColumns = ["runningId"],
)])
data class Coordinates(
    @PrimaryKey(autoGenerate = true)
    val cid: Long,
    val runningId: Long,
    val latitude: Double,
    val longitude: Double,
)
