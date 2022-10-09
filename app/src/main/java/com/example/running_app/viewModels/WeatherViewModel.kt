package com.example.running_app.viewModels

import android.app.Application
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.running_app.data.weather.location.LocationTracker
import com.example.running_app.data.weather.api.WeatherRepository
import com.example.running_app.data.weather.WeatherResource
import com.example.running_app.data.weather.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    application: Application
): AndroidViewModel(application) {

    private val context
        get() = getApplication<Application>()

    private val _address: MutableLiveData<String> = MutableLiveData()
    val address: LiveData<String> = _address

    val switch = mutableStateOf("hours")
    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                Log.d("julian", "${location.latitude}, ${location.longitude}")
                forecastingLocation(location.latitude, location.longitude)
                when(val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is WeatherResource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is WeatherResource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                Log.d("julian", "No Location available.")
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }
    private fun forecastingLocation(lat: Double, long: Double){
        val geocoder = Geocoder(context)
        if (Build.VERSION.SDK_INT >= 33)
            geocoder.getFromLocation(lat, long, 1)?.let{
                _address.postValue(it.first()?.locality)
            }
        else _address.postValue(geocoder.getFromLocation(lat, long, 1)?.first()?.locality ?: "")
//        Log.d("current address", "${address.value}")
    }
}