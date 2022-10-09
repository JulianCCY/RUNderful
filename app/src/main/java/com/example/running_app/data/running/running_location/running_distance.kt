package com.example.running_app.data.running.running_location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.running_app.viewModels.RunningViewModel
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import java.lang.Math.round
import javax.inject.Inject

//class RunningDistanceImp @Inject constructor(
//    private val activity: Activity,
//    private val app: Application,
//    private val runningViewModel: RunningViewModel
//) {
//
//    val TAG = "running location update"
//
//    private var prevLocation: Location? = null
//
//    var fusedLocationClient: FusedLocationProviderClient =
//        LocationServices.getFusedLocationProviderClient(activity)
//
//    private fun hasPermission(): Boolean {
//        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
//            app, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
//            app, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//
//        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//
//        if(!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
//            return false
//        }
//        return true
//    }
//
//
//    @SuppressLint("MissingPermission")
//    fun initFusedLocationClient(){
//        fusedLocationClient.lastLocation.addOnSuccessListener {
//            prevLocation = it
//                Log.d(TAG, "${it.latitude}, ${it.longitude}")
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    fun startGettingRunningDistance() {
//        if (hasPermission()){
//            initFusedLocationClient()
//
//            val locationRequest = LocationRequest
//                    .create()
//                .setInterval(1000)
//                .setPriority(PRIORITY_HIGH_ACCURACY)
//
//            fusedLocationClient.requestLocationUpdates(locationRequest,
//                locationCallback,
//                Looper.getMainLooper())
//        }
//    }
//
//    fun stopGettingRunningDistance() {
//        fusedLocationClient.removeLocationUpdates(locationCallback)
//    }
//
//
//    private var locationCallback: LocationCallback = object : LocationCallback(){
//        override fun onLocationResult(locationResult: LocationResult) {
//            super.onLocationResult(locationResult)
//            locationResult ?: return
//            for (location in locationResult.locations){
//                if (prevLocation != null) {
//                    runningViewModel.distance_.postValue((location.distanceTo(prevLocation)).toDouble())
//                } else {
//                    prevLocation = location
//                }
//            }
////            for (location in locationResult.locations) {
////                if (prevLocation != null) {
////                    distance = round(location.distanceTo(preLocation!!)).toDouble()
////                } else {
////                    preLocation = location
////                }
////                speed = round(location.speed).toDouble()
////                if (speed > maxSpeed) maxSpeed = speed
////                Log.d(TAG, "distance is: $distance")
////                Log.d(TAG, "speed is: $speed")
////                Log.d(TAG,
////                    "location latitude: ${location.latitude} and longitude ${location.longitude}")
////                //preLocation = location
////                long = location.longitude
////                lat = location.latitude
//            }
//        }
//}