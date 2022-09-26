package com.example.running_app.data.weather

import androidx.compose.foundation.ExperimentalFoundationApi
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