package com.example.running_app.data.weather

import com.example.running_app.data.weather.api.WeatherApi
import com.example.running_app.data.weather.api.toWeatherInfo
import com.example.running_app.data.weather.weatherData.WeatherInfo
import javax.inject.Inject


interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): WeatherResource<WeatherInfo>
}

class WeatherRepositoryImp @Inject constructor(
    private val myApi: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): WeatherResource<WeatherInfo> {
        return try {
            WeatherResource.Success(
                data = myApi.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception){
            WeatherResource.Error(e.message ?: e.printStackTrace().toString())
        }
    }
}