package com.example.running_app.viewModels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.running_app.data.db.RoomDB
import com.example.running_app.data.db.Running
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StatViewModel(application: Application): AndroidViewModel(application) {

    val switch = mutableStateOf("records")

    private val roomDB = RoomDB.get(application)

    // get all records
    fun getAllRecords(): LiveData<List<Running>> = roomDB.runningDao().getAllRecords()

    // get number of exercise
    fun getNumExe(): LiveData<Int> = roomDB.runningDao().getNoOfRecords()

    // get total steps of all time
    fun getTS(): LiveData<Int> = roomDB.runningDao().getTotalSteps()

    // get total distance of all time
    fun getTD(): LiveData<Double> = roomDB.runningDao().getTotalDistance()

    // get average speed of last five records
    fun getL5AS(): LiveData<List<Double>> = roomDB.runningDao().getLastFiveAverageSpeed()

    // get average heart rate of last five records
    fun getL5HR(): LiveData<List<Int>> = roomDB.runningDao().getLastFiveAverageHeartRate()


    fun delARecord(id: Long){
        viewModelScope.launch {
            roomDB.coordinatesDao().deleteRelatedCoordinates(id)
            roomDB.runningDao().deleteRecordByRID(id)
        }
    }

}