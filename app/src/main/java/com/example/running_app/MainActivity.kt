package com.example.running_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.running_app.ui.theme.Running_AppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Running_AppTheme {
                // A surface container using the 'background' color from the theme
                rememberSystemUiController().setStatusBarColor(
                    color = MaterialTheme.colors.background
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}
//@Composable
//fun MainView() {
//    val navController = rememberNavController()
//    val sdf1 = SimpleDateFormat("dd/MM/yyyy")
//    val sdf2 = SimpleDateFormat("HH:mm")
//    val date = sdf1.format(Date())
//    val time = sdf2.format(Date())
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//    ) {
//        Text(text = date)
//        Text(text = time)
//        NavHost(navController, startDestination = "home") {
//            composable("home") {
//
//            }
//            composable("weather") {
//
//            }
//            composable("suggestedTracks") {
//
//            }
//            composable("stats") {
//
//            }
//            composable("training") {
//
//            }
//            composable("startRunning") {
//
//            }
//        }
//    }
//}
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