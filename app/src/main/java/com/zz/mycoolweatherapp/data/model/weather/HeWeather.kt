package com.zz.mycoolweatherapp.model.weather

import com.google.gson.annotations.SerializedName
import com.zz.mycoolweatherapp.data.model.weather.Weather

class HeWeather {

    @SerializedName("HeWeather")
    var weather: List<Weather>? = null

}