package com.example.running_app.viewModels


import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.AndroidViewModel
import com.example.running_app.data.db.RoomDB
import kotlin.math.floor

class GoalsViewModel(application: Application) : AndroidViewModel(application) {
    private val roomDB = RoomDB.get(application)
    // total distance, total steps, total hours, average speed, average stride length, kcal, record
    var level = arrayOf(0,0,0,0,0,0,0)
    var target = arrayOf(30,10000,200,5,3,10000,500)

    fun getTotalDistance(): Float{
        var result = (roomDB.runningDao().getTotaldistance()/1000.0).toFloat()
        if(result >= target[0]){
            level[0] = floor(result/target[0]).toInt()
            result = result % target[0]
        }
        return result
    }

    fun getTotalSteps(): Int{
        var result = roomDB.runningDao().getTotalStepsForGoal()
        if (result >= target[1]) {
            level[1] = result/target[1]
            result %= target[1]
        }
        return result
    }

    fun getTotalHours(): Int{
        var result = roomDB.runningDao().getTotalDuration().value?.toList()?.sumOf { it.slice(0..1).toInt() } ?: 0
        if (result >= target[2]) {
            level[2] = result/target[2]
            result %= target[2]
        }
        return result
    }

    @SuppressLint("SuspiciousIndentation")
    fun getHighestVelocity(): Float{
        var result = roomDB.runningDao().getHighestSpeed().toFloat()
        if(result >= target[3]){
            level[3] = floor(result/target[3]).toInt()
        }
        return result
    }

    fun getHighestStrideLength(): Float{
        var result = roomDB.runningDao().getHighestStrideLength().toFloat()
        if (result >= target[4]) {
            level[4] = floor(result/target[4]).toInt()
        }
        return result
    }

    fun getCaloriesBurnt(): Int{
        var result = roomDB.runningDao().get_calories_burnt()
        if(result >= target[5]){
            level[5] = (result/target[5])
            result = result % target[5]
        }
        return result
    }

    fun getTotalRecord(): Int{
        var result = roomDB.runningDao().getNumberOfRecords()
        if(result >= target[6]){
            level[6] = (result/target[6])
            result = result % target[6]
        }
        return result
    }
}