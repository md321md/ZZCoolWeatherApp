package com.zz.mycoolweatherapp.ui.weather

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zz.mycoolweatherapp.data.PlaceRepository
import com.zz.mycoolweatherapp.data.WeatherRepository
import com.zz.mycoolweatherapp.data.model.place.City
import com.zz.mycoolweatherapp.data.model.place.County
import com.zz.mycoolweatherapp.data.model.place.Province
import com.zz.mycoolweatherapp.data.model.weather.Weather
import com.zz.mycoolweatherapp.ui.area.ChooseAreaFragment.Companion.LEVEL_CITY
import com.zz.mycoolweatherapp.ui.area.ChooseAreaFragment.Companion.LEVEL_COUNTY
import com.zz.mycoolweatherapp.ui.area.ChooseAreaFragment.Companion.LEVEL_PROVINCE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository : WeatherRepository) : ViewModel() {


    var bingPicUrl = MutableLiveData<String>()
    var weather = MutableLiveData<Weather>()
    var weatherId = MutableLiveData<String>()

    fun isWeatherCached() : Boolean = weatherRepository.isWeatherCached()
    fun getWeatherCached() = weatherRepository.getWeatherCached()
    fun clearWeatherCached(key : String) = weatherRepository.clearWeatherCache(key)


    fun getWeather(){
        launch {
            weather.value = weatherRepository.getWeather(weatherId.value!!)
            loadBindPic()
        }
    }

    fun refuseWeather(){
        launch {
            weather.value = weatherRepository.requestWeather(weatherId.value!!)
            loadBindPic()
        }
    }

    fun loadBindPic(){
        launch {
            bingPicUrl.value = weatherRepository.requestBindPic()
        }
    }

    private fun launch(block: suspend () -> Unit): Any = viewModelScope.launch {
        try {

            block()

        }catch (t : Throwable){

        }
    }

}