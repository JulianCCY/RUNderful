package com.example.running_app.viewModels

import androidx.lifecycle.LiveData

class WeatherViewModel() {
    var forecastRange: String = "short"
//    val forecastData: LiveData<List<DataStructure>> = function?
    fun switchRange(selection: String) {
        forecastRange = if (selection == "week") { "long" } else "short"
    }
}