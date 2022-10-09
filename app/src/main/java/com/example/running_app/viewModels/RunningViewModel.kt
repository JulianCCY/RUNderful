package com.example.running_app.viewModels

import  android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.running_app.data.running.heartrate.BLEViewModel
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class RunningViewModel (
    application: Application,
) : AndroidViewModel(application), SensorEventListener, LocationListener {

    // Conditions
    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    var isRunning = false

    // Step Counter Sensor Setup
    private lateinit var sm: SensorManager
    private lateinit var stepCounter: Sensor

    // Step count
    private val _steps: MutableLiveData<Int> = MutableLiveData(0)
    val steps: LiveData<Int> = _steps
    private var prevSteps = 0
    private var totalSteps = 0

    // set up Location Tracking while running
    private lateinit var locationManager: LocationManager

    // Time
    var time = MutableLiveData("00:00:00")
    private var timeMills = 0L
    private var lastTimeStamp = 0L

    // Running Velocity
    val velocity_: MutableLiveData<Double> = MutableLiveData(0.0)
    val velocity: LiveData<Double> = velocity_

    // Running Distance
    var prevLat: Double? = null
    var prevLong: Double? = null
    val distance_: MutableLiveData<Double> = MutableLiveData(0.0)
    val distance: LiveData<Double> = distance_

    // Heart rate
    val mBPM: LiveData<Int> = BLEViewModel.mBPM_
    val hBPM: LiveData<Int> = BLEViewModel.hBPM_
    val lBPM: LiveData<Int> = BLEViewModel.lBPM_

    // Stride Length
    val sLength: Double? = distance.value?.div(steps.value?.toDouble()!!)

    // Calories

    fun startRunning(StartIsTrueAndPauseIsFalse: Boolean = false) {

        // if click start button -> reload steps count
        if (StartIsTrueAndPauseIsFalse){
            loadStepCounter()
            resetSteps()
        }

        prevLat = null
        prevLong = null

        if (isRunning){
            coroutineScope.launch {
                delay(3000)
                // start sensor monitoring
                registerStepCounterSensor()
                // start update location while running
                startTrackingRunningLocation()

                lastTimeStamp = System.currentTimeMillis()
                this@RunningViewModel.isRunning = true
                while (this@RunningViewModel.isRunning) {
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
    fun pauseRunning() {
        isRunning = false
        unregisterStepCounterSensor()
        stopTrackingRunningLocation()
        prevLat = null
        prevLong = null
        velocity_.postValue(null)
    }

    // finish running
    // stop sensor and save step count
    fun stopRunning() {
        unregisterStepCounterSensor()
        stopTrackingRunningLocation()
        prevLat = null
        prevLong = null

        saveStepCounter()
        updateStepCounter(0)

        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        timeMills = 0L
        lastTimeStamp = 0L
//        time = "00:00:000"
        time.postValue(formatTime(0))
        isRunning = false
    }

    // formatting the time count
    private fun formatTime(timeMills: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMills),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern("mm:ss:SS", Locale.getDefault())
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

    private fun startTrackingRunningLocation(){
        locationManager = getApplication<Application>().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1.5f, this)
    }

    private fun stopTrackingRunningLocation(){
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {

        // will not update the distance if user doesn't run
        if (location.speed != 0.0f) {
            if (prevLat == null && prevLong == null){
                prevLat = location.latitude
                prevLong = location.longitude
            } else {
                val prevLocation = Location("previous location")
                prevLocation.latitude = prevLat!!
                prevLocation.longitude = prevLong!!

                // calculate distance difference
                val distanceDiff = distance_.value?.plus(location.distanceTo(prevLocation))
                Log.d("new distance", "$distanceDiff")
                distance_.postValue(distanceDiff)
            }
        }
        //update previous location
        prevLat = location.latitude
        prevLong = location.longitude

        velocity_.postValue(location.speed.toDouble())

        Log.d("Running Speed","Latitude: " + location.latitude + " , Longitude: " + location.longitude + " , Speed: " + location.speed)
    }


}