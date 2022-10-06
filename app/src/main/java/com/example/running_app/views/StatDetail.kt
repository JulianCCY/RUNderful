package com.example.running_app.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.running_app.viewModels.StatDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StatDetail(dataId: Int, viewModel: StatDetailViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = dataId.toString())
    }
}