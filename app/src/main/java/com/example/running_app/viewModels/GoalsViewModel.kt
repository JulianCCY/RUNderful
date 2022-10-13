package com.example.running_app.viewModels


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.running_app.data.db.RoomDB
import kotlin.math.floor

class GoalsViewModel(application: Application) : AndroidViewModel(application) {
    private val roomDB = RoomDB.get(application)
    var level_list = arrayOf(0,0,0,0)
    var max_list = arrayOf(25,5,1000,50)

    fun get_total_distance(): Float{
        var result = (roomDB.runningDao().getTotaldistance()/1000.0).toFloat()
        if(result >= max_list[0]){
            level_list[0] = floor(result/max_list[0]).toInt()
            result = result % max_list[0]
        }
        return result
    }

    @SuppressLint("SuspiciousIndentation")
    fun get_highest_velocity(): Float{
        var result = roomDB.runningDao().getHighestSpeed().toFloat()
        if(result >= max_list[1]){
            level_list[1] = floor(result/max_list[1]).toInt()
        }
        return result

    }

    fun get_calories_burnt(): Int{
        var result = roomDB.runningDao().get_calories_burnt()
        if(result >= max_list[2]){
            level_list[2] = (result/max_list[2])
            result = result % max_list[2]
        }
        return result
    }

    fun get_total_record(): Int{
        var result = roomDB.runningDao().getNumberOfRecords()
        if(result >= max_list[3]){
            level_list[3] = (result/max_list[3])
            result = result % max_list[3]
        }
        return result
    }
}