package com.example.running_app.data.weather.module

import com.example.running_app.data.weather.api.DailyWeatherRepository
import com.example.running_app.data.weather.api.DailyWeatherRepositoryImp
import com.example.running_app.data.weather.api.WeatherRepository
import com.example.running_app.data.weather.api.WeatherRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImp: WeatherRepositoryImp): WeatherRepository
}

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class DailyRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDailyWeatherRepository(dailyWeatherRepositoryImp: DailyWeatherRepositoryImp): DailyWeatherRepository
}