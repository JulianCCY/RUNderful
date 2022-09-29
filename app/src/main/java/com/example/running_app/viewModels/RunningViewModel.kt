package com.example.running_app.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class RunningViewModel: ViewModel() {

//    var time by mutableStateOf("00:00:000")
    var time = MutableLiveData("00:00:000")

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isActive = false

    private var timeMills = 0L
    private var lastTimeStamp = 0L

    fun startCountTime() {
        if (isActive) return

        coroutineScope.launch {
            lastTimeStamp = System.currentTimeMillis()
            this@RunningViewModel.isActive = true
            while (this@RunningViewModel.isActive) {
                delay(10L)
                timeMills += System.currentTimeMillis() - lastTimeStamp
                lastTimeStamp = System.currentTimeMillis()
                time.postValue(formatTime(timeMills))
            }
        }
    }

    fun pauseCountTime() {
        isActive = false
    }

    fun stopCountTime() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        timeMills = 0L
        lastTimeStamp = 0L
//        time = "00:00:000"
        time.postValue(formatTime(0))
        isActive = false
    }

    private fun formatTime(timeMills: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMills),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern("mm:ss:SSS", Locale.getDefault())
        return localDateTime.format(formatter)
    }
}