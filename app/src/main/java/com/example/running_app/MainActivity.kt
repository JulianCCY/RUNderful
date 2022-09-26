package com.example.running_app

import android.Manifest
import android.os.Bundle
import android.webkit.PermissionRequest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.data.weather.WeatherCard
import com.example.running_app.data.weather.WeatherForecasting
import com.example.running_app.data.weather.WeatherState
import com.example.running_app.data.weather.WeatherViewModel
import com.example.running_app.ui.theme.Running_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            weatherViewModel.loadWeatherInfo()
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
                WeatherCard(weatherViewModel.state)
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForecasting(weatherViewModel.state)
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