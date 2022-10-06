package com.example.running_app.viewModels

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class RunningViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    // Step Counter Sensor Setup
    private lateinit var sm: SensorManager
    private lateinit var stepCounter: Sensor

    private val _steps: MutableLiveData<Int> = MutableLiveData()
    val steps: LiveData<Int> = _steps
    private var prevSteps = 0
    private var totalSteps = 0

    var time = MutableLiveData("00:00:000")

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    var isActive = false

    private var timeMills = 0L
    private var lastTimeStamp = 0L

    fun startCountTime(startOrResume: Boolean = false) {

        // if click start button -> reload steps count
        if (startOrResume){
            loadStepCounter()
            resetSteps()
            // if start then connect to the bluetooth first
        }

        // start sensor monitoring
        registerStepCounterSensor()

        if (isActive) {
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
    }

    // pause the timer and step count sensor
    // but it still keeps the value of step counter
    fun pauseCountTime() {
        isActive = false
        unregisterStepCounterSensor()
    }

    // finish running
    // stop sensor and save step count
    fun stopCountTime() {
        unregisterStepCounterSensor()

        saveStepCounter()
        updateStepCounter(0)

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

    // function to start sensor monitoring
    fun registerStepCounterSensor() {
        sm = getApplication<Application>().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)?.let {
            this.stepCounter = it
        }
        sm.registerListener(this, this.stepCounter, SensorManager.SENSOR_DELAY_FASTEST, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }

    // function to stop sensor monitoring
    fun unregisterStepCounterSensor(){
        sm.unregisterListener(this)
    }

    // update the step counter to UI
    private fun updateStepCounter(steps: Int) {
        _steps.value = steps
    }

    // save step count
    private fun saveStepCounter(){

        val sharedPreferences = getApplication<Application>().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("steps_key", prevSteps)
        editor.apply()
    }


    // load previous step count because step counter sensor doesn't initialise as Zero
    private fun loadStepCounter(){
        val sharedPreferences = getApplication<Application>().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getInt("steps_key", 0)

        Log.d("load step", "$savedNumber")

        prevSteps = savedNumber
    }

    // initialise
    private fun resetSteps() {
        prevSteps = totalSteps
        saveStepCounter()
    }


    // To update step count when the sensor is working
    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("steps", "${event!!.values[0].toInt()}")

        totalSteps = event.values[0].toInt()

        if (prevSteps == 0){
            loadStepCounter()
            resetSteps()
        }

        val currentSteps = totalSteps - prevSteps
        Log.d("currentSteps", "$currentSteps")
        updateStepCounter(currentSteps)
    }

    // this part is not required
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}