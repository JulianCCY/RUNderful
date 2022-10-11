package com.example.running_app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(User::class), (Running::class), (Coordinates::class)], version = 4)
abstract class RoomDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun runningDao(): RunningDao
    abstract fun coordinatesDao(): CoordinatesDao

    companion object{
        private var sInstance: RoomDB? = null
        @Synchronized
        fun get(context: Context): RoomDB {
            if (sInstance == null) { sInstance =
                Room.databaseBuilder(context.applicationContext,
                    RoomDB::class.java, "julian_little_world.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return sInstance!!
        }
    }
}