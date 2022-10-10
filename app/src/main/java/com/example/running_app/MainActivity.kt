package com.example.running_app

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.running_app.viewModels.DailyWeatherViewModel
import com.example.running_app.viewModels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.running_app.ui.theme.Running_AppTheme
import com.example.running_app.viewModels.RunningViewModel
import com.example.running_app.views.RunningScreen
import com.example.running_app.views.WeatherScreen
import com.example.running_app.views.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@AndroidEntryPoint
class MainActivity : ComponentActivity(){

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val dailyWeatherViewModel: DailyWeatherViewModel by viewModels()
    private val runningViewModel: RunningViewModel by viewModels()
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
            Manifest.permission.ACTIVITY_RECOGNITION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
        ))

        setContent {
            val navController = rememberNavController()
            Running_AppTheme {
                // A surface container using the 'background' color from the theme
                rememberSystemUiController().setStatusBarColor(
                    color = MaterialTheme.colors.background
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController, startDestination = "main") {
                        composable("main") {
                            MainScreen(navController)
                        }
                        composable("weather") {
                            WeatherScreen(weatherViewModel, dailyWeatherViewModel)
                        }
                        composable("tracks") {
                            TrackSuggestionScreen()
                        }
                        composable("stats") {
                            StatScreen(navController)
                        }
                        composable("statsDetail/{dataId}") {
                            val dataId = it.arguments?.getString("dataId")?.toInt() ?: 0
                            StatDetail(dataId = dataId)
                        }
                        composable("goals") {
                            GoalsScreen()
                        }
                        composable("startRunning") {
                            RunningScreen(navController)
                        }
                        composable("result") {
                            ResultScreen()
                        }
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        if (runningViewModel.isRunning){
            runningViewModel.registerStepCounterSensor()
        }
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Running_AppTheme {
//        Greeting("Android")
//    }
//}