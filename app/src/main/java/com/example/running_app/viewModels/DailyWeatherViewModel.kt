package com.example.running_app.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.running_app.data.weather.DailyWeatherState
import com.example.running_app.data.weather.WeatherResource
import com.example.running_app.data.weather.api.DailyWeatherRepository
import com.example.running_app.data.weather.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DailyWeatherViewModel @Inject constructor(
    private val repository: DailyWeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var dailyState by mutableStateOf(DailyWeatherState())
        private set

    fun loadDailyWeatherInfo() {
        viewModelScope.launch {
            dailyState = dailyState.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->

                when (val result = repository.getDailyWeatherData(location.latitude, location.longitude)){
                    is WeatherResource.Success -> {
                        dailyState = dailyState.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is WeatherResource.Error -> {
                        dailyState = dailyState.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                        Log.d("julian error", "${result.message}")
                    }
                }
            }?: kotlin.run {
                dailyState = dailyState.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }
}