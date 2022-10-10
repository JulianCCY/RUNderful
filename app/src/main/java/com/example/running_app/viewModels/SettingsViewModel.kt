package com.example.running_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {
    // Get user height and weight from database

//    var height: LiveData<Int> = 170
    var nickname: String = "Bitch"
    var height: Int = 170
    var weight: Int = 60

    fun updateHeightWeight(inputHeight: Int, inputWeight: Int) {
        // Database
    }
}