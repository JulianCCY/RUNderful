package com.example.running_app.data.weather.api

import android.util.Log
import com.example.running_app.data.weather.WeatherResource
import com.example.running_app.data.weather.weatherData.DailyWeatherInfo
import javax.inject.Inject

interface DailyWeatherRepository{
    suspend fun getDailyWeatherData(lat: Double, long: Double): WeatherResource<DailyWeatherInfo>
}

class DailyWeatherRepositoryImp @Inject constructor(
   private val myApi: DailyWeatherApi
): DailyWeatherRepository {
    override suspend fun getDailyWeatherData(
        lat: Double,
        long: Double
    ): WeatherResource<DailyWeatherInfo> {
        return try {
            WeatherResource.Success(
                data = myApi.getDailyWeatherData(
                    lat = lat,
                    long = long
                ).toDailyWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            WeatherResource.Error(e.message ?: e.localizedMessage)
        }
    }
}