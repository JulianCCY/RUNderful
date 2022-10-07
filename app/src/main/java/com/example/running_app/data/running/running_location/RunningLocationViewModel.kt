package com.example.running_app.data.running.running_location

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.running_app.viewModels.RunningViewModel

class RunningLocationViewModel(
    application: Application,
    private val model: RunningViewModel
): AndroidViewModel(application), LocationListener{

    private lateinit var locationManager: LocationManager
//    private val locationPermissionCode = 2

    fun startTrackingRunningLocation(){
        locationManager = getApplication<Application>().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    fun stopTrackingRunningLocation(){
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        model.velocity_.postValue(location.speed.toDouble())
        Log.d("Running Speed","Latitude: " + location.latitude + " , Longitude: " + location.longitude + " , Speed: " + location.speed)
    }
}