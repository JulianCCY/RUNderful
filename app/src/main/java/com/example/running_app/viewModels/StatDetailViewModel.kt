package com.example.running_app.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.running_app.data.db.RoomDB
import com.example.running_app.data.db.Running
import com.google.android.gms.maps.model.LatLng

// access room database queries for the data insights
class StatDetailViewModel(application: Application): AndroidViewModel(application) {

    private val roomDB = RoomDB.get(application)

    // get a record by the running id
    fun getRecordById(rid: Long): LiveData<Running> {
        return roomDB.runningDao().getRecordByRID(rid)
    }

    // compare last two running steps
    fun compareTwoLatestSteps(): LiveData<List<Int>> = roomDB.runningDao().getLatestTwoSteps()

    // get latest steps
    fun getLatestSteps(): LiveData<Int> = roomDB.runningDao().getLatestSteps()

    // get average steps
    fun getAverageSteps(): LiveData<Int> = roomDB.runningDao().getAverageSteps()

    // get latest distance
    fun getLatestDistance(): LiveData<Double> = roomDB.runningDao().getLatestDistance()

    // get average distance
    fun getAverageDistance(): LiveData<Double> = roomDB.runningDao().getAverageDistance()

    // get latest speed
    fun getLatestSpeed(): LiveData<Double> = roomDB.runningDao().getLatestSpeed()

    // compare last two running speed
    fun compareTwoLatestSpeed(): LiveData<List<Double>> = roomDB.runningDao().getLatestTwoSpeed()

    // get average speed
    fun getAverageSpeed(): LiveData<Double> = roomDB.runningDao().getAverageSpeed()

    // compare last two sum of calories of day
    fun compareTwoLatestCalories(): LiveData<List<Int>> = roomDB.runningDao().getLatestAndPreviousCalories()


}