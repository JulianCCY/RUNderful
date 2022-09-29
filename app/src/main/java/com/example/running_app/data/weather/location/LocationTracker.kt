package com.example.running_app.data.weather.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}

@ExperimentalCoroutinesApi
class InitLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val app: Application
): LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            app, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            app,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }


        return suspendCancellableCoroutine { con ->
            locationClient.lastLocation.apply {
                if(isComplete) {
                    if (isSuccessful) con.resume(result)
                    else con.resume(null)
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { con.resume(it) }
                addOnFailureListener { con.resume(null) }
                addOnCanceledListener { con.cancel() }
            }
        }
    }
}