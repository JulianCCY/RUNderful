package com.example.running_app.views

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Bluetooth
import androidx.compose.material.icons.sharp.BluetoothConnected
import androidx.compose.material.icons.sharp.CleaningServices
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.R
import com.example.running_app.data.db.User
import com.example.running_app.data.running.heartrate.BLEViewModel
import com.example.running_app.ui.theme.Blue1
import com.example.running_app.ui.theme.Blue2
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.viewModels.SettingsViewModel
import com.example.running_app.views.utils.NotificationService

@Composable
fun SettingsScreen(bleViewModel: BLEViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        SettingsTitle()
        SettingsSection(bleViewModel = bleViewModel)
    }
}

@Composable
fun SettingsTitle() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.h1,
        )
//        NotificationSwitch(context = LocalContext.current)
    }
}

@Composable
fun NotificationSwitch(context: Context) {
    val state = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 5.dp, end = 40.dp)
    ) {
        Text(
            text = stringResource(R.string.notification),
            style = MaterialTheme.typography.caption,
        )
        Switch(
            checked = state.value,
            onCheckedChange = {
                state.value = it
                if (state.value) {
                    context.startService(Intent(context, NotificationService::class.java))
                } else {
                    context.stopService(Intent(context, NotificationService::class.java))
                }
            },
        )
    }

}

@Composable
fun SettingsSection(viewModel: SettingsViewModel = viewModel(), bleViewModel: BLEViewModel) {
    val nameMaxChar = 15
    val maxChar = 3
    val userInfo = viewModel.getUser().observeAsState()

    if (userInfo.value != null) {
        var nickname by remember { mutableStateOf(userInfo.value!!.name) }
        var height by remember { mutableStateOf(userInfo.value!!.height.toString()) }
        var weight by remember { mutableStateOf(userInfo.value!!.weight.toString()) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
            ) {
                // Subtitle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.personal_details),
                        style = MaterialTheme.typography.body1
                    )
                    Divider(
                        thickness = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                    )
                }
                // Nickname
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    OutlinedTextField(
                        value = nickname,
                        onValueChange = {
                            if (it.length <= nameMaxChar) nickname = it
                        },
                        label = { Text(text = "Nickname")},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .width(200.dp)
                    )
                }
                // Height and Weight row
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .fillMaxWidth()
                ) {
                    // Height
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        OutlinedTextField(
                            value = height,
                            onValueChange = {
                                if (it.length <= maxChar) height = it
                            },
                            label = { Text(text = "Height")},
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                color = MaterialTheme.colors.onSecondary),
                            modifier = Modifier
                                .width(140.dp)
                                .padding(end = 10.dp)
                        )
                        Text(
                            text = "cm",
                            style = MaterialTheme.typography.body1
                        )
                    }
                    // Weight
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        OutlinedTextField(
                            value = weight,
                            onValueChange = {
                                if (it.length <= maxChar) weight = it
                            },
                            label = { Text(text = "Weight")},
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                color = MaterialTheme.colors.onSecondary),
                            modifier = Modifier
                                .width(140.dp)
                                .padding(end = 10.dp)
                        )
                        Text(
                            text = "kg",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
            BluetoothSection(viewModel = bleViewModel)
            WipeDataSection(viewModel = viewModel)
            // All set button
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 75.dp)
            ) {
                Button(
                    onClick = {
                        println("${userInfo.value}")
                        println("${userInfo.value?.uid}, $nickname, ${height.toInt()}, ${weight.toInt()}")
                        if (userInfo.value?.uid != null) {
                            println("update")
                            viewModel.update(User(userInfo.value!!.uid, nickname, height.toInt(), weight.toInt()))
                        } else {
                            println("insert")
                            viewModel.insert(User(0, nickname, height.toInt(), weight.toInt()))
                        }
                    },
                    modifier = Modifier
                        .width(100.dp)
                ) {
                    Text(
                        text = stringResource(R.string.all_set),
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
            
        }
    }
}

@Composable
fun BluetoothSection(viewModel: BLEViewModel) {
    val getIsConnected by viewModel.isConnected.observeAsState()
    val isConnected = getIsConnected
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp)
    ) {
        // Subtitle
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.bluetooth_connection),
                style = MaterialTheme.typography.body1,
            )
            Divider(
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            )
        }
        // Bluetooth button
        OutlinedButton(
            onClick = {
                viewModel.scanDevices()
            },
            elevation = ButtonDefaults.elevation(defaultElevation = 1.dp, pressedElevation = 2.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    if (isConnected == true) Icons.Sharp.BluetoothConnected else Icons.Sharp.Bluetooth,
                    contentDescription = "Bluetooth",
                    tint = Orange1,
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(
                    text = if (isConnected == true) stringResource(R.string.connected)
                    else stringResource(R.string.disconnected),
                )
            }
        }
    }
}

@Composable
fun WipeDataSection(viewModel: SettingsViewModel) {

    val alertToggle = remember { mutableStateOf(false)  }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Subtitle
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.application_data),
                style = MaterialTheme.typography.body1,
            )
            Divider(
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            )
        }
        // Wipe data button
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {alertToggle.value = true},
                elevation = ButtonDefaults.elevation(defaultElevation = 1.dp, pressedElevation = 2.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        Icons.Sharp.CleaningServices,
                        contentDescription = "DataWipe",
                        tint = Orange1,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = stringResource(R.string.clear_data),
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
            if (alertToggle.value) {
                AlertDialog(
                    backgroundColor = MaterialTheme.colors.background,
                    onDismissRequest = {
                        alertToggle.value = false
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.clear_data),
                            style = MaterialTheme.typography.body1,
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.are_you_sure),
                            style = MaterialTheme.typography.body2,
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.cleanAppData()
                                alertToggle.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Blue2),
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.confirm),
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                alertToggle.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Blue1),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.cancel),
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                )
            }
        }
    }
}