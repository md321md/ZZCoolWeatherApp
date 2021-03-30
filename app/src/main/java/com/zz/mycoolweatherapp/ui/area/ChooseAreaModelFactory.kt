package com.zz.mycoolweatherapp.ui.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zz.mycoolweatherapp.data.PlaceRepository
import com.zz.mycoolweatherapp.data.WeatherRepository

class ChooseAreaModelFactory(private val placeRepository: PlaceRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChooseAreaViewModel(placeRepository) as T
    }
}