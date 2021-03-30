package com.zz.mycoolweatherapp.data.network

import com.zz.mycoolweatherapp.data.network.api.PlaceService
import com.zz.mycoolweatherapp.data.network.api.WeatherService
import retrofit2.await

class CoolWeatherNetWork {
    private val placeService = ServiceCreator.getInstance().create(PlaceService::class.java)


    private val weatherService = ServiceCreator.getInstance().create(WeatherService::class.java)
    // 获取省
    suspend fun fetchProvinceList() = placeService.getProvinces().await()
    // 获取市
    suspend fun fetchCitiesList(provinceId : Int) = placeService.getCities(provinceId).await()
    // 获取区
    suspend fun fetchCountiesList(provinceId : Int,cityId : Int) = placeService.getCounties(provinceId,cityId).await()

    suspend fun fetchWeather(weatherid : String) = weatherService.getWeather(weatherid).await()

    suspend fun fetchBingPic() = weatherService.getBingPic().await()

    companion object {

        private var netWork : CoolWeatherNetWork? = null

        fun getInstance() : CoolWeatherNetWork {
            if (netWork == null) {

                synchronized(CoolWeatherNetWork::class.java){
                    if (netWork == null) {
                        netWork = CoolWeatherNetWork()
                    }
                }
            }

            return netWork!!
        }

    }


}