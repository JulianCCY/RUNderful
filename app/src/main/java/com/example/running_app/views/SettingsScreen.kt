package com.example.running_app.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Bluetooth
import androidx.compose.material.icons.sharp.BluetoothConnected
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.running_app.R
import com.example.running_app.data.running.heartrate.BLEViewModel
import com.example.running_app.ui.theme.Orange1
import com.example.running_app.viewModels.SettingsViewModel
import kotlinx.coroutines.delay

@Composable
fun SettingsScreen(bleViewModel: BLEViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        SettingsTitle()
        InputSection()
        BluetoothSection(viewModel = bleViewModel)
        SettingsSubmit()
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
fun InputSection(viewModel: SettingsViewModel = viewModel()) {
    val nameMaxChar = 15
    val maxChar = 3
    var nickname by remember { mutableStateOf(viewModel.nickname) }
    var height by remember { mutableStateOf(viewModel.height.toString()) }
    var weight by remember { mutableStateOf(viewModel.weight.toString()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp, bottom = 75.dp)
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
            OutlinedTextField(
                value = nickname,
                onValueChange = {
                    if (it.length <= nameMaxChar) nickname = it
                },
                label = { Text(text = "Nickname")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

@Composable
fun SettingsSubmit() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ) {
        Button(
            onClick = { },
            modifier = Modifier
                .width(100.dp)
        ) {
            Text(
                text = stringResource(R.string.all_set),
                style = MaterialTheme.typography.body2,
            )
        }
    }
}