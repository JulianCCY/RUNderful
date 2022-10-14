package com.example.running_app.data.db

import androidx.room.*

// A data class for Setting screen and store user data
@Entity
data class User(
    @PrimaryKey
    val uid: Long,
    val name: String,
    val height: Int,
    val weight: Int,
) {
    override fun toString() = "$uid, $name, $height cm, $weight kg"
}
