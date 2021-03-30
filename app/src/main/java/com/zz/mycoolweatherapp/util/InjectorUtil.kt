package com.zz.mycoolweatherapp.util

import com.zz.mycoolweatherapp.data.PlaceRepository
import com.zz.mycoolweatherapp.data.WeatherRepository
import com.zz.mycoolweatherapp.data.db.CoolWeatherDatabase
import com.zz.mycoolweatherapp.data.db.WeatherDao
import com.zz.mycoolweatherapp.data.network.CoolWeatherNetWork
import com.zz.mycoolweatherapp.ui.MainModelFactory
import com.zz.mycoolweatherapp.ui.area.ChooseAreaModelFactory
import com.zz.mycoolweatherapp.ui.weather.WeatherModelFactory

object InjectorUtil {

    private fun getPlaceRepository() = PlaceRepository.getInstance(getCoolWeatherDatabase().placeDao(),CoolWeatherNetWork.getInstance())
    private fun getWeatherRepository() = WeatherRepository.getInstance(getWeatherDao(),CoolWeatherNetWork.getInstance())
    fun getMainModelFactory() = MainModelFactory(getWeatherRepository())
    fun getChooseAreaModelFactory() = ChooseAreaModelFactory(getPlaceRepository())
    fun getWeatherModelFactory() = WeatherModelFactory(getWeatherRepository())
    fun getCoolWeatherDatabase() = CoolWeatherDatabase.getInstance()
    fun getWeatherDao() = WeatherDao.getInstance()

}