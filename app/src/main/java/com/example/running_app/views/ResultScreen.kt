package com.example.running_app.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ResultScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConfirmButton()
    }
}

@Composable
fun ConfirmButton() {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Confirm result")
    }
}