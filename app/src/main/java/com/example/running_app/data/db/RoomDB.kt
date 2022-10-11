package com.example.running_app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(User::class), (Running::class)], version = 3)
abstract class RoomDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun runningDao(): RunningDao

    companion object{
        private var sInstance: RoomDB? = null
        @Synchronized
        fun get(context: Context): RoomDB {
            if (sInstance == null) { sInstance =
                Room.databaseBuilder(context.applicationContext,
                    RoomDB::class.java, "julian_little_world.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return sInstance!!
        }
    }
}