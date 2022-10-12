package com.example.running_app.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Bluetooth
import androidx.compose.material.icons.sharp.BluetoothConnected
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.R
import com.example.running_app.data.db.User
import com.example.running_app.data.running.heartrate.BLEViewModel
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.viewModels.SettingsViewModel

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
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.h1,
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
                .padding(top = 50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 75.dp)
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
                        .padding(bottom = 15.dp)
                ) {
//                Text(text = userInfo.value?.name ?: "username")
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
                // Height
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
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
                            .width(150.dp)
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
                    modifier = Modifier
                        .fillMaxWidth()
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
                            .width(150.dp)
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = "kg",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            BluetoothSection(viewModel = bleViewModel)
//        SettingsSubmit(userInfo.value?.uid, nickname, height.toInt(), weight.toInt())
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)
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
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
            // required an alert message 30000 thanks!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            Button(
                onClick = {viewModel.cleanAppData()},
                modifier = Modifier
                    .width(100.dp)
            ) {
                Text(
                    text = "Clean data",
                    style = MaterialTheme.typography.body2,
                )
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
//            border = BorderStroke(1.dp, Orange1),
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