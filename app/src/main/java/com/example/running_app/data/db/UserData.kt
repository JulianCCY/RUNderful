package com.example.running_app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey
    val name: String,
    val height: Int,
    val weight: Int,
)
