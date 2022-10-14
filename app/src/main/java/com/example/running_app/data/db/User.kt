package com.example.running_app.data.db

import androidx.room.*

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
