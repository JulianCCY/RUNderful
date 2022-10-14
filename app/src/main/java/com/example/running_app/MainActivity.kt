package com.example.running_app

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.running_app.data.db.User
import com.example.running_app.data.weather.location.LocationTracker
import com.example.running_app.ui.theme.Running_AppTheme
import com.example.running_app.viewModels.*
import com.example.running_app.views.RunningScreen
import com.example.running_app.views.WeatherScreen
import com.example.running_app.views.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

const val CHANNEL_ID = "123"

@AndroidEntryPoint
class MainActivity : ComponentActivity(){

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val dailyWeatherViewModel: DailyWeatherViewModel by viewModels()
    private val runningViewModel: RunningViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val statViewModel: StatViewModel by viewModels()
    private val statDetailViewModel: StatDetailViewModel by viewModels()
    private val goalsViewModel: GoalsViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    // create notification when users start a run and finish a run
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.channel_description)
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        // check permission of the app
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

        // If it's first time to use, we create a default profile
        if (settingsViewModel.checkNewUser()) {
            Log.d("ROOM main", "main ${settingsViewModel.checkNewUser()}")
            settingsViewModel.insert(User(0, "User", 176, 55))
        }

        // Get the weight for running screen to calculate active calories
        settingsViewModel.getUserWeight()
        Log.d("ROOM", "main ${settingsViewModel.weightForCalories.value}")

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
                        composable("settings") {
                            SettingsScreen(navController)
                        }
                        composable("weather") {
                            WeatherScreen(weatherViewModel, dailyWeatherViewModel)
                        }
                        composable("tracks") {
                            TrackSuggestionScreen()
                        }
                        composable("stats") {
                            StatScreen(navController, statViewModel, statDetailViewModel, settingsViewModel)
                        }
                        composable("statsDetail/{dataId}") {
                            val dataId = it.arguments?.getString("dataId")?.toLong() ?: 0
                            StatDetail(runningId = dataId)
                        }
                        composable("goals") {
                            GoalsScreen(goalsViewModel)
                        }
                        composable("startRunning") {
                            RunningScreen(navController, weatherViewModel)
                        }
                        composable("result") {
                            BackHandler(true) {
                                // disable back button after finish running
                            }
                            ResultScreen(navController, runningViewModel)
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