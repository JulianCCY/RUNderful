package com.example.running_app

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.running_app.data.weather.WeatherCard
import com.example.running_app.data.weather.display.DailyWeatherForecasting
import com.example.running_app.data.weather.display.WeatherForecasting
import com.example.running_app.data.weather.viewmodel.DailyWeatherViewModel
import com.example.running_app.data.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val dailyWeatherViewModel: DailyWeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            weatherViewModel.loadWeatherInfo()
            dailyWeatherViewModel.loadDailyWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
        setContent {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                WeatherCard(weatherViewModel.state, dailyWeatherViewModel.dailyState)
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForecasting(weatherViewModel.state)
                Spacer(modifier = Modifier.height(10.dp))
                DailyWeatherForecasting(dailyWeatherViewModel.dailyState)
            }
            
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Running_AppTheme {
////        Greeting("Android")
//    }
//}