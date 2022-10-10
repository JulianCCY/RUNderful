package com.example.running_app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB(
    @PrimaryKey
    val name: String,
    val height: Int,
    val weight: Int,
)
