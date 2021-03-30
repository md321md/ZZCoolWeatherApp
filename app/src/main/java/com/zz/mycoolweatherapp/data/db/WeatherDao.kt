package com.zz.mycoolweatherapp.data.db

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.zz.mycoolweatherapp.CoolWeatherApplication
import com.zz.mycoolweatherapp.data.model.weather.Weather

class WeatherDao {

    companion object{
        private var instance : WeatherDao ?= null
        fun getInstance() : WeatherDao{
            if(instance == null){
                synchronized(WeatherDao::class.java){
                    if(instance == null){
                        instance = WeatherDao()
                    }
                }
            }
            return instance!!
        }
    }

    fun clearCache(key: String?){
        if(key == null) return
        PreferenceManager.getDefaultSharedPreferences(CoolWeatherApplication.context).edit{
            remove(key)
        }
    }

    fun cacheWeatherInfo(weather: Weather?){
        if(weather == null) return
        PreferenceManager.getDefaultSharedPreferences(CoolWeatherApplication.context).edit{
            val weatherInfo = Gson().toJson(weather)
            putString("weather", weatherInfo)
        }
    }

    fun getCachedWeatherInfo() : Weather?{
        val weatherInfo = PreferenceManager.getDefaultSharedPreferences(CoolWeatherApplication.context).getString(
            "weather",
            null
        )
        if(weatherInfo != null){
            return Gson().fromJson(weatherInfo, Weather::class.java)
        }
        return null
    }

    private fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit){
        val editor = edit()
        action(editor)
        editor.apply()
    }

}