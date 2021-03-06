package com.zz.mycoolweatherapp.data.network.api

import com.zz.mycoolweatherapp.data.model.place.City
import com.zz.mycoolweatherapp.data.model.place.County
import com.zz.mycoolweatherapp.data.model.place.Province
import com.zz.mycoolweatherapp.model.weather.HeWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("api/weather")
    fun getWeather(@Query("cityid") weatherId: String): Call<HeWeather>

    @GET("api/bing_pic")
    fun getBingPic(): Call<String>

}