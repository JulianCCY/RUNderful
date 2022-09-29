package com.example.running_app.data.weather.module

import androidx.compose.foundation.ExperimentalFoundationApi
import com.example.running_app.data.weather.location.InitLocationTracker
import com.example.running_app.data.weather.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalFoundationApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule  {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(initLocationTracker: InitLocationTracker): LocationTracker
}