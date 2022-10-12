package com.example.running_app.viewModels

import  android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.example.running_app.data.db.Coordinates
import com.example.running_app.data.db.RoomDB
import com.example.running_app.data.db.Running
import com.example.running_app.data.running.heartrate.BLEViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

class RunningViewModel (
    application: Application,
) : AndroidViewModel(application), SensorEventListener, LocationListener {

    val tag = "running screen"
    val tag2 = "Finish running"
    val tag3 = "Room Running"

    // Room
    private val roomDB = RoomDB.get(application)

    // Conditions
    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    var isRunning = false

    // Step Counter Sensor Setup
    private lateinit var sm: SensorManager
    private lateinit var stepCounter: Sensor

    // Step count
    private val _steps: MutableLiveData<Int> = MutableLiveData(0)
    val steps: LiveData<Int> = _steps
    var endSteps: Int? = null
    private var prevSteps = 0
    private var totalSteps = 0

    // set up Location Tracking while running
    private lateinit var locationManager: LocationManager

    // Current Location for running map
    var runLat_: MutableLiveData<Double> = MutableLiveData()
    val runLat: LiveData<Double> = runLat_
    var runLong_: MutableLiveData<Double> = MutableLiveData()
    val runLong: LiveData<Double> = runLong_

    // Time
    var time: MutableLiveData<String> = MutableLiveData("00:00:00")
    private var timeMills = 0L
    private var lastTimeStamp = 0L
    val minutes_: MutableLiveData<Int> = MutableLiveData(0)
    val minutes: LiveData<Int> = minutes_

    // Running Velocity
    val velocity_: MutableLiveData<Double> = MutableLiveData(0.0)
    val velocity: LiveData<Double> = velocity_
    var velocityList: MutableList<Double> = mutableListOf()
    var avgVelocity: Double? = null

    // Running Distance
    var prevLat: Double? = null
    var prevLong: Double? = null
    val distance_: MutableLiveData<Double> = MutableLiveData(0.0)
    val distance: LiveData<Double> = distance_

    // Heart rate
    val mBPM: LiveData<Int> = BLEViewModel.mBPM_
    val hBPM: LiveData<Int> = BLEViewModel.hBPM_
    val lBPM: LiveData<Int> = BLEViewModel.lBPM_
    val BPMList: LiveData<MutableList<Int>> = BLEViewModel.avgBPM_
    var AVGmBPM: Int = 0
//    var avgHeartRate: LiveData<MutableList<Int>> = BLEViewModel.avgBPM_

    // Stride Length
    val sLength_: MutableLiveData<Double> = MutableLiveData(0.0)
    val sLength: LiveData<Double> = sLength_

    // cadence
    var cadence: Int? = null

    // Calories
    var weight: Int? = SettingsViewModel.weightForCalories_.value
    val calories_: MutableLiveData<Int> = MutableLiveData(0)
    val calories: LiveData<Int> = calories_

    // Time
//    val df = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var startTime: String? = null
    var endTime: String? = null
    val sdf1 = SimpleDateFormat("dd-MM-yyyy")
    val dateTime = sdf1.format(Date())
//    var dateTime = LocalDateTime.of(LocalDate.parse(LocalDateTime.now().toString(), df), LocalDateTime.MIN.toLocalTime()).toString()
    var duration: String? = null

    // Weather
    var weather: String? = "Sunny"
    var temperature: Int? = -1

    // Coordinates
    var latitude = mutableListOf<Double>()
    var longitude = mutableListOf<Double>()

    fun startRunning(StartIsTrueAndPauseIsFalse: Boolean = false) {

//        Log.d(tag, state.weatherInfo?.currentWeatherData?.weatherType?.weatherDesc ?: "")

        prevLat = null
        prevLong = null

        // if click start button -> reload steps count
        if (StartIsTrueAndPauseIsFalse){
            val starttime = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ISO_DATE_TIME)
            startTime = starttime.format(DateTimeFormatter.ofPattern("HH:mm"))

            loadStepCounter()
            resetSteps()
        }
        // start sensor monitoring
        registerStepCounterSensor()
        // start update location while running
        startTrackingRunningLocation()

        Log.d("my weight", "$weight")

        if (isRunning){
            coroutineScope.launch {
                lastTimeStamp = System.currentTimeMillis()
                this@RunningViewModel.isRunning = true
                while (this@RunningViewModel.isRunning) {
                    delay(10L)
                    timeMills += System.currentTimeMillis() - lastTimeStamp
                    lastTimeStamp = System.currentTimeMillis()
                    time.postValue(formatTime(timeMills))


                    val calculateCalories = time.value?.slice(0..1)!!.toInt() * (10 * 3.5 * weight!!) / 200
                    calories_.postValue(calculateCalories.roundToInt())



                    if (_steps.value != 0 && distance_.value != 0.0) {
                        val calculateStrideLength = distance_.value?.div(_steps.value!!)
                        sLength_.postValue(calculateStrideLength)
                    }
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

        val endtime = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ISO_DATE_TIME)
        endTime = endtime.format(DateTimeFormatter.ofPattern("HH:mm"))
        duration = time.value

        avgVelocity = if (velocityList.isNotEmpty()) velocityList.sum() / velocityList.size else 0.0

        cadence = if (minutes.value != 0 && minutes.value != null) steps.value?.div(minutes.value!!) else steps.value

        AVGmBPM = if (BPMList.value != null && BPMList.value!!.sum() != 0) BPMList.value?.sum()!! / BPMList.value!!.size else 0

        isRunning = false
        stopTrackingRunningLocation()
        prevLat = null
        prevLong = null

        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        timeMills = 0L
        lastTimeStamp = 0L
//        time = "00:00:000"
        time.postValue(formatTime(0))

        endSteps = steps.value


        // ********************************************* result data *********************************************
        // weather
        Log.d(tag2, "weather is $weather")
        // temp
        Log.d(tag2, "temperature is $temperature")
        //date
        Log.d(tag2, "date $dateTime")
        // time start
        Log.d(tag2, "time start $startTime")
        // time end
        Log.d(tag2, "end time $endTime")
        //duration
        Log.d(tag2, "duration $duration")
        // total distance
        Log.d(tag2, "total distance ${distance.value}")
        // total step
        Log.d(tag2, "total steps ${steps.value}")
        // Average Speed
        Log.d(tag2, "speed list $velocityList")
        Log.d(tag2, "avg speed $avgVelocity")
        // Calories
        Log.d(tag2, "calories ${calories.value}")
        // Average Heart rate
        Log.d(tag2, "Heart rate list ${BPMList.value}")
        Log.d(tag2, "Avg heart rate $AVGmBPM")
        //Stride Length
        Log.d(tag2, "stride length ${sLength.value}")
        // Cadence
        Log.d(tag2, "cadence $cadence")

        // check coordinate
        Log.d(tag2, "$latitude")
        Log.d(tag2, "$longitude")

        Log.d(tag2, "total steps ${steps.value}")

        insertRecords()
        // wait until finish insert records then insert coordinate then reset steps
        coroutineScope.launch {
            Log.d("room step", "2 ${steps.value}")
            delay(500)
            insertCoordinates()
            saveStepCounter()
            updateStepCounter(0)
        }
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

    // function to stop step counter sensor to make changes
    fun unregisterStepCounterSensor(){
        sm.unregisterListener(this, stepCounter)
        isRunning = false
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

        if (!isRunning){
            unregisterStepCounterSensor()
        }

        // if user is running
        if (isRunning) {
            Log.d("steps", "${event!!.values[0].toInt()}")
            totalSteps = event.values[0].toInt()

            if (prevSteps == 0) {
                loadStepCounter()
                resetSteps()
            }

            val currentSteps = totalSteps - prevSteps
            Log.d("currentSteps", "$currentSteps")
            updateStepCounter(currentSteps)
        }
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

        // get current location to update the mapview in running screen
        runLat_.postValue(location.latitude.toDouble())
        runLong_.postValue(location.longitude.toDouble())

        latitude.add(location.latitude)
        longitude.add(location.longitude)

        // will not update the distance if user doesn't run
        if (location.speed != 0.0f) {
            if (prevLat == null && prevLong == null){
                prevLat = location.latitude
                prevLong = location.longitude
            } else {
                // initialise previous location
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
        velocityList.add(location.speed.toDouble())

        Log.d("Running Speed","Latitude: " + location.latitude + " , Longitude: " + location.longitude + " , Speed: " + location.speed)
    }

    fun getAddressWhenRunning(lat: Double, long: Double): String{
        val geocoder = Geocoder(getApplication<Application>())
        var address = ""

        if (Build.VERSION.SDK_INT >= 33) {
            geocoder.getFromLocation(lat, long, 1)?.let{ address = it.first().getAddressLine(0) }
        } else {
            address = geocoder.getFromLocation(lat, long, 1)?.first()?.getAddressLine(0) ?: "Unknown Place"
        }

        return address
    }

    // ************************************************* ROOM DATABASE *************************************************
    private fun insertRecords() {
        coroutineScope.launch {
            roomDB.runningDao().addNewRecord(
                Running(
                    rid = 0,
                    weatherDesc = weather.toString(),
                    temperature = temperature!!,
                    startTime = startTime.toString(),
                    endTime = endTime.toString(),
                    date = dateTime,
                    duration = duration.toString(),
                    distance = distance.value!!.toDouble(),
                    totalStep = endSteps!!.toInt(),
                    avgSpeed = avgVelocity!!.toDouble(),
                    calories = calories.value!!.toInt(),
                    avgHR = AVGmBPM,
                    avgStrideLength = sLength.value!!.toDouble(),
                    cadence = cadence!!.toInt()
                )
            )
            val test = roomDB.runningDao().getAllRecords()
            Log.d(tag3, "newest $test")
        }
    }

    private fun insertCoordinates(){
        val runningId = roomDB.runningDao().getKeyForCoordinate()
        Log.d(tag3, "key, $runningId")
        coroutineScope.launch {

            Log.d(tag3, "$runningId")

//            val chlat = listOf(39.915499485214696, 39.91528369965235, 39.9134599729653, 39.91119965045904, 39.90893934917637, 39.90765494295471,
//                39.9066606674469, 39.90513265732545, 39.90419009152418, 39.90487301431289, 39.904287941921396, 39.904515628329285, 39.906531341493185,
//                39.908904609878135, 39.91400886567836, 39.91533944744445, 39.91540886416355, 39.91480418305796
//            )
//            val chlong = listOf(116.39719795033322,116.39883553383524,116.39994041993818, 116.40027500765676, 116.40047563891909, 116.40248454418219,
//                116.40268428914682, 116.40255684545879, 116.40005616927874, 116.39772529391956, 116.3957333195003, 116.393232864636, 116.39200389579304, 116.39327549581009,
//                116.39344534678946, 116.39472048212771, 116.39677082838156,  116.397131204287)


            latitude.forEachIndexed { i, e ->
                roomDB.coordinatesDao().insertCoordinates(
                    Coordinates(
                        cid = 0,
                        runningId = runningId,
                        latitude = e,
                        longitude = longitude[i]
                    )
                )
            }
        }
        Log.d(tag3, "cood ${roomDB.coordinatesDao().getALLCoordinates()}")
    }

    fun getAllRecords(): LiveData<List<Running>> = roomDB.runningDao().getAllRecords()

    fun getAllCoordinates(): LiveData<List<Coordinates>> = roomDB.coordinatesDao().getALLCoordinates()

    fun getRecordForResult(): LiveData<Running> = roomDB.runningDao().getLatestRecord()


    fun getRouteForResult(rid: Long): LiveData<List<Coordinates>> {
        return roomDB.coordinatesDao().getAllRelatedCoordinates(rid)
    }





}