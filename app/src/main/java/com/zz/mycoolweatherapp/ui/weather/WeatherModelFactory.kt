package com.zz.mycoolweatherapp.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zz.mycoolweatherapp.data.PlaceRepository
import com.zz.mycoolweatherapp.data.WeatherRepository

class WeatherModelFactory(private val weatherRepository: WeatherRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository) as T
    }
}