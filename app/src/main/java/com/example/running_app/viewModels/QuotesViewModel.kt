package com.example.running_app.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

class QuotesViewModel: ViewModel() {

    private val quoteList = arrayListOf(
        "Good things come to those who sweat",
        "Running is not just exercise; it is a lifestyle",
        "Every workout counts. Just do it",
        "Exercise to have fun and be healthy",
        "Any exercise is better than no exercise",
    )

    val quoteLoop = quoteList
//        .asIterable()
        .asSequence()
        .asFlow()
        .onEach { delay(12000) }
}