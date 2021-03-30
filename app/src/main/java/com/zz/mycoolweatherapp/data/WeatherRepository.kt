package com.zz.mycoolweatherapp.data

import com.zz.mycoolweatherapp.data.db.WeatherDao
import com.zz.mycoolweatherapp.data.model.weather.Weather
import com.zz.mycoolweatherapp.data.network.CoolWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WeatherRepository private constructor(private val dao : WeatherDao, private val netWork: CoolWeatherNetWork){


    suspend fun getWeather(weatherId : String) : Weather {
        var weather : Weather? = dao.getCachedWeatherInfo()
        if(weather == null) weather = requestWeather(weatherId)
        return weather
    }

    // withContext线程切换 这里线程切换到IO线程执行 等待执行完毕
    suspend fun requestWeather(weatherId: String) = withContext(Dispatchers.IO) {
        val heWeather = netWork.fetchWeather(weatherId)
        val weather = heWeather.weather!![0]
        dao.cacheWeatherInfo(weather)
        weather
    }

    suspend fun requestBindPic() = withContext(Dispatchers.IO) {
        val bindPic = netWork.fetchBingPic()
        bindPic
    }


    fun clearWeatherCache(key : String) = dao.clearCache(key)

    fun isWeatherCached() = dao.getCachedWeatherInfo() != null

    fun getWeatherCached() : Weather? = dao.getCachedWeatherInfo()!!

    companion object {

        private var placeRepository : WeatherRepository? = null

        fun getInstance(dao : WeatherDao,coolWeatherNetWork: CoolWeatherNetWork) : WeatherRepository{
            if(placeRepository == null){

                synchronized(WeatherRepository::class.java){

                    if(placeRepository == null){

                        placeRepository = WeatherRepository(dao,coolWeatherNetWork)

                    }

                }

            }

            return placeRepository!!
        }
    }
}